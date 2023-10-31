package stm.airble._4_main._5_setting;


import static stm.airble._4_main.MainActivity.Main_Context;
import static stm.airble._4_main.MainActivity.Main_Fragment_ID;
import static stm.airble._4_main.MainActivity.Main_Get_Airble_Models_Status_Bool;
import static stm.airble._4_main.MainActivity.Main_Loading;
import static stm.airble._4_main.MainActivity.Main_Refresh_Airble_Model_Array_Bool;
import static stm.airble._4_main.MainActivity.select_airble_num;
import static stm.airble._4_main.MainActivity.select_setting_airble_viewpager_page_num;
import static stm.airble._0_public.Public_Values.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import stm.airble.R;
import stm.airble._1_splash.SplashActivity;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._5_setting.dialog.LogOut_Dialog_Activity;
import stm.airble._4_main._5_setting.viewpager2.Main_Setting_ViewPagerAdapter;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._5_add_airble.AddAirbleActivity;

public class _5_SettingsFragment extends Fragment {

    public static Fragment Settings_Context;
    public boolean setting_start;

    public ViewPager2 settings_devices_ViewPager2;
    public Main_Setting_ViewPagerAdapter settings_devices_ViewPager2_adapter;
    Button settings_device_add_Button;
    ActivityResultLauncher<Intent> add_launcher;

    NestedScrollView settings_NestedScrollView;
    LinearLayout settings_LinearLayout;

    TextView settings_alarm_TextView, settings_terms_TextView, settings_homepage_TextView, settings_logout_TextView;

    ActivityResultLauncher<Intent> alarm_ActivityResultLauncher;
    ActivityResultLauncher<Intent> logout_ActivityResultLauncher;

    // 기기 리스트 변경 사항 확인용
    public Disposable device_list_check_Disposable;
    public MutableLiveData<Long> device_list_check_Mutable;
    public Observable<Long> device_list_check_Observable;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Settings_Context = this;
        _5_Setting_Setting(view);
        Setting_Device_ViewPager();

        return view;
    }

    public void _5_Setting_Setting(View view){
        // 아이디 맞춰주기
        {
            settings_devices_ViewPager2 = view.findViewById(R.id.settings_devices_ViewPager2);
            settings_device_add_Button = view.findViewById(R.id.settings_device_add_Button);

            settings_NestedScrollView = view.findViewById(R.id.settings_NestedScrollView);
            settings_LinearLayout = view.findViewById(R.id.settings_LinearLayout);

            settings_alarm_TextView = view.findViewById(R.id.settings_alarm_TextView);
            settings_terms_TextView = view.findViewById(R.id.settings_terms_TextView);
            settings_homepage_TextView = view.findViewById(R.id.settings_homepage_TextView);
            settings_logout_TextView = view.findViewById(R.id.settings_logout_TextView);
        }

        // 기기 추가 버튼
        settings_device_add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_launcher.launch(new Intent(MainActivity.Main_Context, AddAirbleActivity.class));
            }
        });

        // 알람 설정 관련
        settings_alarm_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
//                alarm_ActivityResultLauncher.launch(intent);
            }
        });

        // 이용약관
        settings_terms_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_52_Setting();
            }
        });

        // 홈페이지 바로가기
        settings_homepage_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://airble.stmtek.co.kr"));
                startActivity(intent);
            }
        });

        // 로그아웃
        settings_logout_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout_ActivityResultLauncher.launch(new Intent(MainActivity.Main_Context, LogOut_Dialog_Activity.class));
            }
        });

        // launcher
        {
            add_launcher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Reset_Airble_Model_Array(MainActivity.Main_Context);

                    });

            alarm_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
    //                    Log.d("Sans", "NotificationManagerCompat.from(settings_Context.getContext()).areNotificationsEnabled() = " + NotificationManagerCompat.from(settings_Context.getContext()).areNotificationsEnabled());
    //                    Stop_Background_Thread();
    //                    if(NotificationManagerCompat.from(settings_Context.getContext()).areNotificationsEnabled()){
    //                        try{
    //                            Log.d("Sans", "User_Email = '" + User_Email + "'");
    //                            Intent serviceIntent = new Intent(MainActivity.Main_Context, Background_Alarm_Service.class);
    //                            serviceIntent.putExtra("User_Email", User_Email);
    //                            ((MainActivity)MainActivity.Main_Context).startForegroundService(serviceIntent);
    //                        }catch (Exception e){
    //                            Log.d("Sans", "알람 에러");
    //                        }
    //                    }


                });

            logout_ActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if(((MainActivity)MainActivity.Main_Context).logout_bool){
                            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.default_web_client_id))
                                    .requestEmail()
                                    .build();

                            GoogleSignInClient client = GoogleSignIn.getClient(MainActivity.Main_Context, googleSignInOptions);
                            FirebaseAuth auth = FirebaseAuth.getInstance();

                            client.signOut()
                                    .addOnCompleteListener((Activity)MainActivity.Main_Context, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            auth.signOut();
                                        }
                                    });
    //                        APP_Disposables.clear();
                            APP_Shared_Preferences_Editor.putString("User_Email", "");
                            APP_Shared_Preferences_Editor.putString("User_Nick_Name", "");
                            APP_Shared_Preferences_Editor.putBoolean("First_Start_bool", false);
                            APP_Shared_Preferences_Editor.commit();
                            Toast.makeText(MainActivity.Main_Context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            ((MainActivity)MainActivity.Main_Context).startActivity(new Intent(MainActivity.Main_Context, SplashActivity.class));
                            ((MainActivity)MainActivity.Main_Context).finish();
                        }
                    });

        }
    }

    public void Setting_Device_ViewPager(){
        Log.d("Sans", "뷰 페이져 셋팅 발동");
        settings_devices_ViewPager2_adapter = new Main_Setting_ViewPagerAdapter(APP_Airble_Model_Array);
        settings_devices_ViewPager2.setAdapter(settings_devices_ViewPager2_adapter);

        if(APP_Airble_Model_Array.size() == 1){
            settings_devices_ViewPager2.setOffscreenPageLimit(1);
        }else{
            settings_devices_ViewPager2.setOffscreenPageLimit(APP_Airble_Model_Array.size() + 1);
        }

        if(APP_Airble_Model_Array.size() != 1){
            settings_devices_ViewPager2.setCurrentItem(select_setting_airble_viewpager_page_num,false);
        }

        //settings_devices_ViewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        Display display = _5_SettingsFragment.Settings_Context.getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        int width = size.x;
        int ViewPager_Side_Margin = (width - (int)(getResources().getDimension(R.dimen.public_10dp) * 17)) / 2;
        Log.d("Sans", "width = " + width);

        CompositePageTransformer device_transformer  = new CompositePageTransformer();
        device_transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                float scale = 0.8f + (v * 0.3f);
                if(scale < 0.8){
                    page.setScaleY(0.8f);
                    page.setScaleX(0.8f);
                }else{
                    page.setScaleY(scale);
                    page.setScaleX(scale);
                }
                page.setTranslationX(-(ViewPager_Side_Margin * 2) * (position));

                LinearLayout viewpager2_LinearLayout = page.findViewById(R.id.settings_ViewPager2_device_inside_LinearLayout);
                TextView viewpager2_TextView = page.findViewById(R.id.settings_ViewPager2_device_TextView);
                if( v > 0 ){
                    viewpager2_LinearLayout.setAlpha(0.41f + (v * 0.59f));
                    viewpager2_TextView.setTextColor(Color.rgb((int)(0xff - (v * 0xb6)),(int)(0xff - (v * 0x71)), (int)(0xff - (v * 0x5))));
                }else{
                    viewpager2_LinearLayout.setAlpha(0.41f);
                    viewpager2_TextView.setTextColor(Color.rgb(0xFF,0xFF, 0xFF));
                }
            }
        });
        settings_devices_ViewPager2.setPageTransformer(device_transformer);

        settings_devices_ViewPager2.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = ViewPager_Side_Margin;
                outRect.left = ViewPager_Side_Margin;
            }
        });
    }

    public void Refresh_Setting_Fragment(){
        if(Main_Refresh_Airble_Model_Array_Bool){
            Main_Refresh_Airble_Model_Array_Bool = false;
            Toast.makeText(MainActivity.Main_Context, "기기 리스트가 변경되었습니다.",Toast.LENGTH_SHORT).show();
        }
        ((MainActivity)Main_Context)._5_Settings_Fragment = new _5_SettingsFragment();
        ((MainActivity)Main_Context).getSupportFragmentManager().beginTransaction().replace(Main_Fragment_ID, ((MainActivity)Main_Context)._5_Settings_Fragment).commit();
    }

    public void Start_Device_List_Check_LiveData(){
        device_list_check_Mutable = new MutableLiveData<>();
        device_list_check_Mutable.observe(this, new androidx.lifecycle.Observer<Long>() {
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
                    if (setting_start) {
                        setting_start = false;
                        settings_devices_ViewPager2.setVisibility(View.VISIBLE);
                        Main_Loading.Stop_Loading();
                    }
//                    for (int i = 0; i < device_ViewPager2_ViewHolder.size(); i++) {
//                        try {
//                            Setting_Device_Data(i);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    Set_Device_Title();
//                    device_ViewPager2.postInvalidate();
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
                                    Refresh_Setting_Fragment();
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
        });
        device_list_check_Observable = Observable.interval(1, TimeUnit.SECONDS);

        device_list_check_Disposable = device_list_check_Observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                device_list_check_Mutable.postValue(aLong);
            }
        });
    }

    public void Stop_Device_List_Check_LiveData(){
        if(device_list_check_Disposable != null){
            if(!device_list_check_Disposable.isDisposed()){
                device_list_check_Disposable.dispose();
            }
        }
    }


    @Override
    public void onResume() {
        Log.d("Sans", "Setting Resume");
        super.onResume();
        setting_start = true;
        settings_devices_ViewPager2.setVisibility(View.INVISIBLE);
        Start_Device_List_Check_LiveData();
        Main_Loading.Start_Loading();

    }

    @Override
    public void onPause() {
        Log.d("Sans", "Setting Pause");
        Main_Loading.Stop_Loading();
        Stop_Device_List_Check_LiveData();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d("Sans", "Setting Destroy");
        super.onDestroy();
    }
}
