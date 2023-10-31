package stm.airble._4_main._5_setting.dialog;

import static stm.airble._0_public.Public_Values.APP_Airble_Model_Array;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class Reset_Dialog_Activity extends Activity {

    TextView title_TextView;
    EditText device_rename_EditText;
    LinearLayout option_rename_LinearLayout, option_rename_cancel_LinearLayout;
    TextView option_rename_TextView, option_rename_cancel_TextView;

    String device_name;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_settings_airble_reset);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        device_rename_EditText = findViewById(R.id.device_rename_EditText);
        option_rename_LinearLayout = findViewById(R.id.main_home_option_rename_LinearLayout);
        option_rename_cancel_LinearLayout = findViewById(R.id.main_home_option_rename_cancel_LinearLayout);
        option_rename_TextView = findViewById(R.id.main_home_option_rename_TextView);
        option_rename_cancel_TextView = findViewById(R.id.main_home_option_rename_cancel_TextView);

        device_rename_EditText.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getNick_Name());

        /**
         *  이름 변경 취소 버튼
         */
        {
            option_rename_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            option_rename_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
         *  이름 변경 하기 버튼
         */
        {
            option_rename_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).setNick_Name(device_rename_EditText.getText().toString().trim());
                    finish();
                }
            });

            option_rename_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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