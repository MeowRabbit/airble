package stm.airble._4_main._1_home;

import static stm.airble._4_main.MainActivity.Main_Context;
import static stm.airble._4_main.MainActivity.Main_Fragment_ID;
import static stm.airble._4_main.MainActivity.Main_Get_Airble_Models_Status_Bool;
import static stm.airble._4_main.MainActivity.Main_Loading;
import static stm.airble._4_main.MainActivity.Main_Refresh_Airble_Model_Array_Bool;
import static stm.airble._4_main.MainActivity.select_airble_num;
import static stm.airble._0_public.Public_Values.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import stm.airble.R;
import stm.airble._4_main._5_setting._5_SettingsFragment;
import stm.airble._5_add_airble.AddAirbleActivity;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._1_home.viewpager2.Main_Home_ViewPagerAdapter;
import stm.airble._4_main._1_home.viewpager2._1_Home_ViewHolderPage;
import stm.airble._4_main._1_home.viewpager2.viewpager2._1_Home_Gauge_ViewHolderPage;
import stm.airble._0_public.GET_RequestHttpURLConnection;

public class _1_HomeFragment extends Fragment {
    public static Fragment home_context;

    // 변환 스위치
    public LinearLayout device_auto_LinearLayout;
    public TextView device_auto_TextView;
    public Switch device_auto_Switch;


    // 기기 추가
    public Button device_add_Button;
    public ActivityResultLauncher<Intent> device_add_launcher;

    // 기기 정보
    public TextView device_name_TextView;
    public TextView device_connected_TextView;


    // 기기 선택
    public Button device_select_Button;
    public RelativeLayout device_outside_list_LinearLayout;
    public HorizontalScrollView device_list_HorizontalScrollView;
    public LinearLayout device_inside_list_LinearLayout;

    public boolean device_list_open;
    public boolean device_list_running;
    public MutableLiveData<Long> device_select_Mutable;
    public Observer<Long> device_select_Observer;
    public Observable<Long> device_select_Observable;

    // 하부 기기 상세 데이터
    public ViewPager2 device_ViewPager2;
    public ArrayList<_1_Home_ViewHolderPage> device_ViewPager2_ViewHolder;


    // 기기 화면 데이터 갱신용
    public Disposable device_Data_Disposable;
    public MutableLiveData<Long> device_Data_Mutable;
    public Observable<Long> device_Data_Observable;

    // 리모콘
    public Disposable remocon_Disposable;
    public MutableLiveData<Long> remocon_Mutable;
    public Observable<Long> remocon_Observable;


    public ActivityResultLauncher<Intent> device_place_ActivityResultLauncher;

    public boolean home_start = true;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        home_context = this;
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        _1_Home_Setting(view);
        Setting_Touch_Window(view);
        Setting_ViewPager();
        Setting_Select_Device();

        return view;
    }

    public void _1_Home_Setting(View view){
        // 아이디 맞춰주기
        {
            // 메인페이지
            device_select_Button = view.findViewById(R.id.main_home_device_select_Button);
            device_outside_list_LinearLayout = view.findViewById(R.id.main_home_device_outside_list_LinearLayout);
            device_list_HorizontalScrollView = view.findViewById(R.id.main_home_device_list_HorizontalScrollView);
            device_inside_list_LinearLayout = view.findViewById(R.id.main_home_device_inside_list_LinearLayout);
            device_name_TextView = view.findViewById(R.id.main_home_device_name_TextView);
            device_connected_TextView = view.findViewById(R.id.main_home_device_connected_TextView);
            device_add_Button = view.findViewById(R.id.main_home_device_add_Button);

            device_ViewPager2 = view.findViewById(R.id.main_home_device_ViewPager2);

            device_ViewPager2_ViewHolder = new ArrayList<>();

            device_auto_LinearLayout = view.findViewById(R.id.main_home_device_auto_LinearLayout);
            device_auto_TextView = view.findViewById(R.id.main_home_device_auto_TextView);
            device_auto_Switch = view.findViewById(R.id.main_home_device_auto_Switch);

        }

        // 초기 페이지 비우기
        {
            device_name_TextView.setText("");
            device_connected_TextView.setText("");

        }
        // launcher
        {
            device_add_launcher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Main_Loading.Start_Loading();
                        try{
                            String server_url = Server_Domain + "airble_test?num=42&email=" + User_Email;
                            URL url = new URL(server_url);
                            new Get_Airble_Models_Status_HttpConnection().execute(url);
                        }catch (Exception e){
                            e.printStackTrace();
                            Main_Get_Airble_Models_Status_Bool = false;
                        }
                    });

            device_place_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Refresh_Home_Fragment();
                    });
        }


        // 기기 추가
        {
            device_add_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.Main_Context, AddAirbleActivity.class);
                    //Fragment_Page = -1;
                    device_add_launcher.launch(intent);
                }
            });
        }

        // auto 버튼
        {
            device_auto_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(loop_page_boolean){
                        loop_page_boolean = false;
                        device_auto_Switch.setChecked(false);
                        device_auto_TextView.setTextColor(Color.rgb(0xAA, 0xAA, 0xAA));
                        APP_Airble_Model_Array.get(select_airble_num).setPage_num(1);
                        //test_btn.setText("테스트 = OFF");
                        Log.d("Sans", "버튼 : 자동 종료");
                    }else{
                        loop_page_boolean = true;
                        device_auto_Switch.setChecked(true);
                        device_auto_TextView.setTextColor(Color.rgb(0xF9, 0x50, 0x50));
                        APP_Airble_Model_Array.get(select_airble_num).setPage_num(6);
                        //test_btn.setText("테스트 = ON");
                        Log.d("Sans", "버튼 : 자동 온");
                    }
                    Save_Device_Page();
                    for(int i = 0; i < APP_Airble_Model_Array.size(); i++){
                        Log.d("Sans", "APP_Airble_Model_Array.get(" + i + ").getPage_num() = " + APP_Airble_Model_Array.get(i).getPage_num());
                    }
                }
            });
        }

    }

    public void Setting_Touch_Window(View view){
        View touch_window_View = view.findViewById(R.id.main_home_touch_window_View);

        touch_window_View.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    if(device_list_open){
                        if (!device_list_running) {
                            device_list_running = true;
                            device_list_HorizontalScrollView.setScrollX(0);
                            device_select_Observable.subscribeOn(Schedulers.io()).subscribe(device_select_Observer);
                        }
                    }
                }
                return false;
            }
        });
    }

    public void Start_Remocon_LiveData(){
        remocon_Mutable = new MutableLiveData<>();
        remocon_Mutable.observe(this, new androidx.lifecycle.Observer<Long>() {
            int page_remocon_send_time = 2000;
            int page_remocon_loop_time = 0;
            int viewing_airble_num = -1;
            int viewing_airble_page_num = -1;

            int new_viewing_airble_page_num = 0;

            @Override
            public void onChanged(Long aLong) {
                if(!home_start){
                    page_remocon_loop_time++;
                    new_viewing_airble_page_num = device_ViewPager2_ViewHolder.get(select_airble_num).device_sensor_ViewPager2.getCurrentItem();

                    if(viewing_airble_num != select_airble_num){
                        Log.d("Sans", "보는 기기 변경");
                        page_remocon_loop_time = 0;
                        viewing_airble_num = select_airble_num;
                        viewing_airble_page_num = new_viewing_airble_page_num;
                    }else if(viewing_airble_page_num != new_viewing_airble_page_num){
                        if(!loop_page_boolean) {
                            Log.d("Sans", "보는 페이지 변경");
                            page_remocon_loop_time = 0;
                            viewing_airble_page_num = new_viewing_airble_page_num;
                        }
                    }

                    if(page_remocon_loop_time >= page_remocon_send_time){
                        page_remocon_loop_time = 0;
                        try{
                            String server_url;
                            if(loop_page_boolean){
                                server_url = Server_Domain + "airble_test?num=46&mac=" + APP_Airble_Model_Array.get(viewing_airble_num).getMAC_Address() + "&page=5";
                            }else{
                                server_url = Server_Domain + "airble_test?num=46&mac=" + APP_Airble_Model_Array.get(viewing_airble_num).getMAC_Address() + "&page=" + ( viewing_airble_page_num - 1 );
                            }
                            Log.d("Sans", server_url);
                            URL url = new URL(server_url);
                            new Remocon_HttpConnection().execute(url);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            class Remocon_HttpConnection extends AsyncTask<URL, Integer, String> {

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
                            case "S46":
                            {
                                Log.d("Sans", "리모콘 작동");
                            }
                            break;
                        }

                    } else {  //연결실패

                    }
                }
            }
        });

        remocon_Observable = Observable.interval(1, TimeUnit.MILLISECONDS);

        remocon_Disposable = remocon_Observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                remocon_Mutable.postValue(aLong);
            }
        });
    }

    public void Stop_Remocon_LiveData(){
        if(remocon_Disposable != null){
            if(!remocon_Disposable.isDisposed()){
                remocon_Disposable.dispose();
            }
        }
    }

    public void Set_Device_Connected_ON(){
        device_connected_TextView.setTextColor(Color.rgb(0x50, 0x8e, 0xf9));
        device_connected_TextView.setText("연결 ON");
    }

    public void Set_Device_Connected_OFF(){
        device_connected_TextView.setTextColor(Color.rgb(0xF9, 0x50, 0x50));
        device_connected_TextView.setText("연결 OFF");
    }

    public void Set_Device_Auto_Check_ON(){
        loop_page_boolean = true;
        device_auto_Switch.setChecked(true);
        device_auto_TextView.setTextColor(Color.rgb(0xF9, 0x50, 0x50));
    }

    public void Set_Device_Auto_Check_OFF(){
        loop_page_boolean = false;
        device_auto_Switch.setChecked(false);
        device_auto_TextView.setTextColor(Color.rgb(0xAA, 0xAA, 0xAA));
    }

    public void Set_Device_Title(){
        String name = APP_Airble_Model_Array.get(select_airble_num).getNick_Name();
        int page_num =  APP_Airble_Model_Array.get(select_airble_num).getPage_num();
        device_name_TextView.setText(name);
        if( page_num!= 6 ){
            Set_Device_Auto_Check_OFF();
        }else{
            Set_Device_Auto_Check_ON();
        }

        if(device_ViewPager2_ViewHolder.get(select_airble_num).connect_on){
            Set_Device_Connected_ON();
        }else{
            Set_Device_Connected_OFF();
        }

    }

    public void Setting_ViewPager(){
        device_ViewPager2.setAdapter(new Main_Home_ViewPagerAdapter());
        device_ViewPager2.setOffscreenPageLimit(100);
        device_ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(home_start){
                    device_name_TextView.setText("");
                    Set_Device_Auto_Check_OFF();
                }else{
                    select_airble_num = position;
                    Set_Device_Title();
                    Device_List_Refresh();
                }

                //이제 값을 쓰레드로 받은다음 변환되는지 확인해야댐 수정할것
                /*
                device_ViewPager2.setCurrentItem(((MainActivity) MainActivity.Main_Context).select_airble_num,false);

                if(home_start){
                    home_start = false;
                    device_ViewPager2.setCurrentItem(((MainActivity) MainActivity.Main_Context).select_airble_num,false);

//Device_List_Refresh();

                    if(APP_Airble_Model_Array.get(select_airble_num).getPage_num() != 6){

                        loop_page_boolean = false;
                        device_auto_Switch.setChecked(false);
                        device_auto_TextView.setTextColor(Color.rgb(0xAA, 0xAA, 0xAA));
                        //test_btn.setText("테스트 = OFF");
                        Log.d("Sams", "초기이동 : 자동 종료");
                    }else{
                        loop_page_boolean = true;
                        device_auto_Switch.setChecked(true);
                        device_auto_TextView.setTextColor(Color.rgb(0xF9, 0x50, 0x50));
                        //test_btn.setText("테스트 = ON");
                        Log.d("Sams", "초기이동 : 자동 온");
                    }
                }else {
                    ((MainActivity) MainActivity.Main_Context).select_airble_num = position;
//Device_List_Refresh();

                    if(APP_Airble_Model_Array.get(select_airble_num).getPage_num() != 6){
                        loop_page_boolean = false;
                        device_auto_Switch.setChecked(false);
                        device_auto_TextView.setTextColor(Color.rgb(0xAA, 0xAA, 0xAA));
                        //test_btn.setText("테스트 = OFF");
                        Log.d("Sams", "이동 : 자동 종료");
                    }else{
                        loop_page_boolean = true;
                        device_auto_Switch.setChecked(true);
                        device_auto_TextView.setTextColor(Color.rgb(0xF9, 0x50, 0x50));
                        //test_btn.setText("테스트 = ON");
                        Log.d("Sams", "이동 : 자동 온");
                    }
                }
                Save_Device_Page();
                Log.d("Sams", "home_start = " + home_start);
                for(int i = 0; i < APP_Airble_Model_Array.size(); i++){
                    Log.d("Sams", "APP_Airble_Model_Array.get(" + i + ").getPage_num() = " + APP_Airble_Model_Array.get(i).getPage_num());
                }
*/

            }
        });
    }

    public void Setting_Select_Device(){
        // 기기 선택
        {
            device_list_running = false;
            device_list_open = false;
            device_select_Mutable = new MutableLiveData<>();
            device_select_Mutable.observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Long>() {
                @Override
                public void onChanged(Long aLong) {
                    ViewGroup.LayoutParams layoutParams = device_outside_list_LinearLayout.getLayoutParams();
                    layoutParams.width = Math.toIntExact(aLong);
                    device_outside_list_LinearLayout.setLayoutParams(layoutParams);
                }
            });

            device_select_Observer = new Observer<Long>() {
                Display display;
                Point size;

                double max_width;
                double min_width;

                double unit;
                double width;
                double inner_width;

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    try {
                        display = getActivity().getWindowManager().getDefaultDisplay();
                        size = new Point();
                        display.getRealSize(size);

                        max_width = (size.x - ( getResources().getDimension(R.dimen.public_10dp) * 12.9 ));
                        min_width = getResources().getDimension(R.dimen.public_10dp) * 2.0;

                        inner_width = (APP_Airble_Model_Array.size() * getResources().getDimension(R.dimen.public_10dp) * 4.7)  + (getResources().getDimension(R.dimen.public_10dp) * 3.6);
                        if(inner_width < max_width){
                            max_width = inner_width;
                        }

                        unit = (max_width - min_width) / 100.0;

                        if (device_list_open) {
                            width = max_width;
                        } else {
                            width = min_width;
                        }
                        device_outside_list_LinearLayout.setVisibility(View.VISIBLE);
                        device_select_Mutable.postValue(Math.round(width));
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onNext(@NonNull Long aLong) {
                    try {
                        if (device_list_open) {
                            //width = width - (unit / 100.0 * (100.0 - aLong));
                            device_select_Mutable.postValue(Math.round( min_width + (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                        } else {
                            //width = width + (unit / 100.0 * (100.0 - aLong));
                            device_select_Mutable.postValue(Math.round( max_width - (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                        }
                        //device_select_Mutable.postValue(Math.round(width));
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onComplete() {
                    try {
                        if (device_list_open) {
                            device_list_open = false;
                            width = min_width;
                            device_outside_list_LinearLayout.setVisibility(View.INVISIBLE);
                        } else {
                            device_list_open = true;
                            width = max_width;
                        }
                        device_select_Mutable.postValue(Math.round(width));
                        device_list_running = false;
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            };

            device_select_Observable = Observable.interval(3, TimeUnit.MILLISECONDS).take(100);

            device_select_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!device_list_running) {
                        device_list_running = true;
                        device_list_HorizontalScrollView.setScrollX(0);
                        device_select_Observable.subscribeOn(Schedulers.io()).subscribe(device_select_Observer);
                    }
                }
            });


        }
    }

    public void Start_Device_Data_LiveData(){
        device_Data_Mutable = new MutableLiveData<>();
        device_Data_Mutable.observe(this, new androidx.lifecycle.Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if(Main_Refresh_Airble_Model_Array_Bool){
                    if(!Main_Get_Airble_Models_Status_Bool) {
                        Main_Get_Airble_Models_Status_Bool = true;
                        try{
                            String server_url = Server_Domain + "airble_test?num=42&email=" + User_Email;
                            URL url = new URL(server_url);
                            new Get_Airble_Models_Status_HttpConnection().execute(url);
                        }catch (Exception e){
                            e.printStackTrace();
                            Main_Get_Airble_Models_Status_Bool = false;
                        }
                    }
                }else {
                    if (home_start) {
                        device_ViewPager2.setCurrentItem(select_airble_num, false);
                        home_start = false;
                        Device_List_Refresh();
                        for (int i = 0; i < device_ViewPager2_ViewHolder.size(); i++) {
                            if (device_ViewPager2_ViewHolder.get(i).device_sensor_ViewPager2.getCurrentItem() == 0) {
                                device_ViewPager2_ViewHolder.get(i).device_sensor_ViewPager2.setCurrentItem(1, false);
                                device_ViewPager2_ViewHolder.get(i).right_arrow_ImageButton.setVisibility(View.VISIBLE);
                            }
                        }
                        Main_Loading.Stop_Loading();
                    }
                    for (int i = 0; i < device_ViewPager2_ViewHolder.size(); i++) {
                        try {
                            Setting_Device_Data(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Set_Device_Title();
                    device_ViewPager2.postInvalidate();
                }
            }

        });
        device_Data_Observable = Observable.interval(1, TimeUnit.SECONDS);

        device_Data_Disposable = device_Data_Observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                device_Data_Mutable.postValue(aLong);
            }
        });
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
                            Refresh_Home_Fragment();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    break;

                    case "F42":{
                        if(APP_Airble_Model_Array.size() == 0){
                            Toast.makeText(Main_Context, "기기 없서요", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(Main_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    case "E42":{
                        // 서버 오류
                        Toast.makeText(Main_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                    default:{
                        Toast.makeText(Main_Context, "에러코드 : " + code, Toast.LENGTH_SHORT).show();
                    }

                }

            } else {  //연결실패
                Toast.makeText(Main_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Stop_Device_Data_LiveData(){
        if(device_Data_Disposable != null){
            if(!device_Data_Disposable.isDisposed()){
                device_Data_Disposable.dispose();
            }
        }
    }


    @Override
    public void onResume() {
        Log.d("Sans", "Home Resume");
        super.onResume();
        home_start = true;
        Start_Device_Data_LiveData();
        Start_Remocon_LiveData();
        Main_Loading.Start_Loading();

    }

    @Override
    public void onPause() {
        Log.d("Sans", "Home Pause");
        Main_Loading.Stop_Loading();
        Stop_Device_Data_LiveData();
        Stop_Remocon_LiveData();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d("Sans", "Home Destroy");
        super.onDestroy();
    }


    // 커스텀 함수
    public void Device_List_Refresh(){

        device_inside_list_LinearLayout.removeAllViews();

        if( select_airble_num >= APP_Airble_Model_Array.size()){
            select_airble_num = APP_Airble_Model_Array.size() - 1;
        }
        device_name_TextView.setText(APP_Airble_Model_Array.get(select_airble_num).getNick_Name());

        for(int i = 0; i < APP_Airble_Model_Array.size(); i++) {
            int select_device_num = i;
            Button device_Button = new Button(MainActivity.Main_Context);
            LinearLayout.LayoutParams device_Button_LayoutParms = new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.public_10dp) * 4, getResources().getDimensionPixelOffset(R.dimen.public_10dp) * 4);
            device_Button_LayoutParms.gravity = Gravity.CENTER_VERTICAL;
            device_Button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.home_device_block, null));
            if(APP_Airble_Model_Array.get(i).getNick_Name().length() > 2){
                device_Button.setText(APP_Airble_Model_Array.get(i).getNick_Name().substring(0, 2));
            }else{
                device_Button.setText(APP_Airble_Model_Array.get(i).getNick_Name());
            }
            if(i == ((MainActivity) MainActivity.Main_Context).select_airble_num){
                device_Button.setTypeface(getResources().getFont(R.font.gothic_a1_900_black));
                device_Button.setTextSize(Dimension.DP, 30);
                device_Button.setTextColor(getResources().getColor(R.color.home_device_select_font_color));
            }else{
                device_Button.setTypeface(getResources().getFont(R.font.gothic_a1_700_bold));
                device_Button.setTextSize(Dimension.DP, 30);
                device_Button.setTextColor(Color.argb(0x70,0x42,0x85,0xFA));
            }
            device_Button_LayoutParms.setMargins(getResources().getDimensionPixelOffset(R.dimen.public_7dp), 0, 0, 0);
            device_Button.setLayoutParams(device_Button_LayoutParms);
            device_Button.setStateListAnimator(null);

            device_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!device_list_running) {
                        //device_list_running = true;
                        ((MainActivity) MainActivity.Main_Context).select_airble_num = select_device_num;
                        device_ViewPager2.setCurrentItem(select_device_num);
                        //device_select_Observable.subscribeOn(Schedulers.io()).subscribe(device_select_Observer);
                        // 바뀐 후 바로 갱신 1회
                        //                           Setting_Data();
                        //Device_List_Refresh();
                    }

                }
            });

            device_inside_list_LinearLayout.addView(device_Button);

        }

    }

    public void Refresh_Home_Fragment(){
        if(Main_Refresh_Airble_Model_Array_Bool){
            Main_Refresh_Airble_Model_Array_Bool = false;
            Toast.makeText(MainActivity.Main_Context, "기기 리스트가 변경되었습니다.",Toast.LENGTH_SHORT).show();
        }
        ((MainActivity)Main_Context)._1_Home_Fragment = new _1_HomeFragment();
        ((MainActivity)Main_Context).getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, ((MainActivity)Main_Context)._1_Home_Fragment).commit();
    }

    public void Setting_Device_Data(int device_position){
        _1_Home_ViewHolderPage device_ViewHolderPage = device_ViewPager2_ViewHolder.get(device_position);
        _1_Home_Gauge_ViewHolderPage outside_viewHolderPage = device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(1);

        int pm_value = APP_Airble_Model_Array.get(device_position).getPM();
        int vocs_value = APP_Airble_Model_Array.get(device_position).getVOCs();
        int co_value = APP_Airble_Model_Array.get(device_position).getCO();
        int co2_value = APP_Airble_Model_Array.get(device_position).getCO2();

        if(APP_Airble_Model_Array.get(device_position).getUpdate_date() != null){
            LocalDateTime now_time = LocalDateTime.now();
            LocalDateTime data_time = APP_Airble_Model_Array.get(device_position).getUpdate_date();

            long now_time_long = ( now_time.getYear() * 31536000l ) + ( now_time.getDayOfYear() * 86400l ) + ( now_time.getHour() * 3600l ) + ( now_time.getMinute() * 60l ) + now_time.getSecond();
            long data_time_long = ( data_time.getYear() * 31536000l ) + ( data_time.getDayOfYear() * 86400l ) + ( data_time.getHour() * 3600l ) + ( data_time.getMinute() * 60l ) + data_time.getSecond();
            long update_interval = now_time_long - data_time_long;


            if(update_interval < 60){
                device_ViewHolderPage.connect_on = true;

                Setting_Status_Data(device_ViewHolderPage.pm_value_TextView, device_ViewHolderPage.pm_status_TextView, pm_value, PM_GOOD, PM_SOSO, PM_BAD);
                Setting_Status_Data(device_ViewHolderPage.vocs_value_TextView, device_ViewHolderPage.vocs_status_TextView, vocs_value, VOCs_GOOD, VOCs_SOSO, VOCs_BAD);
                Setting_Status_Data(device_ViewHolderPage.co_value_TextView, device_ViewHolderPage.co_status_TextView, co_value, CO_GOOD, CO_SOSO, CO_BAD);
                Setting_Status_Data(device_ViewHolderPage.co2_value_TextView, device_ViewHolderPage.co2_status_TextView, co2_value, CO2_GOOD, CO2_SOSO, CO2_BAD);
                device_ViewHolderPage.temp_value_TextView.setText(((double) APP_Airble_Model_Array.get(device_position).getTemp() / 10) + "");
                device_ViewHolderPage.humi_value_TextView.setText(((double) APP_Airble_Model_Array.get(device_position).getHumi() / 10) + "");

                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(PM + 2), pm_value, PM_GOOD, PM_SOSO, PM_BAD);
                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(VOCs + 2), vocs_value, VOCs_GOOD, VOCs_SOSO, VOCs_BAD);
                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(CO + 2), co_value, CO_GOOD, CO_SOSO, CO_BAD);
                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(CO2 + 2), co2_value, CO2_GOOD, CO2_SOSO, CO2_BAD);

            }else{
                device_ViewHolderPage.connect_on = false;

                Setting_Status_Data(device_ViewHolderPage.pm_value_TextView, device_ViewHolderPage.pm_status_TextView, 0, PM_GOOD, PM_SOSO, PM_BAD);
                Setting_Status_Data(device_ViewHolderPage.vocs_value_TextView, device_ViewHolderPage.vocs_status_TextView, 0, VOCs_GOOD, VOCs_SOSO, VOCs_BAD);
                Setting_Status_Data(device_ViewHolderPage.co_value_TextView, device_ViewHolderPage.co_status_TextView, 0, CO_GOOD, CO_SOSO, CO_BAD);
                Setting_Status_Data(device_ViewHolderPage.co2_value_TextView, device_ViewHolderPage.co2_status_TextView, 0, CO2_GOOD, CO2_SOSO, CO2_BAD);
                device_ViewHolderPage.temp_value_TextView.setText("0.0");
                device_ViewHolderPage.humi_value_TextView.setText("0.0");

                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(PM + 2), 0, PM_GOOD, PM_SOSO, PM_BAD);
                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(VOCs + 2), 0, VOCs_GOOD, VOCs_SOSO, VOCs_BAD);
                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(CO + 2), 0, CO_GOOD, CO_SOSO, CO_BAD);
                Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(CO2 + 2), 0, CO2_GOOD, CO2_SOSO, CO2_BAD);
            }
        }else{
            device_ViewHolderPage.connect_on = false;

            Setting_Status_Data(device_ViewHolderPage.pm_value_TextView, device_ViewHolderPage.pm_status_TextView, 0, PM_GOOD, PM_SOSO, PM_BAD);
            Setting_Status_Data(device_ViewHolderPage.vocs_value_TextView, device_ViewHolderPage.vocs_status_TextView, 0, VOCs_GOOD, VOCs_SOSO, VOCs_BAD);
            Setting_Status_Data(device_ViewHolderPage.co_value_TextView, device_ViewHolderPage.co_status_TextView, 0, CO_GOOD, CO_SOSO, CO_BAD);
            Setting_Status_Data(device_ViewHolderPage.co2_value_TextView, device_ViewHolderPage.co2_status_TextView, 0, CO2_GOOD, CO2_SOSO, CO2_BAD);
            device_ViewHolderPage.temp_value_TextView.setText("0.0");
            device_ViewHolderPage.humi_value_TextView.setText("0.0");

            Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(PM + 2), 0, PM_GOOD, PM_SOSO, PM_BAD);
            Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(VOCs + 2), 0, VOCs_GOOD, VOCs_SOSO, VOCs_BAD);
            Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(CO + 2), 0, CO_GOOD, CO_SOSO, CO_BAD);
            Setting_Status_Graph_DataGraph_Set_Data(device_ViewHolderPage.device_sensor_ViewPager2_ViewHolder.get(CO2 + 2), 0, CO2_GOOD, CO2_SOSO, CO2_BAD);

        }

        outside_viewHolderPage.gauge_outside_place_value_TextView.setText(APP_Airble_Model_Array.get(device_position).getPlace_name());
        outside_viewHolderPage.gauge_outside_pm_value_TextView.setText(APP_Airble_Model_Array.get(device_position).getOutside_PM() + " ㎍/㎥");
        outside_viewHolderPage.pm_graph.setValue(APP_Airble_Model_Array.get(device_position).getOutside_PM());
        outside_viewHolderPage.gauge_outside_temp_value_TextView.setText(APP_Airble_Model_Array.get(device_position).getOutside_Temp() + " ℃");
        outside_viewHolderPage.temp_graph.setValue((int)(APP_Airble_Model_Array.get(device_position).getOutside_Temp() * 10 ));
        outside_viewHolderPage.gauge_outside_humi_value_TextView.setText(APP_Airble_Model_Array.get(device_position).getOutside_Humi() + " %");
        outside_viewHolderPage.humi_graph.setValue((int)(APP_Airble_Model_Array.get(device_position).getOutside_Humi() * 10 ));
    }

    public void Setting_Status_Data(TextView value_TextView, TextView status_TextView, int value ,int good, int soso, int bad){
        value_TextView.setText(value + "");

        if(value < good){
            status_TextView.setText("좋음");
            status_TextView.setBackground(getResources().getDrawable(R.drawable.status_good_block));
        }else if(value < soso){
            status_TextView.setText("보통");
            status_TextView.setBackground(getResources().getDrawable(R.drawable.status_soso_block));
        }else if(value < bad){
            status_TextView.setText("나쁨");
            status_TextView.setBackground(getResources().getDrawable(R.drawable.status_bad_block));
        }else{
            status_TextView.setText("매우나쁨");
            status_TextView.setBackground(getResources().getDrawable(R.drawable.status_very_bad_block));
        }
    }

    public void Setting_Status_Graph_DataGraph_Set_Data(_1_Home_Gauge_ViewHolderPage viewHolderPage, int value, int good_value, int soso_value, int bad_value){
        viewHolderPage.graph.setValue(value);
        viewHolderPage.gauge_matter_value_TextView.setText(value + "");
        if(value > bad_value){
            viewHolderPage.gauge_matter_value_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
            viewHolderPage.gauge_matter_value_status_TextView.setText("매우 나쁨");
            viewHolderPage.gauge_matter_value_status_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
            viewHolderPage.graph.setColor_position(3);
        }else if(value > soso_value){
            viewHolderPage.gauge_matter_value_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
            viewHolderPage.gauge_matter_value_status_TextView.setText("나쁨");
            viewHolderPage.gauge_matter_value_status_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
            viewHolderPage.graph.setColor_position(2);
        }else if(value > good_value){
            viewHolderPage.gauge_matter_value_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
            viewHolderPage.gauge_matter_value_status_TextView.setText("보통");
            viewHolderPage.gauge_matter_value_status_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
            viewHolderPage.graph.setColor_position(1);
        }else{
            viewHolderPage.gauge_matter_value_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
            viewHolderPage.gauge_matter_value_status_TextView.setText("좋음");
            viewHolderPage.gauge_matter_value_status_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
            viewHolderPage.graph.setColor_position(0);
        }
    }

}
