package stm.airble._4_main._5_setting.airble.viewpager2;

import static android.app.Activity.RESULT_OK;
import static stm.airble._4_main.MainActivity.Main_Loading;
import static stm.airble._0_public.Public_Values.*;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._5_setting.airble._51_SettingsAirbleFragment;
import stm.airble._4_main._5_setting.airble.viewpager2.recyclerview.Main_Setting_Shared_RecyclerViewAdapter;
import stm.airble._4_main._5_setting.dialog.Shared_Dialog_Activity;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;

public class ViewHolderPage_2 extends Fragment {

    LinearLayout shared_LinearLayout;
    public TextView shared_TextView, shared_key_TextView;
    LinearLayout shared_list_LinearLayout;
    public RecyclerView shared_RecyclerView;
    public TextView null_shared_TextView;

    public Main_Setting_Shared_RecyclerViewAdapter shared_adapter;

    public ActivityResultLauncher<Intent> shared_ActivityResultLauncher, shared_remove_ActivityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings_airble_setting_page_2, container, false);

        // 아이디 맞춰주기
        shared_LinearLayout = rootView.findViewById(R.id.settings_airble_shared_LinearLayout);
        shared_TextView = rootView.findViewById(R.id.settings_airble_shared_TextView);
        shared_key_TextView = rootView.findViewById(R.id.settings_airble_shared_key_TextView);
        shared_list_LinearLayout = rootView.findViewById(R.id.settings_airble_shared_list_LinearLayout);
        shared_RecyclerView = rootView.findViewById(R.id.settings_airble_shared_RecyclerView);
        null_shared_TextView = rootView.findViewById(R.id.settings_airble_null_shared_TextView);

        shared_ActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 기기 지우기 넣어주기 -> 데이터 리셋 X\
                    if(result.getResultCode() == RESULT_OK){
                        shared_TextView.setText("공유하기");
                        shared_key_TextView.setText("");
                    }
                });

        shared_remove_ActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == RESULT_OK){
                        try{
                            Main_Loading.Start_Loading();
                            String server_url = Server_Domain + "airble_test?num=425&email=" + User_Email +"&mac=" + APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                            URL url = new URL(server_url);
                            Log.d("Sans", "server_url = " + server_url);
                            new Airble_APP_HttpConnection().execute(url);
                        }catch (Exception e){
                            Main_Loading.Stop_Loading();
                        }
                    }
                });

        shared_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()) {
                    if (shared_key_TextView.getText().toString().trim().equals("")) {
                        try {
                            String server_url = Server_Domain + "airble_test?num=422&email=" + User_Email + "&mac=" + APP_Airble_Model_Array.get(((MainActivity) MainActivity.Main_Context).select_setting_airble_num).getMAC_Address();
                            Log.d("Sans", "server_url = " + server_url);
                            URL url = new URL(server_url);
                            new Airble_APP_HttpConnection().execute(url);
                        } catch (Exception e) {

                        }
                    }
                }
            }
        });

        shared_LinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner() && !shared_key_TextView.getText().toString().trim().equals("")) {
                    Intent intent = new Intent(MainActivity.Main_Context, Shared_Dialog_Activity.class);
                    shared_ActivityResultLauncher.launch(intent);
                }
                return false;
            }
        });

        if(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).isOwner()){
            if(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_key.trim().equals("0000000000")){
                shared_TextView.setText("공유하기");
                shared_key_TextView.setText("");

            }else{
                shared_TextView.setText("공유키");
                shared_key_TextView.setText(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_key.trim());
            }

            if(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.size() == 0){
                shared_RecyclerView.setVisibility(View.GONE);
                null_shared_TextView.setVisibility(View.VISIBLE);
            }else{
                shared_RecyclerView.setVisibility(View.VISIBLE);
                null_shared_TextView.setVisibility(View.GONE);

                shared_adapter = new Main_Setting_Shared_RecyclerViewAdapter(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array);
                shared_RecyclerView.setAdapter(shared_adapter);
                shared_RecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.Main_Context));
            }

        }else{
            shared_list_LinearLayout.setVisibility(View.GONE);
            shared_TextView.setText("공유해준 사람");
            shared_key_TextView.setText(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.mater_email.trim());
        }

        return rootView;
    }

    //서버에 연결하는 코딩
    private class Airble_APP_HttpConnection extends AsyncTask<URL, Integer, String> {

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
                        shared_TextView.setText("공유키");
                        shared_key_TextView.setText(data.split(",")[1].trim());

                    }
                    break;

                    case "S425":
                    {
                        String email[] = data.split(",");
                        ((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.clear();

                        for(int i = 1; i < email.length; i++){
                            ((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.add(email[i]);
                        }

                        Toast.makeText(MainActivity.Main_Context, "공유가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                        shared_adapter.notifyDataSetChanged();
                        if(((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.shared_array.size() == 0){
                            shared_RecyclerView.setVisibility(View.GONE);
                            null_shared_TextView.setVisibility(View.VISIBLE);
                        }else{
                            shared_RecyclerView.setVisibility(View.VISIBLE);
                            null_shared_TextView.setVisibility(View.GONE);
                        }
                        Main_Loading.Stop_Loading();
                    }
                    break;

                    case "E425":
                    case "F425":
                    {
                        Main_Loading.Stop_Loading();
                        Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                }

            } else {  //연결실패
                Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}