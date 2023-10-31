package stm.airble._3_registration.viewpager2.dialog;

import static stm.airble._0_public.Public_Values.Server_Domain;
import static stm.airble._0_public.Public_Values.User_Email;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

import stm.airble.R;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;

public class Shared_Dialog_Activity extends Activity {

    Loading_ProgressDialog Shared_Loading;
    TextView title_TextView;
    EditText shared_EditText;
    LinearLayout shared_LinearLayout, shared_cancel_LinearLayout;
    TextView shared_TextView, shared_cancel_TextView;

    String device_name;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registration_shared);

        Shared_Loading = new Loading_ProgressDialog(this);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        shared_EditText = findViewById(R.id.registration_shared_EditText);
        shared_LinearLayout = findViewById(R.id.registration_shared_LinearLayout);
        shared_cancel_LinearLayout = findViewById(R.id.registration_shared_cancel_LinearLayout);
        shared_TextView = findViewById(R.id.registration_shared_TextView);
        shared_cancel_TextView = findViewById(R.id.registration_shared_cancel_TextView);

        shared_EditText.setText("");
        shared_EditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        shared_EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(shared_EditText.getWindowToken(), 0);
                    try {
                        Shared_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=423&code=" + shared_EditText.getText().toString().trim() +
                                "&email=" + User_Email;
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Load_Airble_HttpConnection().execute(url);
                    } catch (Exception e) {
                        Shared_Loading.Stop_Loading();
                        Toast.makeText(Shared_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        /**
         *  공유기기 추가 취소 버튼
         */
        {
            shared_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            shared_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
         *  공유기기 추가 하기 버튼
         */
        {
            shared_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Shared_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=423&code=" + shared_EditText.getText().toString().trim() +
                                "&email=" + User_Email;
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Load_Airble_HttpConnection().execute(url);
                    } catch (Exception e) {
                        Shared_Loading.Stop_Loading();
                        Toast.makeText(Shared_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            shared_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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

    //서버에 연결하는 코딩
    private class Load_Airble_HttpConnection extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String data = "";
            if (urls.length == 0) {
                return " URL is empty";
            }
            try {
                GET_RequestHttpURLConnection connection = new GET_RequestHttpURLConnection();
                data = connection.request(urls[0]);
            } catch (Exception e) {
                data = e.getMessage();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            if (data != null) { //연결성공
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                switch (code){
                    // 기존에 저장된 기기 불러오기

                    case "S423":
                    {
                        setResult(RESULT_OK);
                        Shared_Loading.Stop_Loading();
                        finish();
                    }
                    break;

                    case "F423_1": // 실패 - Fail 423_1 ( 잘못된 코드, 잘못된 경로 )
                    case "F423_3": // 실패 - Fail 423_3 ( 잘못된 코드 번호 )
                    {
                        Shared_Loading.Stop_Loading();
                        Toast.makeText(Shared_Dialog_Activity.this, "존재하지 않거나 잘못된 코드입니다. 다시 입력해 주시기를 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case "F423_2": // 실패 - Fail 423_2 ( 이미 추가된 보유하고 있는 기기 )
                    {
                        Shared_Loading.Stop_Loading();
                        Toast.makeText(Shared_Dialog_Activity.this, "이미 등록된 기기 입니다. 다른 코드를 입력해 주시기를 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case "E423":
                    {
                        Shared_Loading.Stop_Loading();
                        Toast.makeText(Shared_Dialog_Activity.this, "서버와의 연결상태가 좋지 않습니다. 잠시 후에 다시 시도해 주시기를 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                Shared_Loading.Stop_Loading();
                Toast.makeText(Shared_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}