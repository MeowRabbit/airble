package stm.airble._5_add_airble.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import stm.airble.R;
import stm.airble._5_add_airble.AddAirbleActivity;

public class Add_Cancel_Dialog_Activity extends Activity {

    TextView title_TextView;
    LinearLayout add_airble_cancel_LinearLayout, add_airble_cancel_cancel_LinearLayout;
    TextView add_airble_cancel_TextView, add_airble_cancel_cancel_TextView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_airble_cancel);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        add_airble_cancel_LinearLayout = findViewById(R.id.add_airble_cancel_LinearLayout);
        add_airble_cancel_cancel_LinearLayout = findViewById(R.id.add_airble_cancel_cancel_LinearLayout);
        add_airble_cancel_TextView = findViewById(R.id.add_airble_cancel_TextView);
        add_airble_cancel_cancel_TextView = findViewById(R.id.add_airble_cancel_cancel_TextView);

        /**
         *  기기 추가 취소 취소 버튼
         */
        {
            add_airble_cancel_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            add_airble_cancel_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_UP:         // 버튼을 다시 땜
                            v.setAlpha(1);
                            break;
                        case MotionEvent.ACTION_MOVE:       // 버튼을 누르고 움직임
                            if(!v.isPressed()) {            // 버튼을 벗어날 경우
                                v.setAlpha(1);
                            }
                            break;

                        case MotionEvent.ACTION_DOWN:       // 버튼을 누름
                            v.setAlpha(0.65f);
                            break;

                    }
                    return false;
                }
            });
        }

        /**
         *  기기 추가 취소 하기 버튼
         */
        {
            add_airble_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AddAirbleActivity)AddAirbleActivity.add_airble_context).is_Add_Cancel = true;
                    finish();
                }
            });

            add_airble_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_UP:         // 버튼을 다시 땜
                            v.setAlpha(1);
                            break;
                        case MotionEvent.ACTION_MOVE:       // 버튼을 누르고 움직임
                            if(!v.isPressed()) {            // 버튼을 벗어날 경우
                                v.setAlpha(1);
                            }
                            break;

                        case MotionEvent.ACTION_DOWN:       // 버튼을 누름
                            v.setAlpha(0.65f);
                            break;

                    }
                    return false;
                }
            });
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼
        finish();
        super.onBackPressed();
    }
}