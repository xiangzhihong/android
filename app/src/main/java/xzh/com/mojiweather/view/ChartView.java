package xzh.com.mojiweather.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import xzh.com.mojiweather.R;
import xzh.com.mojiweather.model.ChartItem;
import xzh.com.mojiweather.utils.Utils;

/**
 * Created by xiangzhihong on 2016/2/25 on 17:25.
 * 绘制天气折线图的View
 */
public class ChartView extends View {
    private Context context;
    private ArrayList<ChartItem> items;
    private String unit;
    private String yFormat = "0.#";

    public void setView(ArrayList<ChartItem> list, String unitInfo) {
        this.items = list;
        this.unit = unitInfo;
    }

    public ChartView(Context ct) {
        super(ct);
        this.context = ct;
    }

    public ChartView(Context ct, AttributeSet attrs) {
        super(ct, attrs);
        this.context = ct;
    }

    public ChartView(Context ct, AttributeSet attrs, int defStyle) {
        super(ct, attrs, defStyle);
        this.context = ct;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (items == null) {
            return;
        }
        int height = getHeight();
        int width = getWidth();

        int split = Utils.dip2px(context, 20);
        int marginl = width / 12;
        int margint = Utils.dip2px(context, 60);
        int margint2 = Utils.dip2px(context, 25);
        int bheight = height - margint - 2 * split;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(context.getResources().getColor(R.color.weather_line));
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawLine(split, margint2, width - split, margint2, paint);
//        canvas.drawLine(split, height - split, width - split, height - split,
//                paint);

        // 画单位
        Paint p = new Paint();
        p.setTextSize(Utils.sp2px(context, 12));
        p.setColor(context.getResources().getColor(R.color.weather_line));
        canvas.drawText(unit, split+Utils.dip2px(context,30f), margint2 + split * 2, p);

        // 画X坐标
        ArrayList<Integer> xlist = new ArrayList<Integer>();
        paint.setColor(Color.GRAY);
        for (int i = 0; i < items.size(); i++) {
            int span = (width - 2 * marginl) / items.size();
            int x = marginl + span / 2 + span * i;
            xlist.add(x);
            drawText(items.get(i).getX(), x, split * 2, canvas);
        }

        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (int i = 0; i < items.size(); i++) {
            float y = items.get(i).getY();
            if (y > max) {
                max = y;
            }
            if (y < min) {
                min = y;
            }
        }

        float span = max - min;
        if (span == 0) {
            span = 6.0f;
        }
        max = max + span / 6.0f;
        min = min - span / 6.0f;

        // 获取点集合
        Point[] mPoints = getPoints(xlist, max, min, bheight, margint);

        // 画线
        paint.setColor(context.getResources().getColor(R.color.weather_line));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        drawLine(mPoints, canvas, paint);

        // 画点
        paint.setColor(context.getResources().getColor(R.color.weather_dot));
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mPoints.length; i++) {
            canvas.drawCircle(mPoints[i].x, mPoints[i].y, 12, paint);
            String yText = new java.text.DecimalFormat(yFormat).format(items
                    .get(i).getY());
            drawText(yText, mPoints[i].x,
                    mPoints[i].y - Utils.dip2px(context, 12), canvas);
        }
    }

    private Point[] getPoints(ArrayList<Integer> xlist, float max, float min,
                              int h, int top) {
        Point[] points = new Point[items.size()];
        for (int i = 0; i < items.size(); i++) {
            int ph = top + h
                    - (int) (h * ((items.get(i).getY() - min) / (max - min)));
            points[i] = new Point(xlist.get(i), ph);
        }
        return points;
    }

    private void drawLine(Point[] ps, Canvas canvas, Paint paint) {
        Point startp = new Point();
        Point endp = new Point();
        for (int i = 0; i < ps.length - 1; i++) {
            startp = ps[i];
            endp = ps[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, paint);
        }
    }

    private void drawText(String text, int x, int y, Canvas canvas) {
        Paint p = new Paint();
        p.setAlpha(context.getResources().getColor(R.color.color_write));
        p.setTextSize(Utils.sp2px(context, 14));
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.WHITE);
        canvas.drawText(text, x, y, p);
    }

    public ArrayList<ChartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<ChartItem> items) {
        this.items = items;
    }

    public String getyFormat() {
        return yFormat;
    }

    public void setyFormat(String yFormat) {
        this.yFormat = yFormat;
    }

}
