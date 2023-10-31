package stm.airble._0_public;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import stm.airble.R;


public class CustomTextOutLineView extends androidx.appcompat.widget.AppCompatTextView {
    // 클래스 명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
    private boolean stroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;

    public CustomTextOutLineView(Context context, AttributeSet attrs, int defStyle){
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public CustomTextOutLineView(Context context, AttributeSet attrs){
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
        super(context, attrs);
        initView(context, attrs);
    }

    public CustomTextOutLineView(Context context){
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
        super(context);
    }

    private void initView(Context context, AttributeSet attrs){
        // 이 부분에서 오류가 발생할 경우 attrs.xml을 만들지 않아서니 넘어가도록합니다.
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextOutLineView);
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
        stroke = a.getBoolean(R.styleable.CustomTextOutLineView_textStroke, false);
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
        strokeWidth = a.getFloat(R.styleable.CustomTextOutLineView_textStrokeWidth, 0.0f);
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
        strokeColor = a.getColor(R.styleable.CustomTextOutLineView_textStrokeColor, 0xFFFFFFFF);
        // 클래스명 다르게 생성시 붙여넣으면 이곳을 클래스명에 맞게 수정
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(stroke){
            ColorStateList states = getTextColors();
            getPaint().setStyle(Paint.Style.STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);
            getPaint().setStyle(Paint.Style.FILL);
            setTextColor(states);
        }
        super.onDraw(canvas);
    }

}
