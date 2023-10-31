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

public class HalfCircleDonutGraph extends View {
    double dp;
    int stroke;
    int width;
    int height;
    double max_value;
    double value;
    int color_position;

    int[][] colors = {
            { Color.rgb(0x59, 0xA3, 0xFC), Color.rgb(0x39, 0x79, 0xF9) },
            { Color.rgb(0x10, 0xFE, 0x8D), Color.rgb(0x00, 0xC7, 0x8C) },
            { Color.rgb(0xFF, 0xB8, 0x4F), Color.rgb(0xEF, 0x82, 0x4E) },
            { Color.rgb(0xFF, 0x8C, 0x4F), Color.rgb(0xEF, 0x56, 0x4E) },
    };

    public HalfCircleDonutGraph(Context context, int stroke, double max_value, double value, int color_position){
        super(context);
        dp = getResources().getDimension(R.dimen.public_1dp);
        this.stroke = (int)(dp*stroke);
        if(stroke%2==1) this.stroke++;
        this.width = this.stroke*10 + this.stroke/2 + 1;
        this.height = (int)(this.stroke*5.3) + this.stroke*3/4 + 1;
        this.max_value = max_value;
        this.value = value;
        this.color_position = color_position;
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
        canvas.drawArc(stroke / 2, stroke / 2, width - stroke / 2, ( height - stroke * 3 / 4 ) * 2, -180, 180, false, paint);

        int angle = (int)(value / max_value * 180.0);
        if(angle == 0) angle = 1;
        else if(angle > 180) angle = 180;

        paint.setShader(new LinearGradient(width * angle / 180,0,0,0,colors[color_position][0],colors[color_position][1], Shader.TileMode.CLAMP));
        canvas.drawArc(stroke / 2, stroke / 2, width - stroke / 2, ( height - stroke * 3 / 4 ) * 2, -180, angle, false, paint);
    }

    public void setValue(int value){
        this.value = value;
        this.invalidate();
    }

    public void setMax_value(int max_value){
        this.max_value = max_value;
        this.invalidate();
    }

    public void setColor_position(int color_position){
        this.color_position = color_position;
        this.invalidate();
    }
}
