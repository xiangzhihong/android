package xzh.com.mojiweather.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class Actor {

    protected Context context;
    protected Matrix matrix = new Matrix();

    protected Actor(Context context) {
        this.context = context;
    }
    public abstract void draw(Canvas canvas, int width, int height);
}
