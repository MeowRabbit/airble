package stm.airble._5_add_airble;

import static stm.airble._0_public.Public_Values.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import stm.airble.R;
import stm.airble._5_add_airble.viewpager2.AddAirble_ViewPagerAdapter;
import stm.airble._5_add_airble.dialog.Add_Cancel_Dialog_Activity;
import stm.airble._5_add_airble.dialog.Add_End_Dialog_Activity;
import stm.airble._5_add_airble.dialog.Shared_Dialog_Activity;
import stm.airble._5_add_airble.viewpager2.recyclerview.Main_AddAirble_RecyclerViewAdapter;
import stm.airble._4_main.MainActivity;
import stm.airble._0_public.Airble_Model;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;
import stm.airble._0_public.Permission_Class;
import stm.airble._0_public.RequestHttpURLConnection;

public class AddAirbleActivity extends AppCompatActivity {


    // public
    public static Context add_airble_context;
    public ViewPager2 viewPager2;
    public LinearLayout next_btn, before_btn;
    public boolean connected_wifi = false;
    public String connected_device_mac = "";
    public String connected_airble_ssid = "";
    Loading_ProgressDialog AddAirble_Loading;


    // Page 1
    public TextView Page1_connect_TextView;
    public TextView Page1_find_wifi_TextView;
    public TextView Page1_shared_TextView;
    ActivityResultLauncher<Intent> add_cancel_launcher;
    public boolean is_Add_Cancel;

    // Page 2
    public ArrayList<String> Page2_router_ArrayList;
    public RecyclerView Page2_router_RecyclerView;
    public Main_AddAirble_RecyclerViewAdapter Page2_router_RecyclerViewAdapter;

    // Page 3
    public String Page3_Select_Router_SSID;
    public TextView Page3_SSID_TextView;
    public EditText Page3_PW_EditText;
    public TextView Page3_connect_TextView;
    public boolean Setting_NickName_bool;

    // Page 4
    public EditText Page4_NickName_EditText;
    public TextView Page4_Add_Device_TextView;
    ActivityResultLauncher<Intent> add_end_launcher;
    public boolean is_Add_End;

    public String add_airble_Mac, add_airble_NickName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airble);

        add_airble_context = this;
        AddAirble_Loading = new Loading_ProgressDialog(add_airble_context);

        // 아이디 맞춰주기
        viewPager2 = findViewById(R.id.add_airble_ViewPager2);
        next_btn = findViewById(R.id.add_airble_next_LinearLayout);
        before_btn = findViewById(R.id.add_airble_before_LinearLayout);

        Page_ActivityResultLauncher_Setting();
        Page_ViewPager_Setting();
        Page_Move_Setting();
    }

    // Before, Next Button
    public void Page_Move_Setting(){
        before_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager2.getCurrentItem() == 0) {
                    is_Add_Cancel = false;
                    add_cancel_launcher.launch(new Intent(add_airble_context, Add_Cancel_Dialog_Activity.class));
                } else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                }
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem( viewPager2.getCurrentItem() + 1 );
            }
        });
    }

    // ViewPager2
    public void Page_ViewPager_Setting(){
        viewPager2.setAdapter(new AddAirble_ViewPagerAdapter(this));
        viewPager2.setUserInputEnabled(false);
        viewPager2.setOffscreenPageLimit(4);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        before_btn.setVisibility(View.VISIBLE);
                        next_btn.setVisibility(View.GONE);
                        Page1_Check_Connect_WIFI();
                        break;

                    case 1:
                        before_btn.setVisibility(View.VISIBLE);
                        next_btn.setVisibility(View.GONE);
                        WIFI_Scan_Handler handler = new WIFI_Scan_Handler();
                        Page2_router_ArrayList.clear();
                        Page2_router_RecyclerViewAdapter.notifyDataSetChanged();
                        AddAirble_Loading.Start_Loading();
                        handler.sendEmptyMessage(1);
                        break;

                    case 2:
                        before_btn.setVisibility(View.VISIBLE);
                        next_btn.setVisibility(View.GONE);
                        break;

                    case 3:
                        Page4_NickName_EditText.setHint(connected_airble_ssid);
                        before_btn.setVisibility(View.GONE);
                        next_btn.setVisibility(View.GONE);
                        break;

                }
            }
        });

    }

    /**
     * Page1
     */
    public void Page1_Setting(){

        Page1_find_wifi_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });

        Page1_shared_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_airble_context, Shared_Dialog_Activity.class);
                shared_launcher.launch(intent);
            }
        });
    }

    void Page1_Check_Connect_WIFI(){
        AddAirble_Loading.Start_Loading();
        APP_Network_Info = APP_Connectivity_Manager.getActiveNetworkInfo();

        Page1_connect_TextView.setText("기기와 Wi-Fi로 연결해주세요");

        try{
            if(APP_Network_Info.getTypeName().equals("WIFI") && APP_Wifi_Manager.isWifiEnabled()){
                String ssid = APP_Wifi_Manager.getConnectionInfo().getSSID().trim();

                if (ssid.substring(1, 7).equals("airble") && ssid.length() == 21){

                    String mac = ssid.substring(8,10) + ":" + ssid.substring(10,12) + ":" + ssid.substring(12,14) + ":" + ssid.substring(14,16) + ":" + ssid.substring(16,18) + ":" + ssid.substring(18,20);
                    connected_airble_ssid = APP_Wifi_Manager.getConnectionInfo().getSSID().substring(1, APP_Wifi_Manager.getConnectionInfo().getSSID().length()-1);
                    connected_device_mac = mac;
                    connected_wifi = true;
                    next_btn.setVisibility(View.VISIBLE);
                    Page1_connect_TextView.setText("연결된 기기 : airble");

                }else{
                    connected_wifi = false;
                    Page1_connect_TextView.setText("에어블 기기가 아닙니다.");
                    next_btn.setVisibility(View.GONE);

                }

            }else{
                connected_wifi = false;
                next_btn.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }
        AddAirble_Loading.Stop_Loading();
    }

    /**
     * Page2
     */
    public void Page2_Setting(){
        Page2_router_ArrayList = new ArrayList<>();
        Page2_router_RecyclerViewAdapter = new Main_AddAirble_RecyclerViewAdapter(Page2_router_ArrayList);
        Page2_router_RecyclerView.setAdapter(Page2_router_RecyclerViewAdapter);
        Page2_router_RecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    // WIFI
    @SuppressLint("NotifyDataSetChanged")
    public void WIFI_Scan(){
        @SuppressLint("MissingPermission") List<ScanResult> results = APP_Wifi_Manager.getScanResults();

        // AP정보 수집
        if(results.size() > 0){
            boolean ssid_equals_bool;
            for (final ScanResult result : results) {
                String SSID = result.SSID;
                int RSSI = result.level;
                int Frequency = result.frequency;
                if(RSSI > - 80 && Frequency_2G.contains(Frequency) && !SSID.equals(connected_airble_ssid)) {
                    ssid_equals_bool = true;
                    for(String router_ssid : Page2_router_ArrayList){
                        if(router_ssid.trim().equals(SSID.trim())){
                            ssid_equals_bool = false;
                        }
                    }
                    if(ssid_equals_bool){
                        Page2_router_ArrayList.add(SSID);
                    }
                }
            }
        }
        Page2_router_RecyclerViewAdapter.notifyDataSetChanged();
    }

    class WIFI_Scan_Handler extends Handler {
        int count = 0;

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    count++;

                    if(count > 3){
                        count = 0;
                        if(Page2_router_ArrayList.size() > 0){

                        }else{
                            Toast.makeText(getApplicationContext(), "주변 공유기를 찾을 수 없습니다.\n잠시 후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        AddAirble_Loading.Stop_Loading();
                    }else{
                        WIFI_Scan();
                        this.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }
    }

    /**
     * Page3
     */
    public void Page3_Setting(){
        Setting_NickName_bool = false;
        Page3_connect_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PW = Page3_PW_EditText.getText().toString().trim();
                if(PW.equals("")){
                    Toast.makeText(add_airble_context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    Page3_Connect_Router(Page3_PW_EditText.getText().toString().trim());
                }
            }
        });

        Page3_PW_EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Page3_Connect_Router(Page3_PW_EditText.getText().toString().trim());
                }
                return false;
            }
        });
    }

    public void Page3_Connect_Router(String select_wifi_pw){
        AddAirble_Loading.Start_Loading();

        try{
//            String ssid = URLEncoder.encode(connected_airble_ssid, "UTF-8");
//            String pw = URLEncoder.encode(select_wifi_pw, "UTF-8");
            String ssid = Page3_Select_Router_SSID;
            String pw = select_wifi_pw;
            String server_url = "http://192.168.4.1/system_router?[[0314590405]]" + ssid + "[[0314590405]]" + pw;
            Log.d("Sans", "server_url = '" + server_url + "'");
            URL url = new URL(server_url);
            new Add_Airble_HttpConnection().execute(url);
        } catch (Exception e) {

        }

    }


    /**
     * Page 4
     */
    public void Page4_Setting(){
        Page4_Add_Device_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_NickName(Page4_NickName_EditText.getText().toString().trim());
            }
        });

        Page4_NickName_EditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Set_NickName(Page4_NickName_EditText.getText().toString().trim());
                }
                return false;
            }
        });
    }


    public void Set_NickName(String nickname){
        String ssid = "'" + connected_airble_ssid + "'";
        String mac = ssid.substring(8,10) + ":" + ssid.substring(10,12) + ":" + ssid.substring(12,14) + ":" + ssid.substring(14,16) + ":" + ssid.substring(16,18) + ":" + ssid.substring(18,20);

        if(nickname.length() > 10 || nickname.contains(",")){
            Start_Popup(add_airble_context, "사용할 수 없는 이름입니다.", "1~10자리/특수문자 ',' 사용 불가능");
        }else {
            if(nickname.equals("")){
                nickname = connected_airble_ssid;
            }
            add_airble_Mac = mac;
            add_airble_NickName = nickname;

            is_Add_End = false;
            Intent intent = new Intent(add_airble_context, Add_End_Dialog_Activity.class);
            add_end_launcher.launch(intent);


        }
    }

    // ActivityResult 런처 셋팅
    ActivityResultLauncher<Intent> permission_launcher;
    ActivityResultLauncher<Intent> shared_launcher;
    public void Page_ActivityResultLauncher_Setting(){
        // 퍼미션 런처 셋팅
        permission_launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Page1_Check_Connect_WIFI();
                });

        shared_launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        AddAirble_Loading.Start_Loading();
                        try{
                            String server_url = Server_Domain + "airble_test?num=42&email=" + User_Email;
                            URL url = new URL(server_url);
                            Log.d("Sans", "server_url = " + server_url);
                            new Shared_HttpConnection().execute(url);
                        } catch (Exception e) {
                            AddAirble_Loading.Stop_Loading();
                        }
                    }
                });
        add_cancel_launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(is_Add_Cancel){
                        finish();
                    }
                });

        add_end_launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(is_Add_End){
                        try {
                            AddAirble_Loading.Start_Loading();
                            String server_url = Server_Domain + "airble_test?num=470&mac=" + add_airble_Mac +
                                    "&nickname=" + add_airble_NickName;
                            URL url = new URL(server_url);
                            Log.d("Sans", "server_url = " + server_url);
                            new Set_NickName_HttpConnection().execute(url);
                        } catch (Exception e) {
                            AddAirble_Loading.Stop_Loading();
                            Toast.makeText(add_airble_context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // 퍼미션
    Permission_Class permission;
    private void permissionCheck() {

        // 클래스 객체 생성
        permission = new Permission_Class(AddAirbleActivity.this, add_airble_context);

        // 권한 체크한 후에 리턴이 false일 경우 권한 요청을 해준다.
        if (!permission.checkPermission()) {
            permission.requestPermission();
        } else {
            permission_launcher.launch(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (permission.permissionResult(requestCode, permissions, grantResults)) {
            // 성공
            permission_launcher.launch(new Intent(Settings.ACTION_WIFI_SETTINGS));
        } else {
            // 실패
            Toast.makeText(add_airble_context, "기기 추가를 위해서 권한 허용이 필요 합니다.", Toast.LENGTH_SHORT).show();
            Log.d("Sans", "퍼미션 실패");

        }
    }

    private class Shared_HttpConnection extends AsyncTask<URL, Integer, String> {

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

                    // Airble 현재 데이터 가져오기
                    case "S42":
                    {
                        try{
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
                                airble.setUpdate_date(LocalDateTime.parse(value[5].split("\\.")[0], dateTimeFormatter));

                                APP_Airble_Model_Array.add(airble);
                            }

                        } catch (Exception e) {
                            AddAirble_Loading.Stop_Loading();
                        }
                        AddAirble_Loading.Stop_Loading();

                        Intent intent = new Intent(add_airble_context, MainActivity.class);
                        add_airble_context.startActivity(intent);
                        Toast.makeText(add_airble_context, "기기가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    case "E42":{
                        // 서버 오류
                        Toast.makeText(add_airble_context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                    }
                    break;

                    default:{
                        Toast.makeText(add_airble_context, "에러코드 : " + code, Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                    }

                }

            } else {  //연결실패
                Toast.makeText(add_airble_context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                AddAirble_Loading.Stop_Loading();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            is_Add_Cancel = false;
            add_cancel_launcher.launch(new Intent(add_airble_context, Add_Cancel_Dialog_Activity.class));
        }else if(viewPager2.getCurrentItem() == 3){
            is_Add_End = false;
            add_end_launcher.launch(new Intent(add_airble_context, Add_Cancel_Dialog_Activity.class));
        }else{
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    //서버에 연결하는 코딩
    private class Add_Airble_HttpConnection extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String data = "";
            if (urls.length == 0) {
                return " URL is empty";
            }
            try {
                RequestHttpURLConnection connection = new RequestHttpURLConnection();
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
                    // 라우터 테스트 요청 시작
                    case "RT0":
                    case "RT1":
                    {
                        try{
                            String server_url = "http://192.168.4.1/system_router";
                            URL url = new URL(server_url);
                            new Add_Airble_HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;

                    // 라우터 테스트 실패
                    case "RT2":
                    {
                        Toast.makeText(add_airble_context, "라우터 연결에 실패하였습니다.\n비밀번호를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                    }
                    break;

                    // 라우터 테스트 성공
                    case "RT3":
                    {
                        Toast.makeText(add_airble_context, "라우터 연결 성공", Toast.LENGTH_SHORT).show();
                        try{
                            String server_url = "http://192.168.4.1/add_airble?" + User_Email;
                            URL url = new URL(server_url);
                            new Add_Airble_HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;

                    case "AA0":
                    case "AA1":
                    {
                        try{
                            String server_url = "http://192.168.4.1/add_airble";
                            URL url = new URL(server_url);
                            new Add_Airble_HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;
                    case "AA2":
                    {
                        Toast.makeText(add_airble_context, "이미 사용자가 존재하는 기기 입니다.\n다른 기기를 추가해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                    }
                    break;
                    case "AA3":
                    {
                        //String ssid = APP_Wifi_Manager.getConnectionInfo().getSSID().trim();
                        //String mac = ssid.substring(8,10) + ":" + ssid.substring(10,12) + ":" + ssid.substring(12,14) + ":" + ssid.substring(14,16) + ":" + ssid.substring(16,18) + ":" + ssid.substring(18,20);
                        String server_url = "http://192.168.4.1/setting_nickname?" + connected_airble_ssid;
                        try{
                            URL url = new URL(server_url);
                            new Add_Airble_HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;

                    case "SN0":
                    case "SN1":
                    {
                        try{
                            Setting_NickName_bool = true;
                            String server_url = "http://192.168.4.1/setting_nickname";
                            URL url = new URL(server_url);
                            new Add_Airble_HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;

                    case "SN2":
                    {
                        Toast.makeText(add_airble_context, "기기와의 연결이 원활하지 않습니다. 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                    }
                    break;

                    case "SN3":
                    {
                        Setting_NickName_bool = false;
                        Toast.makeText(add_airble_context, "기기가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                        viewPager2.setCurrentItem(3);
                    }
                    break;

                }
            } else {  //연결실패
                if(Setting_NickName_bool){
                    Setting_NickName_bool = false;
                    Toast.makeText(add_airble_context, "기기가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    AddAirble_Loading.Stop_Loading();
                    viewPager2.setCurrentItem(3);
                }else {
                    Toast.makeText(add_airble_context, "기기와의 연결상태를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                    AddAirble_Loading.Stop_Loading();
                }
            }
        }

    }

    private class Set_NickName_HttpConnection extends AsyncTask<URL, Integer, String> {

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

                    case "S470":
                    {
                        AddAirble_Loading.Stop_Loading();
                        Airble_Model airble = new Airble_Model();
                        airble.setMAC_Address(connected_device_mac);
                        airble.setNick_Name(add_airble_NickName);
                        APP_Airble_Model_Array.add(airble);
                        finish();
                    }
                    break;

                    case "F470":
                    {
                        AddAirble_Loading.Stop_Loading();
                        Toast.makeText(add_airble_context, "설정에 실패하였습니다. 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                AddAirble_Loading.Stop_Loading();
                Toast.makeText(add_airble_context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
/*
public class AddAirbleActivity extends AppCompatActivity {

    public static Context add_airble_context;
    Loading_ProgressDialog Add_Airble_Loading;

    public String select_wifi_ssid = "", select_wifi_nickname = "";
    public ViewPager2 inner_ViewPager2;
    public TextView inner_ViewPager2_WIFI_SSID_TextView;
    public ArrayList<String> inner_Router_ArrayList;
    public Main_AddAirble_RecyclerViewAdapter inner_Router_recyclerViewAdapter;

    public ViewPager2 viewPager2;
    public LinearLayout next_btn, before_btn;
    public boolean connected_wifi = false;

    public TextView connect_TextView;

    String add_Mac, add_NickName;
    public static String add_SSID;

    ActivityResultLauncher<Intent> add_cancel_ActivityResultLauncher;
    public boolean is_Add_Cancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_airble);

        add_airble_context = this;
        Add_Airble_Loading = new Loading_ProgressDialog(add_airble_context);

        // 아이디 맞춰주기
        viewPager2 = findViewById(R.id.add_airble_ViewPager2);
        next_btn = findViewById(R.id.add_airble_next_LinearLayout);
        before_btn = findViewById(R.id.add_airble_before_LinearLayout);

        viewPager2.setAdapter(new AddAirble_ViewPagerAdapter(this));
        viewPager2.setUserInputEnabled(false);

        // wifi 관련
        {
            connected_wifi = false;

            BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onReceive(Context context, Intent intent) {
                    try{
                        if(viewPager2.getCurrentItem() == 1){
                            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);

                            if (success) {
                                @SuppressLint("MissingPermission") List<ScanResult> results = APP_Wifi_Manager.getScanResults();

                                // AP정보 수집
                                for (final ScanResult result : results) {
                                    String SSID = result.SSID;
                                    int RSSI = result.level;
                                    int Frequency = result.frequency;

                                    if(RSSI > - 80 && Frequency_2G.contains(Frequency) && !SSID.equals(APP_Wifi_Manager.getConnectionInfo().getSSID().substring(1, APP_Wifi_Manager.getConnectionInfo().getSSID().length()-1))) {
                                        inner_Router_ArrayList.add(SSID);
                                    }

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "주변 공유기를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                            inner_Router_recyclerViewAdapter.notifyDataSetChanged();
                            Add_Airble_Loading.Stop_Loading();
                        }
                    }catch (Exception e){

                    }
                }

            };

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            getApplicationContext().registerReceiver(wifiScanReceiver, intentFilter);
        }

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        before_btn.setVisibility(View.VISIBLE);
                        if(connected_wifi) {
                            next_btn.setVisibility(View.VISIBLE);
                        }else{
                            next_btn.setVisibility(View.GONE);
                        }
                        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Check_Connected_Airble();
                        break;

                    case 1:
                        before_btn.setVisibility(View.VISIBLE);
                        next_btn.setVisibility(View.GONE);
                        try{
                            inner_Router_ArrayList.clear();
                            inner_Router_recyclerViewAdapter.notifyDataSetChanged();
                            inner_ViewPager2.setCurrentItem(0, false);
                        }catch (Exception e){

                        }
                        Add_Airble_Loading.Start_Loading();
                        APP_Wifi_Manager.startScan();
                        break;

                    case 2:
                        before_btn.setVisibility(View.GONE);
                        next_btn.setVisibility(View.GONE);
                        break;

                }
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem( viewPager2.getCurrentItem() + 1 );
            }
        });

        before_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewPager2.getCurrentItem()){
                    case 0:
                        onBackPressed();
                        break;
                    case 2:
                        viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                        break;

                    case 1:
                        if(inner_ViewPager2.getCurrentItem() == 0) {
                            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
                        }else {
                            inner_ViewPager2.setCurrentItem(inner_ViewPager2.getCurrentItem() - 1);
                        }
                        break;
                }
            }
        });

        // 취소하기 관련
        add_cancel_ActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(is_Add_Cancel){
                        finish();
                    }
                });
    }

    public void Start_Main(String nickname){
        String ssid = APP_Wifi_Manager.getConnectionInfo().getSSID().trim();
        add_SSID = ssid;
        String mac = ssid.substring(8,10) + ":" + ssid.substring(10,12) + ":" + ssid.substring(12,14) + ":" + ssid.substring(14,16) + ":" + ssid.substring(16,18) + ":" + ssid.substring(18,20);
        if(nickname.equals("")){
            nickname = ssid.substring(1, ssid.length() - 1);
        }
        add_Mac = mac;
        add_NickName = nickname;
        Set_Airble_NickName(nickname);
    }

    public ArrayList<String> Check_Connected_Airble(){

        Add_Airble_Loading.Start_Loading();
        ArrayList<String> result = new ArrayList<>();
        APP_Network_Info = APP_Connectivity_Manager.getActiveNetworkInfo();

        connect_TextView.setText("기기와 Wi-Fi로 연결해주세요");
        if(APP_Network_Info.getTypeName().equals("WIFI") && APP_Wifi_Manager.isWifiEnabled()){
            String ssid = APP_Wifi_Manager.getConnectionInfo().getSSID().trim();
            add_SSID = ssid;

            if (ssid.substring(1, 7).equals("airble") && ssid.length() == 21){
                result.add("ok");
                result.add(ssid);

                String mac = ssid.substring(7, 9) + ":" + ssid.substring(9, 11) + ":" + ssid.substring(11, 13) + ":" + ssid.substring(13, 15) + ":" + ssid.substring(15, 17) + ":" + ssid.substring(17, 19);
                result.add(mac);

                connected_wifi = true;
                next_btn.setVisibility(View.VISIBLE);
                //connect_TextView.setText("연결된 기기 : " + ssid.substring(1, 20));
                connect_TextView.setText("연결된 기기 : airble");


            }else{
                result.add("ssid_fail");
                connected_wifi = false;
                connect_TextView.setText("에어블 기기가 아닙니다.");
                next_btn.setVisibility(View.GONE);

            }

        }else{
            result.add("wifi_fail");
            connected_wifi = false;
            next_btn.setVisibility(View.GONE);
        }

        Add_Airble_Loading.Stop_Loading();
        return result;
    }


    @Override
    public void onBackPressed() {
        if(viewPager2.getCurrentItem() != 0){
            if(inner_ViewPager2.getCurrentItem() != 0){
                inner_ViewPager2.setCurrentItem(inner_ViewPager2.getCurrentItem()-1);
            }else{
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()-1);
            }
        }else{
            is_Add_Cancel = false;
            Intent intent = new Intent(AddAirbleActivity.this, Add_Cancel_Dialog_Activity.class);
            add_cancel_ActivityResultLauncher.launch(intent);
        }
    }

    public void Check_Airble_Connected_to_Airble(String select_wifi_pw){
        Add_Airble_Loading.Start_Loading();

        try{
            String server_url = "http://192.168.4.1/system_router?[[0314590405]]" + select_wifi_ssid + "[[0314590405]]" + select_wifi_pw;
            URL url = new URL(server_url);
            new HttpConnection().execute(url);
        } catch (Exception e) {

        }

    }

    public void Set_Airble_NickName(String nickname) {
        Add_Airble_Loading.Start_Loading();

        try {
            String server_url = "http://192.168.4.1/setting_nickname?" + nickname;
            URL url = new URL(server_url);
            new HttpConnection().execute(url);
        } catch (Exception e) {

        }
    }


        //서버에 연결하는 코딩
    private class HttpConnection extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String data = "";
            if (urls.length == 0) {
                return " URL is empty";
            }
            try {
                RequestHttpURLConnection connection = new RequestHttpURLConnection();
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
                Log.d("Sans", "Data = '" + data + "'");
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                //String code = data;
                switch (code){
                    // 라우터 테스트 요청 시작
                    case "RT0":
                    case "RT1":
                    {
                        try{
                            String server_url = "http://192.168.4.1/system_router";
                            URL url = new URL(server_url);
                            new HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;

                    // 라우터 테스트 실패
                    case "RT2":
                    {
                        Toast.makeText(AddAirbleActivity.this, "라우터 연결에 실패하였습니다.\n비밀번호를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                        Add_Airble_Loading.Stop_Loading();
                    }
                    break;

                    // 라우터 테스트 성공
                    case "RT3":
                    {
                        Toast.makeText(AddAirbleActivity.this, "라우터 연결 성공", Toast.LENGTH_SHORT).show();
                        try{
                            String server_url = "http://192.168.4.1/add_airble?" + User_Email;
                            URL url = new URL(server_url);
                            new HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;

                    case "AA0":
                    case "AA1":
                    {
                        try{
                            String server_url = "http://192.168.4.1/add_airble";
                            URL url = new URL(server_url);
                            new HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;
                    case "AA2":
                    {
                        Toast.makeText(AddAirbleActivity.this, "이미 사용자가 존재하는 기기 입니다.\n다른 기기를 추가해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        Add_Airble_Loading.Stop_Loading();
                    }
                    break;

                    case "AA3":
                    {
                        Add_Airble_Loading.Stop_Loading();
                        viewPager2.setCurrentItem(2);
                    }
                    break;

                    case "SN0":
                    case "SN1":
                    {
                        try{
                            String server_url = "http://192.168.4.1/setting_nickname";
                            URL url = new URL(server_url);
                            new HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                    break;
                    case "SN2":
                    {
                        Toast.makeText(AddAirbleActivity.this, "기기와의 연결이 원활하지 않습니다. 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        Add_Airble_Loading.Stop_Loading();
                    }
                    break;
                    case "SN3":
                    {
                        Toast.makeText(AddAirbleActivity.this, "기기가 추가되었습니다.", Toast.LENGTH_SHORT).show();

                        String ssid = APP_Wifi_Manager.getConnectionInfo().getSSID().trim();
                        String mac = ssid.substring(8,10) + ":" + ssid.substring(10,12) + ":" + ssid.substring(12,14) + ":" + ssid.substring(14,16) + ":" + ssid.substring(16,18) + ":" + ssid.substring(18,20);
                        Add_Airble(mac, add_NickName);
                        Add_Airble_Loading.Stop_Loading();
                        finish();
                    }
                    break;
                }
            } else {  //연결실패
                Toast.makeText(AddAirbleActivity.this, "기기와의 연결상태를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                Add_Airble_Loading.Stop_Loading();
            }
        }
    }

}
*/