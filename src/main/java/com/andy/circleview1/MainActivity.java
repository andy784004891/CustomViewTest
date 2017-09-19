package com.andy.circleview1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CircleView view1;
    private TimeCircleView view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = (CircleView) findViewById(R.id.circle_1);
        view1.setOnClickListener(this);
        view2 = (TimeCircleView) findViewById(R.id.circle_2);
        view2.setOnClickListener(this);
        view2.setCircleContent("跳过广告");
        view2.startDraw();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.circle_1:
                view1.startDraw();
                break;
            case R.id.circle_2:
                Toast.makeText(this, "执行跳过动作", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
