package stm.airble._4_main._5_setting.dialog;

import static stm.airble._0_public.Public_Values.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;

public class Change_Place_Dialog_Activity extends AppCompatActivity {

    Loading_ProgressDialog Place_Loading;

    TextView title_TextView;
    LinearLayout place_LinearLayout, place_cancel_LinearLayout;
    TextView place_TextView, place_cancel_TextView;

    Button find_current_location_Button;

    TextInputLayout place_division_1_TextInputLayout, place_division_2_TextInputLayout, place_division_3_TextInputLayout;
    AutoCompleteTextView place_division_1_AutoCompleteTextView, place_division_2_AutoCompleteTextView, place_division_3_AutoCompleteTextView;

    ArrayList<String> place_division_1_ArrayList, place_division_2_ArrayList, place_division_3_ArrayList;
    ArrayAdapter<String> place_division_1_ArrayAdapter;
    ArrayAdapter place_division_2_ArrayAdapter;
    ArrayAdapter<String> place_division_3_ArrayAdapter;

    String select_division_1 = "", select_division_2 = "", select_division_3 = "";
    ArrayList<String> division_1_array, division_2_array, division_3_array, lat_lon_array;

    LocationManager location_manger;
    Location last_known_location;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_airble_change_place);

        Place_Loading = new Loading_ProgressDialog(this);

        // 아이디 맞춰주기
        title_TextView = findViewById(R.id.title_TextView);
        place_division_1_TextInputLayout = findViewById(R.id.place_division_1_TextInputLayout);
        place_division_1_AutoCompleteTextView = findViewById(R.id.place_division_1_AutoCompleteTextView);
        place_division_2_TextInputLayout = findViewById(R.id.place_division_2_TextInputLayout);
        place_division_2_AutoCompleteTextView = findViewById(R.id.place_division_2_AutoCompleteTextView);
        place_division_3_TextInputLayout = findViewById(R.id.place_division_3_TextInputLayout);
        place_division_3_AutoCompleteTextView = findViewById(R.id.place_division_3_AutoCompleteTextView);
        find_current_location_Button = findViewById(R.id.find_current_location_Button);
        place_LinearLayout = findViewById(R.id.main_settings_place_LinearLayout);
        place_cancel_LinearLayout = findViewById(R.id.main_settings_place_cancel_LinearLayout);
        place_TextView = findViewById(R.id.main_settings_place_TextView);
        place_cancel_TextView = findViewById(R.id.main_settings_place_cancel_TextView);

        division_1_array = new ArrayList<>();
        division_2_array = new ArrayList<>();
        division_3_array = new ArrayList<>();
        lat_lon_array = new ArrayList<>();

        division_1_array.addAll(Arrays.asList(getResources().getStringArray(R.array.address_division_1)));
        division_2_array.addAll(Arrays.asList(getResources().getStringArray(R.array.address_division_2)));
        division_3_array.addAll(Arrays.asList(getResources().getStringArray(R.array.address_division_3)));
        lat_lon_array.addAll(Arrays.asList(getResources().getStringArray(R.array.lati_long_location)));

        find_current_location_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat, lon, distance;

                place_division_1_AutoCompleteTextView.setText("");
                place_division_2_AutoCompleteTextView.setText("");
                place_division_3_AutoCompleteTextView.setText("");

                if (ActivityCompat.checkSelfPermission(Change_Place_Dialog_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(Change_Place_Dialog_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // 권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Change_Place_Dialog_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            && ActivityCompat.shouldShowRequestPermissionRationale(Change_Place_Dialog_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        // 권한 재요청
                        ActivityCompat.requestPermissions(Change_Place_Dialog_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                        return;
                    } else {
                        ActivityCompat.requestPermissions(Change_Place_Dialog_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                        return;
                    }
                }

                location_manger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Log.d("Sans", "location_manger == null? : " + (location_manger == null));
                if(location_manger.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null){
                    last_known_location = location_manger.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }else{
                    last_known_location = location_manger.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

                if(last_known_location == null){
                    Toast.makeText(Change_Place_Dialog_Activity.this, "현재 위치를 찾을 수 없습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }else {
                    lat = last_known_location.getLatitude();
                    lon = last_known_location.getLongitude();
                    distance = 99999;

                    double lat_distance, lon_distance;
                    for (int i = 0; i < lat_lon_array.size(); i++) {
                        lon_distance = Double.parseDouble(lat_lon_array.get(i).split("_")[3]) - lon;
                        lat_distance = Double.parseDouble(lat_lon_array.get(i).split("_")[4]) - lat;
                        if ((lon_distance * lon_distance) + (lat_distance * lat_distance) < distance) {
                            distance = (lon_distance * lon_distance) + (lat_distance * lat_distance);
                            select_division_1 = lat_lon_array.get(i).split("_")[0];
                            select_division_2 = lat_lon_array.get(i).split("_")[1];
                            select_division_3 = lat_lon_array.get(i).split("_")[2];
                        }
                    }
                    place_division_1_ArrayList.clear();
                    place_division_2_ArrayList.clear();
                    place_division_3_ArrayList.clear();

                    for (int i = 0; i < division_1_array.size(); i++) {
                        place_division_1_ArrayList.add(division_1_array.get(i).split("_")[1]);
                    }

                    for (int i = 0; i < division_2_array.size(); i++) {
                        if (division_2_array.get(i).split("_")[0].trim().equals(select_division_1)) {
                            place_division_2_ArrayList.add(division_2_array.get(i).split("_")[2]);
                        }
                    }

                    if (!select_division_2.equals("0")) {
                        for (int i = 0; i < division_3_array.size(); i++) {
                            if (division_3_array.get(i).split("_")[0].trim().equals(select_division_1)) {
                                if (division_3_array.get(i).split("_")[1].trim().equals(select_division_2)) {
                                    place_division_3_ArrayList.add(division_3_array.get(i).split("_")[3]);
                                }
                            }
                        }
                    }

                    place_division_1_AutoCompleteTextView.setText(place_division_1_ArrayList.get(Integer.parseInt(select_division_1) - 1));
                    if (!select_division_2.equals("0")) {
                        place_division_2_AutoCompleteTextView.setText(place_division_2_ArrayList.get(Integer.parseInt(select_division_2) - 1));
                        if (!select_division_3.equals("0")) {
                            place_division_3_AutoCompleteTextView.setText(place_division_3_ArrayList.get(Integer.parseInt(select_division_3) - 1));
                        }
                    }

                    place_division_1_AutoCompleteTextView.clearFocus();
                    place_division_2_AutoCompleteTextView.clearFocus();
                    place_division_3_AutoCompleteTextView.clearFocus();
                    place_division_1_ArrayAdapter = new ArrayAdapter<>(Change_Place_Dialog_Activity.this, R.layout.fragment_settings_airble_change_place_item, place_division_1_ArrayList);
                    place_division_2_ArrayAdapter = new ArrayAdapter<>(Change_Place_Dialog_Activity.this, R.layout.fragment_settings_airble_change_place_item, place_division_2_ArrayList);
                    place_division_3_ArrayAdapter = new ArrayAdapter<>(Change_Place_Dialog_Activity.this, R.layout.fragment_settings_airble_change_place_item, place_division_3_ArrayList);
                    place_division_1_AutoCompleteTextView.setAdapter(place_division_1_ArrayAdapter);
                    place_division_2_AutoCompleteTextView.setAdapter(place_division_2_ArrayAdapter);
                    place_division_3_AutoCompleteTextView.setAdapter(place_division_3_ArrayAdapter);
                }

            }
        });

        /**
         * 위치 수동으로 찾기
         */
        place_division_1_ArrayList = new ArrayList<>();
        for(int i = 0; i < division_1_array.size(); i++) {
            place_division_1_ArrayList.add(division_1_array.get(i).split("_")[1]);
        }
        place_division_2_ArrayList = new ArrayList<>();
        place_division_3_ArrayList = new ArrayList<>();

        place_division_1_ArrayAdapter = new ArrayAdapter<>(this, R.layout.fragment_settings_airble_change_place_item, place_division_1_ArrayList);
        place_division_2_ArrayAdapter = new ArrayAdapter<>(this, R.layout.fragment_settings_airble_change_place_item, place_division_2_ArrayList);
        place_division_3_ArrayAdapter = new ArrayAdapter<>(this, R.layout.fragment_settings_airble_change_place_item, place_division_3_ArrayList);

        place_division_1_AutoCompleteTextView.setAdapter(place_division_1_ArrayAdapter);
        place_division_2_AutoCompleteTextView.setAdapter(place_division_2_ArrayAdapter);
        place_division_3_AutoCompleteTextView.setAdapter(place_division_3_ArrayAdapter);

        place_division_1_AutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                place_division_1_AutoCompleteTextView.clearFocus();
                place_division_2_ArrayList.clear();
                place_division_3_ArrayList.clear();
                select_division_1 = (position + 1) + "";
                for(int i = 0; i < division_2_array.size(); i++) {
                    if(division_2_array.get(i).split("_")[0].trim().equals(select_division_1)) {
                        place_division_2_ArrayList.add(division_2_array.get(i).split("_")[2]);
                    }
                }
                select_division_2 = "0";
                select_division_3 = "0";
                place_division_2_AutoCompleteTextView.setText("");
                place_division_3_AutoCompleteTextView.setText("");
                place_division_2_ArrayAdapter = new ArrayAdapter<>(Change_Place_Dialog_Activity.this, R.layout.fragment_settings_airble_change_place_item, place_division_2_ArrayList);
                place_division_3_ArrayAdapter = new ArrayAdapter<>(Change_Place_Dialog_Activity.this, R.layout.fragment_settings_airble_change_place_item, place_division_3_ArrayList);
                place_division_2_AutoCompleteTextView.setAdapter(place_division_2_ArrayAdapter);
                place_division_3_AutoCompleteTextView.setAdapter(place_division_3_ArrayAdapter);
            }
        });

        place_division_2_AutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                place_division_2_AutoCompleteTextView.clearFocus();
                place_division_3_ArrayList.clear();
                select_division_2 = (position + 1) + "";
                for(int i = 0; i < division_3_array.size(); i++) {
                    if(division_3_array.get(i).split("_")[0].trim().equals(select_division_1)) {
                        if (division_3_array.get(i).split("_")[1].trim().equals(select_division_2)) {
                            place_division_3_ArrayList.add(division_3_array.get(i).split("_")[3]);
                        }
                    }
                }
                select_division_3 = "0";
                place_division_3_AutoCompleteTextView.setText("");
                place_division_3_ArrayAdapter = new ArrayAdapter<>(Change_Place_Dialog_Activity.this, R.layout.fragment_settings_airble_change_place_item, place_division_3_ArrayList);
                place_division_3_AutoCompleteTextView.setAdapter(place_division_3_ArrayAdapter);
            }
        });

        place_division_3_AutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                place_division_3_AutoCompleteTextView.clearFocus();
                select_division_3 = (position + 1) + "";
            }
        });

        /**
         *  위치 변경 취소 버튼
         */
        {
            place_cancel_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            place_cancel_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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
         *  위치 변경 하기 버튼
         */
        {
            place_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(place_division_1_AutoCompleteTextView.getText().toString().trim().equals("")){
                            Toast.makeText(Change_Place_Dialog_Activity.this, "지역을 선택해 주세요.", Toast.LENGTH_SHORT).show();
                        }else {

                            Place_Loading.Start_Loading();
                            String server_url = Server_Domain + "airble_test?num=473&mac=";
                            if (((MainActivity) MainActivity.Main_Context).select_setting_airble_num == -1) {
                                server_url += APP_Airble_Model_Array.get(((MainActivity) MainActivity.Main_Context).select_airble_num).getMAC_Address();
                            } else {
                                server_url += APP_Airble_Model_Array.get(((MainActivity) MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                            }
                            server_url += "&division_1=" + place_division_1_AutoCompleteTextView.getText().toString().trim() +
                                    "&division_2=" + place_division_2_AutoCompleteTextView.getText().toString().trim() +
                                    "&division_3=" + place_division_3_AutoCompleteTextView.getText().toString().trim();
                            URL url = new URL(server_url);
                            Log.d("Sans", "server_url = " + server_url);
                            new Load_Airble_Status_HttpConnection().execute(url);
                        }
                    } catch (Exception e) {
                        Place_Loading.Stop_Loading();
                        Toast.makeText(Change_Place_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            place_LinearLayout.setOnTouchListener(new View.OnTouchListener() {
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

                    case "S473":
                    {
                        Place_Loading.Stop_Loading();
                        Toast.makeText(Change_Place_Dialog_Activity.this, "위치를 변경하였습니다.", Toast.LENGTH_SHORT).show();
                        String place_name = place_division_1_AutoCompleteTextView.getText().toString().trim() + " " + place_division_2_AutoCompleteTextView.getText().toString().trim() + " " + place_division_3_AutoCompleteTextView.getText().toString().trim();
                        if(((MainActivity)MainActivity.Main_Context).select_setting_airble_num == -1) {
                            APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_airble_num).setPlace_name(place_name.trim());
                        }else{
                            APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).setPlace_name(place_name.trim());
                        }
                        finish();
                    }
                    break;

                    case "E473":
                    case "F473":
                    {
                        Place_Loading.Stop_Loading();
                        Toast.makeText(Change_Place_Dialog_Activity.this, "설정에 실패하였습니다. 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                Place_Loading.Stop_Loading();
                Toast.makeText(Change_Place_Dialog_Activity.this, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}