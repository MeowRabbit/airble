package stm.airble._4_main._5_setting.dialog;

import static stm.airble._0_public.Public_Values.*;

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

import stm.airble.R;
import stm.airble._0_public.Loading_ProgressDialog;
import stm.airble._4_main.MainActivity;
import stm.airble._0_public.GET_RequestHttpURLConnection;

import java.net.URL;

public class Remove_Dialog_Activity extends Activity {

    Loading_ProgressDialog Remove_Loading;

    TextView title_TextView;
    LinearLayout settings_airble_remove_LinearLayout, settings_airble_remove_cancel_LinearLayout;
    TextView settings_airble_remove_TextView, settings_airble_remove_cancel_TextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_settings_airble_remove);

        Remove_Loading = new Loading_ProgressDialog(this);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        settings_airble_remove_LinearLayout = findViewById(R.id.settings_airble_remove_LinearLayout);
        settings_airble_remove_cancel_LinearLayout = findViewById(R.id.settings_airble_remove_cancel_LinearLayout);
        settings_airble_remove_TextView = findViewById(R.id.settings_airble_remove_TextView);
        settings_airble_remove_cancel_TextView = findViewById(R.id.settings_airble_remove_cancel_TextView);

        if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()){
            title_TextView.setText("기기를 삭제하시겠습니까?\n공유된 모든 계정에서 기기가 삭제됩니다.");
        }else{
            title_TextView.setText("기기를 삭제하시겠습니까?");
        }

        /**
         *  기기 삭제 취소 버튼
         */
        {
            settings_airble_remove_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            settings_airble_remove_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
         *  기기 삭제 하기 버튼
         */
        {
            settings_airble_remove_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Remove_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=421&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address() + "&email=" + User_Email;
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Remove_HttpConnection().execute(url);
                    } catch (Exception e) {
                        Remove_Loading.Stop_Loading();
                        Toast.makeText(Remove_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            settings_airble_remove_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
                    // 리셋리셋
                    case "S421":
                    {
                        Remove_Loading.Stop_Loading();
                        ((MainActivity)MainActivity.Main_Context).airble_remove_bool = true;
                        onBackPressed();
                    }
                    break;

                    case "E421":
                    case "F421":
                    {
                        Remove_Loading.Stop_Loading();
                        Toast.makeText(Remove_Dialog_Activity.this, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                Remove_Loading.Stop_Loading();
                Toast.makeText(Remove_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}