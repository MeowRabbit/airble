package stm.airble._1_splash.dialog;

import static stm.airble._0_public.Public_Values.Start_Popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import stm.airble.R;

public class Join_Dialog_Activity extends Activity {

    TextView service_title_TextView, service_content_TextView;
    TextView privacy_policy_title_TextView, privacy_policy_content_TextView;
    CheckBox service_CheckBox, privacy_policy_CheckBox;
    LinearLayout join_LinearLayout, join_cancel_LinearLayout;
    TextView join_TextView, join_cancel_TextView;

    String[] service_String_Array;
    String[] privacy_policy_String_Array;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_join);

        // 아이디 맞춰주기
        service_title_TextView = findViewById(R.id.service_title_TextView);
        service_content_TextView = findViewById(R.id.service_content_TextView);
        service_CheckBox = findViewById(R.id.service_CheckBox);
        privacy_policy_title_TextView = findViewById(R.id.privacy_policy_title_TextView);
        privacy_policy_content_TextView = findViewById(R.id.privacy_policy_content_TextView);
        privacy_policy_CheckBox = findViewById(R.id.privacy_policy_CheckBox);

        join_LinearLayout = findViewById(R.id.splash_join_LinearLayout);
        join_cancel_LinearLayout = findViewById(R.id.splash_join_cancel_LinearLayout);
        join_TextView = findViewById(R.id.splash_join_TextView);
        join_cancel_TextView = findViewById(R.id.splash_join_cancel_TextView);

        service_String_Array = getResources().getStringArray(R.array.settings_terms_service);
        privacy_policy_String_Array = getResources().getStringArray(R.array.settings_terms_privacy_policy);

        String service_content = "";
        for(int i = 0; i < service_String_Array.length; i++){
            service_content += service_String_Array[i] + "\n\n";
        }
        service_content_TextView.setText(service_content);

        String privacy_policy_content = "";
        for(int i = 0; i < privacy_policy_String_Array.length; i++){
            privacy_policy_content += privacy_policy_String_Array[i] + "\n\n";
        }
        privacy_policy_content_TextView.setText(privacy_policy_content);

        /**
         *  가입 취소 버튼
         */
        {
            join_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            join_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
         *  가입 하기 버튼
         */
        {
            join_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(service_CheckBox.isChecked()) {
                        if (privacy_policy_CheckBox.isChecked()){
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            Start_Popup(Join_Dialog_Activity.this, "개인정보 처리 방침에 대한 안내에 동의하여 주시기 바랍니다.", "");
                        }
                    }else{
                        Start_Popup(Join_Dialog_Activity.this, "이용약관에 동의하여 주시기 바랍니다.", "");
                    }
                }
            });

            join_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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