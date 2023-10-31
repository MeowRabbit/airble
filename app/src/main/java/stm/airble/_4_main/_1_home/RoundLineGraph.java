package stm.airble._4_main._1_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

import stm.airble.R;

public class RoundLineGraph extends View {
    double dp;
    int stroke;
    int width;
    int height;
    double max_value;
    double value;
    int[] colors = { Color.rgb(0x59, 0xA3, 0xFC), Color.rgb(0x39, 0x79, 0xF9) };

    public RoundLineGraph(Context context, int stroke, double max_value, double value){
        super(context);
        dp = getResources().getDimension(R.dimen.public_1dp);
        this.stroke = (int)(dp*stroke);
        if(stroke%2==1) this.stroke++;
        this.width = this.stroke*14 + this.stroke/2 + 1;
        this.height = (int)(this.stroke);
        this.max_value = max_value;
        this.value = value;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(stroke);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setColor(Color.rgb(0xE4, 0xEF, 0xFF));
        canvas.drawLine(stroke / 2, stroke / 2, width - stroke / 2, stroke / 2, paint);

        int percent = (int)(value / max_value * 100.0);
        if(percent == 0) percent = 1;
        else if(percent > 100) percent = 100;

        paint.setShader(new LinearGradient(width * percent / 100,0,0,0,colors[0],colors[1], Shader.TileMode.CLAMP));
        canvas.drawLine(stroke / 2, stroke / 2, (width - stroke / 2) * percent / 100, stroke / 2, paint);
    }

    public void setValue(int value){
        this.value = value;
        this.invalidate();
    }

    public void setMax_value(int max_value){
        this.max_value = max_value;
        this.invalidate();
    }
}
