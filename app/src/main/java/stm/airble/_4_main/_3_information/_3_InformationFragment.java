package stm.airble._4_main._3_information;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class _3_InformationFragment extends Fragment {

    RelativeLayout app_RelativeLayout, matter_RelativeLayout, app_manual_RelativeLayout, airble_manual_RelativeLayout;
    TextView app_main_TextView, matter_main_TextView, app_manual_main_TextView, airble_manual_main_TextView;
    TextView app_sub_TextView, matter_sub_TextView, app_manual_sub_TextView, airble_manual_sub_TextView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        //아이디 맞춰주기
        app_RelativeLayout = view.findViewById(R.id.information_app_RelativeLayout);
        matter_RelativeLayout = view.findViewById(R.id.information_matter_RelativeLayout);
        app_manual_RelativeLayout = view.findViewById(R.id.information_app_manual_RelativeLayout);
        airble_manual_RelativeLayout = view.findViewById(R.id.information_airble_manual_RelativeLayout);
        app_main_TextView = view.findViewById(R.id.information_app_main_TextView);
        matter_main_TextView = view.findViewById(R.id.information_matter_main_TextView);
        app_manual_main_TextView = view.findViewById(R.id.information_app_manual_main_TextView);
        airble_manual_main_TextView = view.findViewById(R.id.information_airble_manual_main_TextView);
        app_sub_TextView = view.findViewById(R.id.information_app_sub_TextView);
        matter_sub_TextView = view.findViewById(R.id.information_matter_sub_TextView);
        app_manual_sub_TextView = view.findViewById(R.id.information_app_manual_sub_TextView);
        airble_manual_sub_TextView = view.findViewById(R.id.information_airble_manual_sub_TextView);



        app_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_31_Information_Page();
            }
        });

        matter_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)MainActivity.Main_Context)._32_Change_Page_Information_Matter_Fragment();
            }
        });

        app_manual_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)MainActivity.Main_Context)._33_Change_Page_Information_Airble_Manual_Fragment();
            }
        });

        airble_manual_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity)MainActivity.Main_Context)._34_Change_Page_Information_APP_Manual_Fragment();
            }
        });

        return view;
    }
}
