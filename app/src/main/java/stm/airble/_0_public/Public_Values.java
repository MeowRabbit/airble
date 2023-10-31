package stm.airble._0_public;

import static stm.airble._4_main.MainActivity.device_refresh_bool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import stm.airble._1_splash.SplashActivity;
import stm.airble._4_main.MainActivity;

public class Public_Values {

    public static boolean loop_page_boolean = false;

    /**
     * 페이지 공용
     */
    public static final int MAX_DEVICE_COUNT = 20;
    public static final String Server_Domain = "http://airble.stmtek.co.kr/";
    //public static final String Server_Domain = "http://192.168.0.36:8080/";
    public static ArrayList<Airble_Model> APP_Airble_Model_Array = new ArrayList<>();   // 사용자 기기 리스트
    // 기본 팝업 띄우기
    public static void Start_Popup(Context context, String title, String content){
        Intent intent = new Intent(context,Popup_Dialog_Activity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }


    /**
     *  현제 페이지 구분 관련
     */
    public static int Fragment_Page = 0;
    public static final int HOME_FRAGMENT = 1;
    public static final int GRAPH_FRAGMENT = 2;
    public static final int INFORMATION_FRAGMENT = 3;
    public static final int INFORMATION_PAGE_FRAGMENT = 31;
    public static final int FAQ_FRAGMENT = 4;
    public static final int FAQ_PAGE_FRAGMENT = 41;
    public static final int SETTINGS_FRAGMENT = 5;
    public static final int SETTINGS_AIRBLE_FRAGMENT = 51;
    public static final int SETTINGS_TERMS_FRAGMENT = 52;

    /**
     *  센서 관련 값
     */
    public static final int VOCs = 0;
    public static final int CO2 = 1;
    public static final int CO = 2;
    public static final int PM = 3;
    public static final int TEMPERATURE = 4;
    public static final int HUMIDITY = 5;
    public static final int OUTSIDE_DATA = 6;

    public static final int PM_MAX = 100;
    public static final int PM_GOOD = 15;
    public static final int PM_SOSO = 35;
    public static final int PM_BAD = 75;

    public static final int VOCs_MAX = 3000;
    public static final int VOCs_GOOD = 220;
    public static final int VOCs_SOSO = 660;
    public static final int VOCs_BAD = 2200;

    public static final int CO_MAX = 300;
    public static final int CO_GOOD = 100;
    public static final int CO_SOSO = 200;
    public static final int CO_BAD = 250;

    public static final int CO2_MAX = 5000;
    public static final int CO2_GOOD = 2000;
    public static final int CO2_SOSO = 4000;
    public static final int CO2_BAD = 5000;

    public static final int TEMPERATURE_MAX = 1000;

    public static final int HUMIDITY_MAX = 1000;

    /**
     * 시스템 관련
     */
    public static SharedPreferences APP_Shared_Preferences = null;                  // 간단한 데이터 저장용
    public static SharedPreferences.Editor APP_Shared_Preferences_Editor = null;
    public static boolean First_Start_bool = false; // APP 첫 시작 유무 ( false = 첫시작, true = 실행한 적이 있음 )
    public static String User_Email = "";           // 유저의 이메일 = 아이디
    public static String User_Nick_Name = "";       // 유저의 닉네임
    public static String Device_Page_Num = "";      // 종료 전 기기들의 페이지 상태 ( mac_1,페이지번호,,mac2... )

    //네트워크
    public static ConnectivityManager APP_Connectivity_Manager;
    public static NetworkInfo APP_Network_Info;
    public static WifiManager APP_Wifi_Manager;
    public static WifiInfo APP_Wifi_Info;

    public static List<Integer> Frequency_2G;
    public static List<Integer> Frequency_5G;

    public static void Start_Settings(Context context){
        // 데이터 확인
        {
            // 1. 초기화
            Close_App_bool = false;
            APP_Shared_Preferences = context.getSharedPreferences("Airble", Activity.MODE_PRIVATE);
            APP_Shared_Preferences_Editor = APP_Shared_Preferences.edit();
            Fragment_Page = 0;

            // 2. 저장해 둔 값 불러오기
            First_Start_bool = APP_Shared_Preferences.getBoolean("First_Start_bool", false);
            User_Email = APP_Shared_Preferences.getString("User_Email", "");
            User_Nick_Name = APP_Shared_Preferences.getString("User_Nick_Name", "");
            Device_Page_Num = APP_Shared_Preferences.getString("Device_Page_Num", "");

        }

        // 네트워크 셋팅
        {
            // 1. 초기화
            APP_Connectivity_Manager = context.getSystemService(ConnectivityManager.class);
            APP_Wifi_Manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            Integer[] _2G = {2412, 2417, 2422, 2427, 2432, 2437, 2442, 2447, 2452, 2457, 2462, 2467, 2472, 2484};
            Integer[] _5G = {
                    // U-NII1 (Low)
                    5180, 5200, 5220, 5240,
                    // U-NII2 (Mid)
                    5260, 5280, 5300, 5320,
                    // U-NII3 (High)
                    5745, 5765, 5785, 5805,
                    // -
                    5825,
                    // U-NII2 Extended
                    5500, 5520, 5540, 5560, 5580, 5600, 5620, 5640, 5660, 5680, 5700
            };
            Frequency_2G = new ArrayList<>(Arrays.asList(_2G));
            Frequency_5G = new ArrayList<>(Arrays.asList(_5G));
        }
    }

    public static void Setting_Devices_Page(){
        Log.d("Sams", Device_Page_Num);
        String[] devices = Device_Page_Num.split(",,");
        Log.d("Sams", "devices.length = " + devices.length);
        String device_mac = "";
        for(int i = 0; i < devices.length; i++){
            device_mac = devices[i].split(",")[0];
            for(int j = 0; j < APP_Airble_Model_Array.size(); j++){
                if(APP_Airble_Model_Array.get(j).getMAC_Address().equals(device_mac)){
                    APP_Airble_Model_Array.get(j).setPage_num(Integer.parseInt(devices[i].split(",")[1]));
                }
            }
        }

        for(int i = 0; i < APP_Airble_Model_Array.size(); i++){
            if(APP_Airble_Model_Array.get(i).getPage_num() <= 0){
                APP_Airble_Model_Array.get(i).setPage_num(1);
            }
        }
    }

    public static void Save_Device_Page(){
        String device_page = "";
        Log.d("Sans", Device_Page_Num);
        for(int i = 0; i < APP_Airble_Model_Array.size(); i++){
            device_page += APP_Airble_Model_Array.get(i).getMAC_Address() + "," + APP_Airble_Model_Array.get(i).getPage_num() + ",,";
        }
        Device_Page_Num = device_page;
        APP_Shared_Preferences_Editor.putString("Device_Page_Num", Device_Page_Num);
        APP_Shared_Preferences_Editor.commit();
    }

    // 기기를 추가 할 때
    public static boolean Add_Airble(String Airble_MAC, String Airble_NickName){
        try{
            APP_Shared_Preferences_Editor.commit();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    // 옵션에서 유저 정보 설정할 때
    public static boolean Setting_User(String email, String nick_name){
        try{
            User_Email = email;
            User_Nick_Name = nick_name;

            APP_Shared_Preferences_Editor.putString("User_Email", User_Email);
            APP_Shared_Preferences_Editor.putString("User_Nick_Name", User_Nick_Name);
            APP_Shared_Preferences_Editor.commit();

            return true;
        }catch (Exception e){
            return false;
        }

    }

    // 리셋 Airble 기기 리스트들
    public static void Reset_Airble_Model_Array(Context context){
//        Loading_ProgressDialog.Start_Loading(context);
        try{
            String server_url = Server_Domain + "airble_test?num=42&email=" + User_Email;
            URL url = new URL(server_url);
            new Reset_Airble_HttpConnection().execute(url);
        }catch (Exception e){
//            Loading_ProgressDialog.Stop_Loading();
        }
    }

    //서버에 연결하는 코딩
    private static class Reset_Airble_HttpConnection extends AsyncTask<URL, Integer, String> {

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
                Log.d("Sans", "data = " + data);
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                //String code = data;
                switch (code){
                    // 유저 로그인
                    case "S42":
                    {
                        APP_Airble_Model_Array.clear();

                        String values[] = data.split(",,");
                        for(int i = 1; i< values.length; i++){
                            String value[] = values[i].split(",");

                            Airble_Model airble = new Airble_Model();
                            airble.setMAC_Address(value[0]);
                            if(Integer.parseInt(value[1]) == 1){
                                airble.setOwner(true);
                            }else{
                                airble.setOwner(false);
                            }
                            airble.setSSID(value[3]);
                            airble.setNick_Name(value[4]);

                            APP_Airble_Model_Array.add(airble);
                        }
                        device_refresh_bool = false;

//                        Loading_ProgressDialog.Stop_Loading();
                        switch (Fragment_Page){
                            case HOME_FRAGMENT:
//                                Loading_ProgressDialog.Stop_Loading();
                                ((MainActivity)MainActivity.Main_Context).Change_Fragment_1_Home();
                                break;

                            case SETTINGS_FRAGMENT:
                                ((MainActivity)MainActivity.Main_Context).airble_remove_bool = false;
                                ((MainActivity)MainActivity.Main_Context).Change_Fragment_5_Setting();

                                break;
                        }
                    }
                    break;

                    case "F42":{

                    }
                    break;

                    case "E42":
                    {
                        // 서버 오류
//                        Loading_ProgressDialog.Stop_Loading();
                        Toast.makeText(SplashActivity.Splash_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    default:{
//                        Loading_ProgressDialog.Stop_Loading();
                    }

                }

            } else {  //연결실패
                Log.d("Sans", "실패 ");
//                Loading_ProgressDialog.Stop_Loading();
            }
        }
    }


    // 앱 종료 관련
    public static final int CLOSE_APP = 1;

    public static final int CLOSE_APP_Interval = 1500;

    public static boolean Close_App_bool = false; // APP 닫기 확인 ( false = 닫기버튼 한번 누름, true = 닫기버튼 2번 누름 )

    @SuppressLint("HandlerLeak")
    public static Handler System_Handler = new Handler(){
        @Override
        public void handleMessage(Message message){
            super.handleMessage(message);
            switch (message.what){
                case CLOSE_APP: // CLOSE_APP_Interval 시간에 맞춰 호출되면 false 로 초기화
                {
                    Close_App_bool = false;
                }
                break;
            }
        }
    };

    /**
     * 로딩 관련
     */
    /*
    private static Loading_ProgressDialog loading_progressDialog;
    public static void Start_Loading(Context context){
        loading_progressDialog = new Loading_ProgressDialog(context);
        loading_progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading_progressDialog.setCancelable(false);
        loading_progressDialog.show();
    }

    public static void Stop_Loading(){
        try{
            loading_progressDialog.cancel();
        }catch (Exception e){

        }
    }

     */

    /**
     * 그래프 페이지 관련
     */
    public static final int GRAPH_TYPE_DAY = 0;
    public static final int GRAPH_TYPE_MONTH = 1;
}
