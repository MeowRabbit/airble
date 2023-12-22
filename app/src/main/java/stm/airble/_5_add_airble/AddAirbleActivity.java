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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import stm.airble.R;
import stm.airble._3_registration.RegistrationActivity;
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
    public LinearLayout before_btn;
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
    public LinearLayout Page1_next_btn;
    public TextView Page1_next_TextView;

    // Page 2
    public ArrayList<String> Page2_router_ArrayList;
    public SwipeRefreshLayout Page2_router_SwipeRefreshLayout;
    public RecyclerView Page2_router_RecyclerView;
    public Main_AddAirble_RecyclerViewAdapter Page2_router_RecyclerViewAdapter;
    public View Page2_router_RecyclerView_Cover;

    // Page 3
    public String Page3_Select_Router_SSID;
    public TextView Page3_SSID_TextView;
    public EditText Page3_PW_EditText;
    public TextView Page3_connect_TextView;
    int router_connected_count;

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
        before_btn = findViewById(R.id.add_airble_before_LinearLayout);

        Page_ActivityResultLauncher_Setting();
        Page_ViewPager_Setting();
        Page_Move_Setting();
    }

    // Before Button
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
                        Page1_Check_Connect_WIFI();
                        break;

                    case 1:
                        before_btn.setVisibility(View.VISIBLE);
                        WIFI_Scan_Refresh();
                        break;

                    case 2:
                        before_btn.setVisibility(View.VISIBLE);
                        break;

                    case 3:
                        Page4_NickName_EditText.setHint(connected_airble_ssid);
                        before_btn.setVisibility(View.GONE);
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

        Page1_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager2.setCurrentItem( viewPager2.getCurrentItem() + 1 );
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
                    Page1_next_btn.setVisibility(View.VISIBLE);
                    Page1_connect_TextView.setText("연결된 기기 : airble");

                }else{
                    connected_wifi = false;
                    Page1_connect_TextView.setText("에어블 기기가 아닙니다.");
                    Page1_next_btn.setVisibility(View.GONE);

                }

            }else{
                connected_wifi = false;
                Page1_next_btn.setVisibility(View.GONE);
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

        Page2_router_SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WIFI_Scan_Refresh();
            }
        });

        Page2_router_RecyclerView_Cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
        Page2_router_RecyclerView.invalidate();
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
                        Page2_router_SwipeRefreshLayout.setRefreshing(false);
                        Page2_router_RecyclerView_Cover.setVisibility(View.GONE);
                        Page2_router_RecyclerViewAdapter.notifyDataSetChanged();
                        Page2_router_RecyclerView.invalidate();
                    }else{
                        WIFI_Scan();
                        this.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    void WIFI_Scan_Refresh(){
        if(!Page2_router_SwipeRefreshLayout.isRefreshing()){
            Page2_router_SwipeRefreshLayout.setRefreshing(true);
        }
        Page2_router_RecyclerView_Cover.setVisibility(View.VISIBLE);
        WIFI_Scan_Handler handler = new WIFI_Scan_Handler();
        Page2_router_ArrayList.clear();
        Page2_router_RecyclerViewAdapter.notifyDataSetChanged();
        Page2_router_RecyclerView.invalidate();
        handler.sendEmptyMessage(1);
    }

    /**
     * Page3
     */
    public void Page3_Setting(){
        Page3_connect_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                router_connected_count = 0;
                String PW = Page3_PW_EditText.getText().toString().trim();
                Page3_Connect_Router(PW);

                //if(PW.equals("")){
                //    Toast.makeText(add_airble_context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                //}else{
                //    Page3_Connect_Router(Page3_PW_EditText.getText().toString().trim());
                //}
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
            String ssid = String_To_Int(Page3_Select_Router_SSID, "%");
            String pw = String_To_Int(select_wifi_pw, "%");
            String email = String_To_Int(User_Email, "%");

            String server_url = "http://192.168.4.1/add_airble?" + ssid + "&" + pw + "&" + email;
            Log.d("Sans", "server_url = '" + server_url + "'");
            URL url = new URL(server_url);
            new Add_Airble_HttpConnection().execute(url);
        } catch (Exception e) {

        }

    }

    String String_To_Int(String str, String split_str){
        String result_str = "";
        char ch[]= str.toCharArray();
        for(int i = 0; i < ch.length; i++){
            result_str += split_str + ((int)ch[i]);
        }
        return result_str;
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

                    // 라우터 테스트 실패
                    case "RT2":
                    {
                        router_connected_count++;
                        if(router_connected_count > 1){
                            Toast.makeText(AddAirbleActivity.this, "공유기 연결에 실패하였습니다.\n비밀번호를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
                            AddAirble_Loading.Stop_Loading();
                            is_Add_End = false;
                        }else{
                            String PW = Page3_PW_EditText.getText().toString().trim();
                            Page3_Connect_Router(PW);
                        }
                    }
                    break;

                    // 라우터 테스트 성공
                    case "RT3":
                    {
                        Toast.makeText(AddAirbleActivity.this, "공유기 연결 성공", Toast.LENGTH_SHORT).show();
                        try{
                            router_connected_count = 0;
                            String server_url = "http://192.168.4.1/add_airble";
                            URL url = new URL(server_url);
                            new Add_Airble_HttpConnection().execute(url);
                            is_Add_End = true;
                        } catch (Exception e) {

                        }
                    }
                    break;

                    case "AA2":
                    {
                        is_Add_End = false;
                        Toast.makeText(AddAirbleActivity.this, "이미 사용자가 존재하는 기기 입니다.\n다른 기기를 추가해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        AddAirble_Loading.Stop_Loading();
                    }
                    break;

                    case "AA3":
                    {
                        Toast.makeText(AddAirbleActivity.this, "기기가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        Fragment_Page = 0;
                        AddAirble_Loading.Stop_Loading();
                        viewPager2.setCurrentItem(3);
                    }
                    break;
                }
            } else {  //연결실패
                if(is_Add_End) {
                    is_Add_End = false;
                    Toast.makeText(AddAirbleActivity.this, "기기가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    Fragment_Page = 0;
                    AddAirble_Loading.Stop_Loading();
                    viewPager2.setCurrentItem(3);
                }else{
                    Toast.makeText(AddAirbleActivity.this, "기기와의 연결상태를 확인 해 주세요.", Toast.LENGTH_SHORT).show();
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