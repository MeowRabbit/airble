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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;

import java.net.URL;

public class Change_Volume_Dialog_Activity extends Activity {

    Loading_ProgressDialog Volume_Loading;

    TextView title_TextView;
    LinearLayout volume_LinearLayout, volume_cancel_LinearLayout;
    TextView volume_TextView, volume_cancel_TextView;
    SeekBar volume_SeekBar;
    String device_name;

    int volume;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_settings_airble_change_volume);

        Volume_Loading = new Loading_ProgressDialog(this);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        volume_LinearLayout = findViewById(R.id.main_settings_volume_LinearLayout);
        volume_cancel_LinearLayout = findViewById(R.id.main_settings_volume_cancel_LinearLayout);
        volume_TextView = findViewById(R.id.main_settings_volume_TextView);
        volume_cancel_TextView = findViewById(R.id.main_settings_volume_cancel_TextView);
        volume_SeekBar = findViewById(R.id.device_volume_SeekBar);

        if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() > 155){
            Log.d("Sans", APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() + "");
            volume = APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() - 155;
        }else{
            volume = 0;
        }
        title_TextView.setText("볼륨 : " + volume + "%");
        volume_SeekBar.setProgress(volume);

        volume_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = seekBar.getProgress();
                title_TextView.setText("볼륨 : " + volume + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /**
         *  볼륨 변경 취소 버튼
         */
        {
            volume_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            volume_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
            volume_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(volume != 0){
                            volume += 155;
                        }else{
                            volume = 150;
                        }
                        Volume_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=471&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address() +
                                "&volume=" + volume +
                                "&brightness=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getBrightness();
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Load_Airble_Status_HttpConnection().execute(url);
                    } catch (Exception e) {
                        Volume_Loading.Stop_Loading();
                        Toast.makeText(Change_Volume_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            volume_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
    public void onPause(){
        //APP_Disposables.clear();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼
        finish();
        super.onBackPressed();
    }

    //서버에 연결하는 코딩
    private class Load_Airble_Status_HttpConnection extends AsyncTask<URL, Integer, String> {

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

                    case "S471":
                    {
                        APP_Airble_Model_Array.get(((MainActivity) MainActivity.Main_Context).select_setting_airble_num).setVolume(volume);
                        Volume_Loading.Stop_Loading();
                        Toast.makeText(Change_Volume_Dialog_Activity.this, "볼륨이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;

                    case "F471":
                    {
                        Volume_Loading.Stop_Loading();
                        Toast.makeText(Change_Volume_Dialog_Activity.this, "설정에 실패하였습니다. 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                Volume_Loading.Stop_Loading();
                Toast.makeText(Change_Volume_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}