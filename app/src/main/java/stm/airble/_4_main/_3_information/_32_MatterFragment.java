package stm.airble._4_main._3_information;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class _32_MatterFragment extends Fragment {

    ImageButton back_Button;

    RelativeLayout fine_dust_RelativeLayout, vocs_RelativeLayout, co_RelativeLayout, co2_RelativeLayout, temp_RelativeLayout, humi_RelativeLayout;
    TextView fine_dust_TextView, vocs_TextView, co_TextView, co2_TextView, temp_TextView, humi_TextView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_matter, container, false);

        // 아이디 맞춰주기
        back_Button = view.findViewById(R.id.back_Button);
        fine_dust_RelativeLayout = view.findViewById(R.id.information_matter_find_dust_RelativeLayout);
        vocs_RelativeLayout = view.findViewById(R.id.information_matter_vocs_RelativeLayout);
        co_RelativeLayout = view.findViewById(R.id.information_matter_co_RelativeLayout);
        co2_RelativeLayout = view.findViewById(R.id.information_matter_co2_RelativeLayout);
        temp_RelativeLayout = view.findViewById(R.id.information_matter_temp_RelativeLayout);
        humi_RelativeLayout = view.findViewById(R.id.information_matter_humi_RelativeLayout);
        fine_dust_TextView = view.findViewById(R.id.information_matter_find_dust_TextView);
        vocs_TextView = view.findViewById(R.id.information_matter_vocs_TextView);
        co_TextView = view.findViewById(R.id.information_matter_co_TextView);
        co2_TextView = view.findViewById(R.id.information_matter_co2_TextView);
        temp_TextView = view.findViewById(R.id.information_matter_temp_TextView);
        humi_TextView = view.findViewById(R.id.information_matter_humi_TextView);

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_3_Information();
            }
        });
/*
        fine_dust_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatterActivity.this, FineDustActivity.class);
                startActivity(intent);
            }
        });
        vocs_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatterActivity.this, VOCsActivity.class);
                startActivity(intent);
            }
        });
        co_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatterActivity.this, CoActivity.class);
                startActivity(intent);
            }
        });
        co2_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatterActivity.this, Co2Activity.class);
                startActivity(intent);
            }
        });
        temp_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatterActivity.this, TemperatureActivity.class);
                startActivity(intent);
            }
        });
        humi_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatterActivity.this, HumidityActivity.class);
                startActivity(intent);
            }
        });
        */
        return view;
    }

}