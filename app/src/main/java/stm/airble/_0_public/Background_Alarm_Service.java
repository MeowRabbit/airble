package stm.airble._0_public;


import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static stm.airble._0_public.Public_Values.*;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import stm.airble.R;

public class Background_Alarm_Service extends Service {
    public static final int Ongoing_Channel = 1;
    public static int Alarm_Channel = 2;

    NotificationCompat.Builder notification;
    public static NotificationManager mNotificationManager;

    public static Thread Background_Alarm_Thread;

    Map<String, Map<Integer, Integer>> Alarm_Map;

    int test_pm = 0, test_co = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("NotificationId0")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Alarm_Map = new HashMap<>();
            // PendingIntent를 이용하면 포그라운드 서비스 상태에서 알림을 누르면 앱의 MainActivity를 다시 열게 된다.
            //Intent testIntent = new Intent(getApplicationContext(), MainActivity.class);
            //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, testIntent, FLAG_IMMUTABLE);

            // 오래오 윗버젼일 때는 아래와 같이 채널을 만들어 Notification과 연결해야 한다.
            NotificationChannel channel = new NotificationChannel("channel", "Alarm",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Notification과 채널 연걸
            mNotificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            mNotificationManager.createNotificationChannel(channel);

            Intent alarm_off = new Intent(this, Background_BroadcastReceiver.class);
            alarm_off.setAction("alarm_off");
            PendingIntent alarm_off_PendingIntent = PendingIntent.getBroadcast(this, 0, alarm_off, FLAG_IMMUTABLE);

            // Notification 세팅
            notification
                    = new NotificationCompat.Builder(getApplicationContext(), "channel")
                    .setSmallIcon(R.drawable.app_logo)
                    .setContentTitle("실시간으로 공기질을 확인하고 있습니다.")
                    //.setContentIntent(pendingIntent)
                    .addAction(R.drawable.app_logo ,"알림 끄기", alarm_off_PendingIntent)
                    .setOngoing(true);

            // id 값은 0보다 큰 양수가 들어가야 한다.
            mNotificationManager.notify(Ongoing_Channel, notification.build());

            // foreground에서 시작
            startForeground(Ongoing_Channel, notification.build());

            // 이전 포스트에서 패키지 이름을 2초마다 가져오는 스레드를 서비스에서 실행해준다. 서비스가 실행되면 이 스레드도 같이 실행된다.
            //checkPackageNameThread = new CheckPackageNameThread();
            //checkPackageNameThread.start();

            Start_Background_Thread(intent.getStringExtra("User_Email"));

        }catch (Exception e){
            e.printStackTrace();
        }
        return START_STICKY;

    }

    public void Start_Background_Thread(String email){
        Log.d("Sans", "Start_Background_Thread() 작동 시작");
        Stop_Background_Thread();
        Log.d("Sans", "Background_Alarm_Thread 새로 만들기");
        Background_Alarm_Thread = new Thread(new Runnable() {
            int num = 0;
            @Override
            public void run() {
                // 수행 시킬 동작들 구현

                try{
                    while(!Background_Alarm_Thread.isInterrupted()) {
                        Log.d("Sans", "백그라운드 작동중.. " + num++ + "초 경과");
        /*
                        test_pm += 1;
                        test_co += 10;

                        if(test_pm > 10) {
                            if(Alarm_Map.containsKey("Test_Device")){
                                Alarm_Map.put("Test_Device", new HashMap<>());
                            }

                            Alarm_Map.get("Test_Device").put(CO, test_co);

                            if(test_co > 100)
                            Make_Alarm();
                        }
*/
                        String server_url = Server_Domain + "airble_test?num=48&email=" + email;

                        Log.d("Sans", server_url);
                        URL url = new URL(server_url);
                        new HttpConnection().execute(url);

                        Thread.sleep(5000);
                    }
                }catch (Exception e){
                    Log.d("Sans", "백그라운드 쓰레드 문제 발생");
                    e.printStackTrace();
                }finally {
                    Log.d("Sans", "백그라운드 쓰레드 종료");
                    mNotificationManager.cancel(Ongoing_Channel);
                }
            }

            //서버에 연결하는 코딩
            class HttpConnection extends AsyncTask<URL, Integer, String> {

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
                            // Airble 현재 데이터 가져오기
                            case "S48":
                            {
                                try{
                                    String[] device_data = data.split(",,");
                                    for(int i = 1; i < device_data.length; i++){
                                        Log.d("Sans", device_data[i]);
                                        String[] matter_data = device_data[i].split(",");
                                        if(!Alarm_Map.containsKey(matter_data[0])){
                                            Alarm_Map.put(matter_data[0], new HashMap<>());
                                            Alarm_Map.get(matter_data[0]).put(VOCs, 0);
                                            Alarm_Map.get(matter_data[0]).put(CO2, 0);
                                            Alarm_Map.get(matter_data[0]).put(CO, 0);
                                            Alarm_Map.get(matter_data[0]).put(PM, 0);
                                        };

                                        int vocs_status, co2_status, co_status, pm_status;

                                        int vocs_value = Integer.parseInt(matter_data[1]);
                                        int co2_value = Integer.parseInt(matter_data[2]);
                                        int co_value = Integer.parseInt(matter_data[3]);
                                        int pm_value = Integer.parseInt(matter_data[4]);

                                        if(vocs_value >= VOCs_BAD){
                                            vocs_status = 3;
                                        }else if(vocs_value >= VOCs_SOSO){
                                            vocs_status = 2;
                                        }else if(vocs_value >= VOCs_GOOD){
                                            vocs_status = 1;
                                        }else{
                                            vocs_status = 0;
                                        }

                                        if(co2_value >= CO2_BAD){
                                            co2_status = 3;
                                        }else if(co2_value >= CO2_SOSO){
                                            co2_status = 2;
                                        }else if(co2_value >= CO2_GOOD){
                                            co2_status = 1;
                                        }else{
                                            co2_status = 0;
                                        }

                                        if(co_value >= CO_BAD){
                                            co_status = 3;
                                        }else if(co_value >= CO_SOSO){
                                            co_status = 2;
                                        }else if(co_value >= CO_GOOD){
                                            co_status = 1;
                                        }else{
                                            co_status = 0;
                                        }

                                        if(pm_value >= PM_BAD){
                                            pm_status = 3;
                                        }else if(pm_value >= PM_SOSO){
                                            pm_status = 2;
                                        }else if(pm_value >= PM_GOOD){
                                            pm_status = 1;
                                        }else{
                                            pm_status = 0;
                                        }

                                        Log.d("Sans", "nickname = '" + matter_data[0] + "', old voc status = " + Alarm_Map.get(matter_data[0]).get(VOCs) + ", new voc status = " + vocs_status + ", voc value = " + vocs_value);

                                        if(Alarm_Map.get(matter_data[0]).get(VOCs) < vocs_status){
                                            Make_Alarm(matter_data[0], VOCs, vocs_status, vocs_value);
                                            Alarm_Map.get(matter_data[0]).put(VOCs, vocs_status);
                                        }else{
                                            if( Alarm_Map.get(matter_data[0]).get(VOCs) != 0 && vocs_status == 0 ){
                                                Make_Alarm(matter_data[0], VOCs, vocs_status, vocs_value);
                                                Alarm_Map.get(matter_data[0]).put(VOCs, vocs_status);
                                            }
                                        }

                                        if(Alarm_Map.get(matter_data[0]).get(CO) < co_status){
                                            Make_Alarm(matter_data[0], CO, co_status, co_value);
                                            Alarm_Map.get(matter_data[0]).put(CO, co_status);
                                        }else{
                                            if( Alarm_Map.get(matter_data[0]).get(CO) != 0 && co_status == 0 ){
                                                Make_Alarm(matter_data[0], CO, co_status, co_value);
                                                Alarm_Map.get(matter_data[0]).put(CO, co_status);
                                            }
                                        }

                                        if(Alarm_Map.get(matter_data[0]).get(CO2) < co2_status){
                                            Make_Alarm(matter_data[0], CO2, co2_status, co2_value);
                                            Alarm_Map.get(matter_data[0]).put(CO2, co2_status);
                                        }else{
                                            if( Alarm_Map.get(matter_data[0]).get(CO2) != 0 && co2_status == 0 ){
                                                Make_Alarm(matter_data[0], CO2, co2_status, co2_value);
                                                Alarm_Map.get(matter_data[0]).put(CO2, co2_status);
                                            }
                                        }

                                        if(Alarm_Map.get(matter_data[0]).get(PM) < pm_status){
                                            Make_Alarm(matter_data[0], PM, pm_status, pm_value);
                                            Alarm_Map.get(matter_data[0]).put(PM, pm_status);
                                        }else{
                                            if( Alarm_Map.get(matter_data[0]).get(PM) != 0 && pm_status == 0 ){
                                                Make_Alarm(matter_data[0], PM, pm_status, pm_value);
                                                Alarm_Map.get(matter_data[0]).put(PM, pm_status);
                                            }
                                        }

                                    }

                                } catch (Exception e) {

                                }
                            }
                            break;

                            case "F48": {

                            }
                            break;

                            case "E48": {

                            }
                            break;

                        }

                    } else {  //연결실패
                    }
                }
            }
        });

        Log.d("Sans", "Background_Alarm_Thread 새로 만들기");
        Background_Alarm_Thread.start();
        Log.d("Sans", "Background_Alarm_Thread 시작");
    }

    public static void Stop_Background_Thread(){
        Log.d("Sans", "Stop_Background_Thread() 작동 시작");
        if(Background_Alarm_Thread != null){
            Log.d("Sans", "Background_Alarm_Thread 가 null 이  아님");
            if(Background_Alarm_Thread.isAlive()){
                Log.d("Sans", "Stop_Background_Thread() 가 동작중");
                Background_Alarm_Thread.interrupt();
                Log.d("Sans", "Background_Alarm_Thread 인터럽트 완료");
            }
        }
        Log.d("Sans", "Stop_Background_Thread() 종료");
    }

    @Override
    public void onDestroy() {
        Stop_Background_Thread();
        super.onDestroy();
    }

    public void Make_Alarm(String nickname, int matter, int status, int value){

        String status_str = "";

        switch (status){
            case 0:
            {
                status_str = "다시 좋아졌습니다.";
            }
            break;
            case 1:
            {
                status_str = "좋지 않습니다.";
            }
            break;
            case 2:
            {
                status_str = "나쁩니다.";
            }
            break;
            case 3:
            {
                status_str = "매우 나쁩니다.";
            }
            break;
        }

        String matter_str = "";
        String value_str = "";

        switch (matter){
            case VOCs:
            {
                matter_str = "VOCs";
                value_str = value + " ㎎/㎥";
            }
            break;
            case CO2:
            {
                matter_str = "CO2";
                value_str = value + " ppm";
            }
            break;
            case CO:
            {
                matter_str = "CO";
                value_str = value + " ppm";
            }
            break;
            case PM:
            {
                matter_str = "PM";
                value_str = value + " ㎍/㎥";
            }
            break;
        }

        NotificationChannel channel = new NotificationChannel("warning", "matter_warning",
                NotificationManager.IMPORTANCE_HIGH);

        // Notification과 채널 연걸
        NotificationManager mNotificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        mNotificationManager.createNotificationChannel(channel);

        // Notification 세팅
        NotificationCompat.Builder notification
                = new NotificationCompat.Builder(getApplicationContext(), "warning")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle( nickname + "의 실내 공기질이 " + status_str)
                .setContentText( matter_str + "의 값 : " + value_str);

        // id 값은 0보다 큰 양수가 들어가야 한다.
        mNotificationManager.notify(Alarm_Channel++, notification.build());
    }
}
