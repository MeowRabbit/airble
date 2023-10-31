package stm.airble._0_public;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import stm.airble.R;

public class Popup_Dialog_Activity extends Activity {

    TextView title_TextView, content_TextView;
    String title_str, content_str;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.default_popup);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.popup_title_TextView);
        content_TextView = findViewById(R.id.popup_content_TextView);

        Intent intent = getIntent();
        title_str = intent.getStringExtra("title");
        content_str = intent.getStringExtra("content");

        if(title_str.trim().equals("")){
            title_TextView.setVisibility(View.GONE);
        }else{
            title_TextView.setText(title_str.trim());
        }

        if(content_str.trim().equals("")){
            content_TextView.setVisibility(View.GONE);
        }else{
            content_TextView.setText(content_str.trim());
        }


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_DOWN){
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