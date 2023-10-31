package stm.airble._4_main;

import static stm.airble._0_public.Public_Values.*;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import stm.airble.R;
import stm.airble._4_main._1_home._1_HomeFragment;
import stm.airble._4_main._2_graph._2_GraphFragment;
import stm.airble._4_main._3_information._31_InformationPageFragment;
import stm.airble._4_main._3_information._3_InformationFragment;
import stm.airble._4_main._4_faq._41_FaqPageFragment;
import stm.airble._4_main._4_faq._4_FaqFragment;
import stm.airble._4_main._5_setting._5_SettingsFragment;
import stm.airble._4_main._5_setting.airble._51_SettingsAirbleFragment;
import stm.airble._4_main._5_setting.terms._52_SettingsTermsFragment;
import stm.airble._0_public.Airble_Model;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;
import stm.airble._3_registration.RegistrationActivity;


public class MainActivity extends AppCompatActivity {
    public static Context Main_Context;

    public static Loading_ProgressDialog Main_Loading;
    public static boolean Main_Refresh_Airble_Model_Array_Bool;
    public static boolean Main_Get_Airble_Models_Status_Bool;

    //하단 메뉴 버튼 관련 선언
    LinearLayout home_LinearLayout;
    ImageView home_ImageView;
    TextView home_TextView;
    LinearLayout graph_LinearLayout;
    ImageView graph_ImageView;
    TextView graph_TextView;
    LinearLayout information_LinearLayout;
    ImageView information_ImageView;
    TextView information_TextView;
    LinearLayout faq_LinearLayout;
    ImageView faq_ImageView;
    TextView faq_TextView;
    LinearLayout settings_LinearLayout;
    ImageView settings_ImageView;
    TextView settings_TextView;

    public static final int Main_Fragment_ID = R.id.main_container;
    public FragmentTransaction Main_FragmentTransaction;
    public _1_HomeFragment _1_Home_Fragment;
    public _2_GraphFragment _2_Graph_Fragment;
    public _3_InformationFragment _3_Information_Fragment;
    public _31_InformationPageFragment _31_Information_Page_Fragment;
    public _4_FaqFragment _4_Faq_Fragment;
    public _41_FaqPageFragment _41_Faq_Page_Fragment;
    public _5_SettingsFragment _5_Settings_Fragment;
    public _51_SettingsAirbleFragment _51_Settings_Airble_Fragment;
    public _52_SettingsTermsFragment _52_Settings_Terms_Fragment;

    public static int select_airble_num;
    public static int select_setting_airble_num;
    public static int select_setting_airble_viewpager_page_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main_Context = this;
        Main_Loading = new Loading_ProgressDialog(Main_Context);
        Main_Refresh_Airble_Model_Array_Bool = false;
        Main_Get_Airble_Models_Status_Bool = false;

        Main_Setting();

    }

    public void Main_Setting() {
        // 아이디 맞춰주기
        {
            frame_area_View = findViewById(R.id.main_frame_area_View);
            home_LinearLayout = findViewById(R.id.main_home_LinearLayout);
            home_ImageView = findViewById(R.id.main_home_ImageView);
            home_TextView = findViewById(R.id.main_home_TextView);
            graph_LinearLayout = findViewById(R.id.main_graph_LinearLayout);
            graph_ImageView = findViewById(R.id.main_graph_ImageView);
            graph_TextView = findViewById(R.id.main_graph_TextView);
            information_LinearLayout = findViewById(R.id.main_information_LinearLayout);
            information_ImageView = findViewById(R.id.main_information_ImageView);
            information_TextView = findViewById(R.id.main_information_TextView);
            faq_LinearLayout = findViewById(R.id.main_faq_LinearLayout);
            faq_ImageView = findViewById(R.id.main_faq_ImageView);
            faq_TextView = findViewById(R.id.main_faq_TextView);
            settings_LinearLayout = findViewById(R.id.main_user_settings_LinearLayout);
            settings_ImageView = findViewById(R.id.main_user_settings_ImageView);
            settings_TextView = findViewById(R.id.main_user_settings_TextView);

            Main_FragmentTransaction = getSupportFragmentManager().beginTransaction();
        }

        // 하단 메뉴 셋팅
        {
            home_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Change_Fragment_1_Home();
                }
            });

            graph_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Change_Fragment_2_Graph(VOCs);
                }
            });

            information_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Main_Context, "다음 버전 업데이트를 기다려주세요.", Toast.LENGTH_SHORT).show();
                    //Change_Fragment_3_Information();
                }
            });

            faq_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Main_Context, "다음 버전 업데이트를 기다려주세요.", Toast.LENGTH_SHORT).show();
                    //Change_Fragment_4_Faq();
                }
            });

            settings_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_setting_airble_viewpager_page_num = 400 * APP_Airble_Model_Array.size();
                    Change_Fragment_5_Setting();
                }
            });
        }


        if (Fragment_Page == 0) {
            Change_Fragment_1_Home();
        } else {
            switch (Fragment_Page) {
                case HOME_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_1_Home();
                }
                break;

                case GRAPH_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_2_Graph(VOCs);
                }
                break;

                case INFORMATION_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_3_Information();
                }
                break;

                case INFORMATION_PAGE_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_31_Information_Page();
                }
                break;

                case FAQ_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_4_Faq();
                }
                break;

                case FAQ_PAGE_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_41_Faq_Page();
                }
                break;

                case SETTINGS_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_5_Setting();
                }
                break;

                case SETTINGS_AIRBLE_FRAGMENT: {
                    if (((MainActivity) MainActivity.Main_Context)._51_Settings_Airble_Fragment.drawer_ViewPager2.getCurrentItem() == 1) {

                    } else {
                        Fragment_Page = 0;
                        Change_Fragment_51_Setting();
                    }
                }
                break;

                case SETTINGS_TERMS_FRAGMENT: {
                    Fragment_Page = 0;
                    Change_Fragment_52_Setting();
                }
                break;
            }
        }
    }

    public void Change_Fragment_1_Home() {
        if (Fragment_Page != HOME_FRAGMENT) {
            Fragment_Page = HOME_FRAGMENT;
            home_LinearLayout.setAlpha(0.5f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(1f);

            _1_Home_Fragment = new _1_HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _1_Home_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_2_Graph(int matter) {
        if (Fragment_Page != GRAPH_FRAGMENT) {
            Fragment_Page = GRAPH_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(0.5f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(1f);

            _2_Graph_Fragment = new _2_GraphFragment();
            _2_Graph_Fragment.select_matter = matter;
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _2_Graph_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_3_Information() {
        if (Fragment_Page != INFORMATION_FRAGMENT) {
            Fragment_Page = INFORMATION_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(0.5f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(1f);

            _3_Information_Fragment = new _3_InformationFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _3_Information_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_31_Information_Page() {
        if (Fragment_Page != INFORMATION_PAGE_FRAGMENT) {
            Fragment_Page = INFORMATION_PAGE_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(0.5f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(1f);

            _31_Information_Page_Fragment = new _31_InformationPageFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _31_Information_Page_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_4_Faq() {
        if (Fragment_Page != FAQ_FRAGMENT) {
            Fragment_Page = FAQ_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(0.5f);
            settings_LinearLayout.setAlpha(1f);

            _4_Faq_Fragment = new _4_FaqFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _4_Faq_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_41_Faq_Page() {
        if (Fragment_Page != FAQ_PAGE_FRAGMENT) {
            Fragment_Page = FAQ_PAGE_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(0.5f);
            settings_LinearLayout.setAlpha(1f);

            _41_Faq_Page_Fragment = new _41_FaqPageFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _41_Faq_Page_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_5_Setting() {
        if (Fragment_Page != SETTINGS_FRAGMENT) {
            Fragment_Page = SETTINGS_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(0.5f);

            _5_Settings_Fragment = new _5_SettingsFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _5_Settings_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_51_Setting() {
        if (Fragment_Page != SETTINGS_AIRBLE_FRAGMENT) {
            Fragment_Page = SETTINGS_AIRBLE_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(0.5f);

            _51_Settings_Airble_Fragment = new _51_SettingsAirbleFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _51_Settings_Airble_Fragment).commit();
        } else {

        }
    }

    public void Change_Fragment_52_Setting() {
        if (Fragment_Page != SETTINGS_TERMS_FRAGMENT) {
            Fragment_Page = SETTINGS_TERMS_FRAGMENT;
            home_LinearLayout.setAlpha(1f);
            graph_LinearLayout.setAlpha(1f);
            information_LinearLayout.setAlpha(1f);
            faq_LinearLayout.setAlpha(1f);
            settings_LinearLayout.setAlpha(0.5f);

            _52_Settings_Terms_Fragment = new _52_SettingsTermsFragment();
            getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, _52_Settings_Terms_Fragment).commit();
        } else {

        }
    }


    // 각각의 페이지 선언
    public _41_FaqPageFragment _41_faqPageFragment;

    // 화면 View
    public View frame_area_View;

    public static String faqFragment_keyword;


    public boolean airble_remove_bool = false;
    public boolean logout_bool = false;
    public static boolean device_refresh_bool = false;

    @Override
    protected void onResume() {
        Log.d("Sans", "Main onResume");
        super.onResume();
        Start_Get_Device_Data();
    }

    @Override
    protected void onPause() {
        Log.d("Sans", "Main onPause");
        super.onPause();
        Stop_Get_Device_Data();
    }

    @Override
    public void onDestroy() {
        Log.d("Sans", "Main onDestroy");
        super.onDestroy();
        Stop_Get_Device_Data();
    }

    public static Get_Device_Data_Thread get_device_data_thread;

    public void Start_Get_Device_Data() {
        Log.d("Sans", "Start_Get_Device_Data");
        if (get_device_data_thread == null) {
            get_device_data_thread = new Get_Device_Data_Thread();
        } else if (get_device_data_thread.isInterrupted()) {
            get_device_data_thread = new Get_Device_Data_Thread();
        } else if (get_device_data_thread.isAlive()) {
            Stop_Get_Device_Data();
            get_device_data_thread = new Get_Device_Data_Thread();
        } else {
            get_device_data_thread = new Get_Device_Data_Thread();

        }
        get_device_data_thread.start();
    }

    public void Stop_Get_Device_Data() {
        Log.d("Sans", "Stop_Get_Device_Data");
        if (get_device_data_thread != null) {
            if (!get_device_data_thread.isInterrupted()) {
                get_device_data_thread.interrupt();
            }
        }
    }

    public static class Get_Device_Data_Thread extends Thread {
        boolean Get_Device_Data_Bool = true;

        public Get_Device_Data_Thread() {

        }

        @Override
        public void run() {
            while (true) {
                if (isInterrupted()) {
                    break;
                }

                try {
                    if (Get_Device_Data_Bool) {
                        Get_Device_Data_Bool = false;
                        String server_url = Server_Domain + "airble_test?num=43&email=" + User_Email;
                        URL url = new URL(server_url);
                        new Get_Device_Data_HttpConnection().execute(url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //서버에 연결하는 코딩
        private class Get_Device_Data_HttpConnection extends AsyncTask<URL, Integer, String> {

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

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Get_Device_Data_Bool = true;
                    }
                }, 1000);

                if (data != null) { //연결성공
                    String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                    //String code = data;
                    switch (code) {
                        // Airble 현재 데이터 가져오기
                        case "S43": {
                            try {
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String values[] = data.split(",,");
                                ArrayList<Airble_Model> Airble_Model_List = new ArrayList<>();
                                for (int i = 1; i < values.length; i++) {
                                    String value[] = values[i].split(",");
                                    Airble_Model airble = new Airble_Model();
                                    airble.setMAC_Address(value[0].trim());
                                    airble.setCO(Integer.parseInt(value[1].trim()));
                                    airble.setCO2(Integer.parseInt(value[2].trim()));
                                    airble.setVOCs(Integer.parseInt(value[3].trim()));
                                    airble.setPM(Integer.parseInt(value[4].trim()));
                                    airble.setTemp(Integer.parseInt(value[5].trim()));
                                    airble.setHumi(Integer.parseInt(value[6].trim()));
                                    airble.setPlace_code(value[7].trim());
                                    airble.setPlace_name(value[8].trim());
                                    airble.setOutside_Temp(Float.parseFloat(value[9].trim()));
                                    airble.setOutside_Humi(Float.parseFloat(value[10].trim()));
                                    airble.setOutside_PM(Integer.parseInt(value[11].trim()));
                                    airble.setUpdate_date(LocalDateTime.parse(value[12].trim().split("\\.")[0], dateTimeFormatter));

                                    Airble_Model_List.add(airble);
                                }

                                if (APP_Airble_Model_Array.size() == Airble_Model_List.size()) {
                                    for (int i = 0; i < Airble_Model_List.size(); i++) {
                                        if (!Main_Refresh_Airble_Model_Array_Bool) {
                                            if (APP_Airble_Model_Array.get(i).getMAC_Address().equals(Airble_Model_List.get(i).getMAC_Address())) {
                                                APP_Airble_Model_Array.get(i).setCO(Airble_Model_List.get(i).getCO());
                                                APP_Airble_Model_Array.get(i).setCO2(Airble_Model_List.get(i).getCO2());
                                                APP_Airble_Model_Array.get(i).setVOCs(Airble_Model_List.get(i).getVOCs());
                                                APP_Airble_Model_Array.get(i).setPM(Airble_Model_List.get(i).getPM());
                                                APP_Airble_Model_Array.get(i).setTemp(Airble_Model_List.get(i).getTemp());
                                                APP_Airble_Model_Array.get(i).setHumi(Airble_Model_List.get(i).getHumi());
                                                APP_Airble_Model_Array.get(i).setPlace_code(Airble_Model_List.get(i).getPlace_code());
                                                APP_Airble_Model_Array.get(i).setPlace_name(Airble_Model_List.get(i).getPlace_name());
                                                APP_Airble_Model_Array.get(i).setOutside_Temp(Airble_Model_List.get(i).getOutside_Temp());
                                                APP_Airble_Model_Array.get(i).setOutside_Humi(Airble_Model_List.get(i).getOutside_Humi());
                                                APP_Airble_Model_Array.get(i).setOutside_PM(Airble_Model_List.get(i).getOutside_PM());
                                                APP_Airble_Model_Array.get(i).setUpdate_date(Airble_Model_List.get(i).getUpdate_date());
                                            } else {
                                                Main_Refresh_Airble_Model_Array_Bool = true;
                                            }
                                        }
                                    }
                                } else {
                                    Main_Refresh_Airble_Model_Array_Bool = true;
                                }

                                if (Main_Refresh_Airble_Model_Array_Bool) {
                                    Log.d("Sans", "Get_Device_Data_HttpConnection, 기기 변화발생!");
                                    APP_Airble_Model_Array.clear();
                                    APP_Airble_Model_Array.addAll(Airble_Model_List);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                        case "F43": {
                            APP_Airble_Model_Array.clear();
                            Toast.makeText(MainActivity.Main_Context, "기기리스트가 변경되었습니다.\n등록된 기기가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.Main_Context, RegistrationActivity.class);
                            ((MainActivity) MainActivity.Main_Context).startActivity(intent);
                            ((MainActivity) MainActivity.Main_Context).finish();
                        }
                        break;

                    }

                } else {  //연결실패

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(Fragment_Page != HOME_FRAGMENT){
            switch (Fragment_Page){
                case GRAPH_FRAGMENT:
                case INFORMATION_FRAGMENT:
                case FAQ_FRAGMENT:
                case SETTINGS_FRAGMENT:
                {
                    Change_Fragment_1_Home();
                }
                break;

                case INFORMATION_PAGE_FRAGMENT:
                {
                    Change_Fragment_3_Information();
                }
                break;

                case FAQ_PAGE_FRAGMENT:
                {
                    if(_41_Faq_Page_Fragment.faq_ViewPager2.getCurrentItem() == 0){
                        Change_Fragment_4_Faq();
                    }else{
                        _41_Faq_Page_Fragment.faq_ViewPager2.setCurrentItem(0);
                    }
                }
                break;

                case SETTINGS_AIRBLE_FRAGMENT:
                {
                    if(_51_Settings_Airble_Fragment.settings_airble_DrawerLayout.isDrawerOpen(_51_Settings_Airble_Fragment.drawer_LinearLayout)){
                        _51_Settings_Airble_Fragment.settings_airble_DrawerLayout.closeDrawer(_51_Settings_Airble_Fragment.drawer_LinearLayout);
                    }else{
                        Change_Fragment_5_Setting();
                    }
                }
                break;
                case SETTINGS_TERMS_FRAGMENT:
                {
                    if(_52_Settings_Terms_Fragment.settings_terms_DrawerLayout.isDrawerOpen(_52_Settings_Terms_Fragment.settings_terms_drawer)){
                        _52_Settings_Terms_Fragment.settings_terms_DrawerLayout.closeDrawer(_52_Settings_Terms_Fragment.settings_terms_drawer);
                    }else {
                        Change_Fragment_5_Setting();
                    }
                }
                break;

            }
        }else {
            if(!Close_App_bool){
                Toast.makeText(MainActivity.this, "뒤로가기를 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                System_Handler.sendEmptyMessageDelayed(CLOSE_APP, 1500);
                Close_App_bool = true;
            }else{
                System_Handler.removeMessages(CLOSE_APP);
                super.onBackPressed();
            }
        }

    }
}



/*

public class MainActivity extends Public_AppCompatActivity {

    public static Context Main_Context;



    // 각각의 페이지 선언
    public FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    public _1_HomeFragment _1_homeFragment;
    _2_GraphFragment _2_graphFragment;
    _3_InformationFragment _3_informationFragment;
    _31_InformationPageFragment _31_informationPageFragment;
    _4_FaqFragment _4_faqFragment;
    public _41_FaqPageFragment _41_faqPageFragment;
    public _5_SettingsFragment _5_settingsFragment;
    public _51_SettingsAirbleFragment _51_settingsAirble_Fragment;
    _52_SettingsTermsFragment _52_settingsTermsFragment;

    // 화면 View
    public View frame_area_View;


    public static int homeFragment_start_num;
    public static int graphFragment_keyword;
    public static String faqFragment_keyword;

    public static int select_airble_num;
    public static int select_setting_airble_num;

    public boolean airble_remove_bool = false;
    public boolean logout_bool = false;
    public static boolean device_refresh_bool = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main_Context = this;
        homeFragment_start_num = 0;
        //Start_Loading(this);

        // 페이지 생성 및 초기화
        {
            select_airble_num = 0;
            select_setting_airble_num = 0;
            _1_homeFragment = new _1_HomeFragment();
            _2_graphFragment = new _2_GraphFragment();
            _3_informationFragment = new _3_InformationFragment();
            _31_informationPageFragment = new _31_InformationPageFragment();
            _4_faqFragment = new _4_FaqFragment();
            _41_faqPageFragment = new _41_FaqPageFragment();
            _5_settingsFragment = new _5_SettingsFragment();
            _51_settingsAirble_Fragment = new _51_SettingsAirbleFragment();
            _52_settingsTermsFragment = new _52_SettingsTermsFragment();
        }



        // Disaable 설정
        {

        }



    }

    @Override
    public void onResume(){
        super.onResume();

        try {
            if(APP_Airble_Data_Thread != null){
                if(APP_Airble_Data_Thread.isAlive()){
                    APP_Airble_Data_Thread.interrupt();
                }
            }
            Start_APP_Thread();

        }catch (Exception e){

        }


}
*/