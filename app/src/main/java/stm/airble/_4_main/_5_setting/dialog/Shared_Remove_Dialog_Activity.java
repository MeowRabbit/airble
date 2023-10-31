package stm.airble._4_main._5_setting.dialog;

import static stm.airble._0_public.Public_Values.APP_Airble_Model_Array;
import static stm.airble._0_public.Public_Values.Server_Domain;
import static stm.airble._0_public.Public_Values.User_Email;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

import stm.airble.R;
import stm.airble._0_public.Loading_ProgressDialog;
import stm.airble._4_main.MainActivity;
import stm.airble._0_public.GET_RequestHttpURLConnection;

public class Shared_Remove_Dialog_Activity extends Activity {

    Loading_ProgressDialog Shared_Remove_Loading;

    TextView title_TextView, settings_airble_title_sub_TextView;
    LinearLayout settings_airble_shared_remove_LinearLayout, settings_airble_shared_remove_cancel_LinearLayout;
    TextView settings_airble_shared_remove_TextView, settings_airble_shared_remove_cancel_TextView;

    String shared_email = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_settings_airble_setting_page_2_remove);

        Shared_Remove_Loading = new Loading_ProgressDialog(this);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        settings_airble_title_sub_TextView = findViewById(R.id.settings_airble_title_sub_TextView);
        settings_airble_shared_remove_LinearLayout = findViewById(R.id.settings_airble_shared_remove_LinearLayout);
        settings_airble_shared_remove_cancel_LinearLayout = findViewById(R.id.settings_airble_shared_remove_cancel_LinearLayout);
        settings_airble_shared_remove_TextView = findViewById(R.id.settings_airble_shared_remove_TextView);
        settings_airble_shared_remove_cancel_TextView = findViewById(R.id.settings_airble_shared_remove_cancel_TextView);

        shared_email = getIntent().getStringExtra("shared_email");
        settings_airble_title_sub_TextView.setText(shared_email + " 님이 기기의 데이터를 확인할 수 없으며, 보유한 기기목록에서 기기가 사라집니다.");

        /**
         *  공유 해제 취소 버튼
         */
        {
            settings_airble_shared_remove_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            settings_airble_shared_remove_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
         *  공유 해제 하기 버튼
         */
        {
            settings_airble_shared_remove_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Shared_Remove_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=426&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address() + "&email=" + User_Email + "&shared_email=" + shared_email;
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Remove_HttpConnection().execute(url);
                    } catch (Exception e) {
                        Shared_Remove_Loading.Stop_Loading();
                        Toast.makeText(Shared_Remove_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            settings_airble_shared_remove_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
        super.onBackPressed();
    }

    //서버에 연결하는 코딩
    private class Remove_HttpConnection extends AsyncTask<URL, Integer, String> {

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
                //String code = data;
                switch (code){
                    // 공유해제
                    case "S426":
                    {
                        Shared_Remove_Loading.Stop_Loading();
                        setResult(RESULT_OK);
                        onBackPressed();
                    }
                    break;

                    case "E426":
                    case "F426":
                    {
                        Shared_Remove_Loading.Stop_Loading();
                        Toast.makeText(Shared_Remove_Dialog_Activity.this, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                Shared_Remove_Loading.Stop_Loading();
                Toast.makeText(Shared_Remove_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}