package stm.airble._4_main._5_setting.airble;


import static stm.airble._4_main.MainActivity.Main_Context;
import static stm.airble._4_main.MainActivity.Main_Get_Airble_Models_Status_Bool;
import static stm.airble._4_main.MainActivity.Main_Loading;
import static stm.airble._0_public.Public_Values.*;
import static stm.airble._4_main.MainActivity.Main_Refresh_Airble_Model_Array_Bool;
import static stm.airble._4_main.MainActivity.select_airble_num;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._1_home._1_HomeFragment;
import stm.airble._4_main._5_setting._5_SettingsFragment;
import stm.airble._4_main._5_setting.airble.viewpager2.Main_Setting_Airble_ViewPagerAdapter;
import stm.airble._4_main._5_setting.airble.viewpager2.recyclerview.Main_Setting_Shared_RecyclerViewAdapter;
import stm.airble._4_main._5_setting.dialog.Change_NickName_Dialog_Activity;
import stm.airble._4_main._5_setting.dialog.Change_Place_Dialog_Activity;
import stm.airble._4_main._5_setting.dialog.Change_Volume_Dialog_Activity;
import stm.airble._4_main._5_setting.dialog.Remove_Dialog_Activity;
import stm.airble._0_public.GET_RequestHttpURLConnection;

public class _51_SettingsAirbleFragment extends Fragment {

    public static Fragment Setting_Airble_Context;
    public DrawerLayout settings_airble_DrawerLayout;

    public boolean start_shared_popup_bool;
    public boolean running_popup_bool;

    ImageButton back_Button;
    TextView title_TextView;
    LinearLayout nickname_LinearLayout, information_LinearLayout, volume_LinearLayout, place_LinearLayout, shared_LinearLayout;
    TextView nickname_TextView, volume_TextView, place_TextView, shared_TextView_1, shared_TextView_2;
    Button reset_Button, remove_Button;

    public LinearLayout drawer_LinearLayout;
    ImageButton drawer_back_Button;
    TextView drawer_title_TextView;
    public ViewPager2 drawer_ViewPager2;
    public Main_Setting_Airble_ViewPagerAdapter drawer_adapter;

    public String shared_key = "", mater_email = "";
    public ArrayList<String> shared_array;

    ActivityResultLauncher<Intent> nickname_ActivityResultLauncher, volume_ActivityResultLauncher, place_ActivityResultLauncher, reset_ActivityResultLauncher, remove_ActivityResultLauncher;

    // 기기 화면 데이터 갱신용
    public Disposable setting_airble_Disposable;
    public MutableLiveData<Long> setting_airble_Mutable;
    public Observable<Long> setting_airble_Observable;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        Setting_Airble_Context = this;

        View view = inflater.inflate(R.layout.fragment_settings_airble, container, false);

        _51_Setting_Airble_Setting(view);
        Setting_Drawer(view);
        start_shared_popup_bool = false;
        running_popup_bool = false;


        return view;
    }

    @Override
    public void onResume() {
        Log.d("Sans", "Settings Airble Resume");
        super.onResume();
        Load_Airble_Status();
        Start_Setting_Airble_LiveData();
        running_popup_bool = false;
        Main_Loading.Start_Loading();

    }

    @Override
    public void onPause() {
        Log.d("Sans", "Settings Airble Pause");
        Main_Loading.Stop_Loading();
        if(!running_popup_bool){
            Stop_Setting_Airble_LiveData();
        }
        super.onPause();
    }

    public void _51_Setting_Airble_Setting(View view){
        // 아이디 맞춰주기
        {
            settings_airble_DrawerLayout = view.findViewById(R.id.settings_airble_DrawerLayout);

            back_Button = view.findViewById(R.id.back_Button);
            title_TextView = view.findViewById(R.id.settings_airble_title_TextView);
            nickname_LinearLayout = view.findViewById(R.id.settings_airble_nickname_LinearLayout);
            information_LinearLayout = view.findViewById(R.id.settings_airble_information_LinearLayout);
            volume_LinearLayout = view.findViewById(R.id.settings_airble_volume_LinearLayout);
            place_LinearLayout = view.findViewById(R.id.settings_airble_place_LinearLayout);
            shared_LinearLayout = view.findViewById(R.id.settings_airble_shared_LinearLayout);
            nickname_TextView = view.findViewById(R.id.settings_airble_nickname_TextView);
            volume_TextView = view.findViewById(R.id.settings_airble_volume_TextView);
            place_TextView = view.findViewById(R.id.settings_airble_place_TextView);
            shared_TextView_1 = view.findViewById(R.id.settings_airble_shared_TextView_1);
            shared_TextView_2 = view.findViewById(R.id.settings_airble_shared_TextView_2);
            reset_Button = view.findViewById(R.id.settings_reset_Button);
            remove_Button = view.findViewById(R.id.settings_remove_Button);

        }

        // launcher 설정
        {
            nickname_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        nickname_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getNick_Name());

                    });

            volume_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() <= 155){
                            volume_TextView.setText("0%");
                        }else{
                            volume_TextView.setText((APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() - 155) + "%");
                        }
                    });

            place_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        place_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getPlace_name());
                    });

            reset_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        // 리셋 관련 넣어주기
                        running_popup_bool = true;
                        Toast.makeText(MainActivity.Main_Context, "데이터가 초기화 되었습니다.", Toast.LENGTH_SHORT).show();
                    });

            remove_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        // 기기 지우기 넣어주기 -> 데이터 리셋 X
                        if(((MainActivity)MainActivity.Main_Context).airble_remove_bool){
                            Main_Loading.Start_Loading();
                            try{
                                String server_url = Server_Domain + "airble_test?num=42&email=" + User_Email;
                                URL url = new URL(server_url);
                                new Get_Airble_Models_Status_HttpConnection().execute(url);
                            }catch (Exception e){
                                e.printStackTrace();
                                Main_Get_Airble_Models_Status_Bool = false;
                            }
                        }
                    });
        }

        // 기본 세팅
        {
            title_TextView.setText("기기 설정");
            nickname_TextView.setText("");
            volume_TextView.setText("");
            place_TextView.setText("");
            String ssid = "airble_";
            String mac[] = APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address().split(":");
            for(int i = 0; i<mac.length; i++){
                ssid += mac[i];
            }
            APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).setSSID(ssid);
        }

        // 뒤로가기
        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_5_Setting();
            }
        });

        // 닉네임
        nickname_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()) {
                    Intent intent = new Intent(MainActivity.Main_Context, Change_NickName_Dialog_Activity.class);
                    running_popup_bool = true;
                    nickname_ActivityResultLauncher.launch(intent);
                }
            }
        });

        // 볼륨
        volume_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()) {
                    Intent intent = new Intent(MainActivity.Main_Context, Change_Volume_Dialog_Activity.class);
                    running_popup_bool = true;
                    volume_ActivityResultLauncher.launch(intent);
                    //Toast.makeText(MainActivity.Main_Context, "준비 중입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 지역
        place_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.Main_Context, Change_Place_Dialog_Activity.class);
                running_popup_bool = true;
                place_ActivityResultLauncher.launch(intent);
            }
        });

        // 데이터 리셋
        reset_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.Main_Context, Reset_Dialog_Activity.class);
                //reset_ActivityResultLauncher.launch(intent);
                Toast.makeText(MainActivity.Main_Context, "준비 중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        // 기기 지우기
        remove_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).airble_remove_bool = false;
                Intent intent = new Intent(MainActivity.Main_Context, Remove_Dialog_Activity.class);
                running_popup_bool = true;
                remove_ActivityResultLauncher.launch(intent);
            }
        });

        // 기기 주인이 아니면 할 수 없는 기능 봉인
        if(!APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()){
            reset_Button.setVisibility(View.GONE);
            place_LinearLayout.setVisibility(View.GONE);
        }
    }

    public void Setting_Drawer(View view){
        // 아이디 맞춰주기
        {
            drawer_LinearLayout = view.findViewById(R.id.settings_airble_drawer_LinearLayout);
            drawer_back_Button = view.findViewById(R.id.settings_airble_drawer_back_Button);
            drawer_ViewPager2 = view.findViewById(R.id.settings_airble_drawer_ViewPager2);
            drawer_title_TextView = view.findViewById(R.id.settings_airble_drawer_title_TextView);
            shared_array = new ArrayList<>();
        }

        // Drawer 설정
        {
            settings_airble_DrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            settings_airble_DrawerLayout.setScrimColor(Color.TRANSPARENT);
            Point size = new Point();
            ((MainActivity)MainActivity.Main_Context).getWindowManager().getDefaultDisplay().getRealSize(size);
            drawer_LinearLayout.getLayoutParams().width = size.x;
            drawer_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        // 뒤로가기
        drawer_back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_airble_DrawerLayout.closeDrawer(drawer_LinearLayout);
            }
        });

        // 기기 정보
        information_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_title_TextView.setText("기기 정보");
                drawer_ViewPager2.setCurrentItem(0, false);
                settings_airble_DrawerLayout.openDrawer(drawer_LinearLayout);
            }
        });

        // 공유하기
        shared_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if(shared_key.trim().equals("")){

                }else{
                    try{
                        Main_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=44&email=" + User_Email +"&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Running_Airble_APP_HttpConnection().execute(url);
                    }catch (Exception e){
                        Main_Loading.Stop_Loading();
                    }
                }*/

                if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()){
                    try{
                        Main_Loading.Start_Loading();
                        String server_url = Server_Domain + "airble_test?num=44&email=" + User_Email +"&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                        URL url = new URL(server_url);
                        Log.d("Sans", "server_url = " + server_url);
                        new Running_Airble_APP_HttpConnection().execute(url);
                    }catch (Exception e){
                        Check_DrawerLayout();
                        Main_Loading.Stop_Loading();
                    }
                }else{
                    settings_airble_DrawerLayout.openDrawer(drawer_LinearLayout);
                    drawer_ViewPager2.setCurrentItem(1);
                }


            }
        });
    }

    public void Load_Airble_Status(){
        try {
            String server_url = Server_Domain + "airble_test?num=44&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address() + "&email=" + User_Email;
            URL url = new URL(server_url);
            Log.d("Sans", "server_url = " + server_url);
            new Load_Airble_Status_HttpConnection().execute(url);
        } catch (Exception e) {
            Check_DrawerLayout();
            Main_Loading.Stop_Loading();
            Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void Check_DrawerLayout(){
        if(start_shared_popup_bool){
            start_shared_popup_bool = false;
            running_popup_bool = false;
            drawer_ViewPager2.setCurrentItem(1, false);
        }else{
            if(settings_airble_DrawerLayout.isDrawerOpen(drawer_LinearLayout)){
                settings_airble_DrawerLayout.closeDrawer(drawer_LinearLayout);
            }
        }
    }


    public void Start_Setting_Airble_LiveData(){
        setting_airble_Mutable = new MutableLiveData<>();
        setting_airble_Mutable.observe(this, new androidx.lifecycle.Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                try{
                    //Log.d("Sans",APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address() );
                    String server_url = Server_Domain + "airble_test?num=45&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                    URL url = new URL(server_url);
                    new Get_Airble_Models_Status_HttpConnection().execute(url);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
        setting_airble_Observable = Observable.interval(3, TimeUnit.SECONDS);

        setting_airble_Disposable = setting_airble_Observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                setting_airble_Mutable.postValue(aLong);
            }
        });
    }
    public void Stop_Setting_Airble_LiveData(){
        if(setting_airble_Disposable != null){
            if(!setting_airble_Disposable.isDisposed()){
                setting_airble_Disposable.dispose();
            }
        }
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
                Log.d("Sans", data);
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                switch (code){
                    case "S425":
                    {
                        String email[] = data.split(",");
                        shared_array.clear();

                        for(int i = 1; i < email.length; i++){
                            shared_array.add(email[i]);
                        }

                        drawer_adapter = new Main_Setting_Airble_ViewPagerAdapter((MainActivity)MainActivity.Main_Context);
                        drawer_ViewPager2.setAdapter(drawer_adapter);
                        drawer_ViewPager2.setUserInputEnabled(false);
                        drawer_ViewPager2.setOffscreenPageLimit(2);
                        Check_DrawerLayout();
                        Main_Loading.Stop_Loading();
                    }
                    break;

                    case "S44":
                    {
                        String status[] = data.split(",");
                        APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).setBrightness(Integer.parseInt(status[1]));
                        APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).setVolume(Integer.parseInt(status[2]));

                        nickname_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getNick_Name());
                        if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() <= 155){
                            volume_TextView.setText("0%");
                        }else{
                            volume_TextView.setText((APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getVolume() - 155) + "%");
                        }
                        place_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getPlace_name());

                        shared_key = status[4].trim();
                        mater_email = status[5].trim();
                        if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()){
                            try{
                                String server_url = Server_Domain + "airble_test?num=425&email=" + User_Email +"&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                                URL url = new URL(server_url);
                                Log.d("Sans", "server_url = " + server_url);
                                new Load_Airble_Status_HttpConnection().execute(url);
                            }catch (Exception e){

                            }
                        }else{
                            drawer_adapter = new Main_Setting_Airble_ViewPagerAdapter((MainActivity)MainActivity.Main_Context);
                            drawer_ViewPager2.setAdapter(drawer_adapter);
                            drawer_ViewPager2.setUserInputEnabled(false);
                            drawer_ViewPager2.setOffscreenPageLimit(1);
                            Check_DrawerLayout();
                            Main_Loading.Stop_Loading();
                        }

                    }
                    break;

                    case "E425":
                    case "F425":
                    case "F44":
                    {
                        Check_DrawerLayout();
                        Main_Loading.Stop_Loading();
                        Toast.makeText(MainActivity.Main_Context, "기기 정보를 불러오지 못하였습니다. 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case "FF44":
                    {
                        Check_DrawerLayout();
                        Main_Loading.Stop_Loading();
                        ((MainActivity)MainActivity.Main_Context).Change_Fragment_5_Setting();
                    }
                    break;

                }

            } else {  //연결실패
                Check_DrawerLayout();
                Main_Loading.Stop_Loading();
                Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //서버에 연결하는 코딩
    private class Running_Airble_APP_HttpConnection extends AsyncTask<URL, Integer, String> {

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

                    case "S422":
                    {
                        Log.d("Sans", data.split(",")[1].trim());
                        Toast.makeText(MainActivity.Main_Context, "1회용 공유키가 발급되었습니다.", Toast.LENGTH_SHORT).show();
                        shared_TextView_1.setText("공유키");
                        shared_TextView_2.setText(data.split(",")[1].trim());

                    }
                    break;

                    case "S425":
                    {
                        String email[] = data.split(",");
                        ((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.clear();

                        for(int i = 1; i < email.length; i++){
                            ((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.add(email[i]);
                        }

                        if(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.size() == 0){
                            drawer_adapter.shared_page.shared_RecyclerView.setVisibility(View.GONE);
                            drawer_adapter.shared_page.null_shared_TextView.setVisibility(View.VISIBLE);
                        }else{
                            drawer_adapter.shared_page.shared_RecyclerView.setVisibility(View.VISIBLE);
                            drawer_adapter.shared_page.null_shared_TextView.setVisibility(View.GONE);
                        }

                        if(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_key.trim().equals("0000000000")){
                            drawer_adapter.shared_page.shared_TextView.setText("공유하기");
                            drawer_adapter.shared_page.shared_key_TextView.setText("");

                        }else{
                            drawer_adapter.shared_page.shared_TextView.setText("공유키");
                            drawer_adapter.shared_page.shared_key_TextView.setText(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_key.trim());
                        }
                        Check_DrawerLayout();
                        Main_Loading.Stop_Loading();


                        drawer_title_TextView.setText("기기 공유");
                        drawer_ViewPager2.setCurrentItem(1,false);
                        settings_airble_DrawerLayout.openDrawer(drawer_LinearLayout);

                        try{
                            drawer_adapter.shared_page.shared_adapter.notifyDataSetChanged();
                        }catch ( Exception e ){
                            drawer_adapter.shared_page.shared_adapter = new Main_Setting_Shared_RecyclerViewAdapter(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array);
                            drawer_adapter.shared_page.shared_RecyclerView.setAdapter(drawer_adapter.shared_page.shared_adapter);
                            drawer_adapter.shared_page.shared_RecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.Main_Context));
                        }
                    }
                    break;

                    case "E425":
                    case "F425":
                    {
                        Check_DrawerLayout();
                        Main_Loading.Stop_Loading();
                        Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case "S44":
                    {
                        String status[] = data.split(",");

                        shared_key = status[4].trim();

                        try{
                            String server_url = Server_Domain + "airble_test?num=425&email=" + User_Email +"&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                            URL url = new URL(server_url);
                            Log.d("Sans", "server_url = " + server_url);
                            new Running_Airble_APP_HttpConnection().execute(url);
                        }catch (Exception e){

                        }
                    }
                    break;

                }

            } else {  //연결실패
                Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class Get_Airble_Models_Status_HttpConnection extends AsyncTask<URL, Integer, String> {
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
                Log.d("Sans", "Get_Airble_Models_Status_HttpConnection data = " + data);
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                //String code = data;
                switch (code){
                    // Airble 현재 데이터 가져오기
                    case "S42":
                    {
                        try{
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            String values[] = data.split(",,");
                            for(int i = 1; i< values.length; i++){
                                String value[] = values[i].split(",");
                                if(Integer.parseInt(value[1]) == 1){
                                    APP_Airble_Model_Array.get(i-1).setOwner(true);
                                }else{
                                    APP_Airble_Model_Array.get(i-1).setOwner(false);
                                }
                                APP_Airble_Model_Array.get(i-1).setSSID(value[3]);
                                APP_Airble_Model_Array.get(i-1).setNick_Name(value[4]);
                                APP_Airble_Model_Array.get(i-1).setUpdate_date(LocalDateTime.parse(value[5].split("\\.")[0], dateTimeFormatter));
                            }

                            Main_Get_Airble_Models_Status_Bool = false;
                            if(select_airble_num >= APP_Airble_Model_Array.size()){
                                select_airble_num = APP_Airble_Model_Array.size() - 1;
                            }
                            ((MainActivity) Main_Context).Change_Fragment_5_Setting();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;

                    case "F42":
                    case "E42":
                    {
                        // 서버 오류
                        Toast.makeText(Main_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case "S45":
                    {
                        Log.d("Sans", "로그인 갱신");
                    }
                    break;

                    default:
                    {
                        Toast.makeText(Main_Context, "에러코드 : " + code, Toast.LENGTH_SHORT).show();
                    }

                }

            } else {  //연결실패
                Toast.makeText(Main_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
