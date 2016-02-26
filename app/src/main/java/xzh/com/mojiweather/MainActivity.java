package xzh.com.mojiweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import xzh.com.mojiweather.model.ChartItem;
import xzh.com.mojiweather.view.ChartView;
import xzh.com.mojiweather.view.SceneSurfaceView;

public class MainActivity extends AppCompatActivity {

    private ChartView cView;
    private SceneSurfaceView bgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        cView= (ChartView) findViewById(R.id.chartView);
        bgView= (SceneSurfaceView) findViewById(R.id.id_bgView);
        loadData();
    }

    private void loadData() {
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        list.add(new ChartItem("昨天", 23.2f));
        list.add(new ChartItem("今天", 22.4f));
        list.add(new ChartItem("明天", 24.7f));
        list.add(new ChartItem("4/18", 23.5f));
        list.add(new ChartItem("4/19", 25.5f));
        cView.setView(list,"单位：摄氏度");
    }


    @Override
    protected void onResume() {
        super.onResume();
       bgView.resume();
    }
}
