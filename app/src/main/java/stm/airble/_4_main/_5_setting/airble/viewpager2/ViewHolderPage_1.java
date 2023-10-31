package stm.airble._4_main._5_setting.airble.viewpager2;

import static stm.airble._0_public.Public_Values.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class ViewHolderPage_1 extends Fragment {

    TextView information_model_TextView, information_model_num_TextView, information_mac_TextView, information_ssid_TextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings_airble_setting_page_1, container, false);

        // 아이디 맞춰주기
        information_model_TextView = rootView.findViewById(R.id.settings_airble_information_model_TextView);
        information_model_num_TextView = rootView.findViewById(R.id.settings_airble_information_model_num_TextView);
        information_mac_TextView = rootView.findViewById(R.id.settings_airble_information_mac_TextView);
        information_ssid_TextView = rootView.findViewById(R.id.settings_airble_information_ssid_TextView);

        information_model_TextView.setText("Airble");
        information_model_num_TextView.setText("STM-A3000");
        information_mac_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getMAC_Address());
        information_ssid_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_setting_airble_num).getSSID());

        return rootView;
    }

}