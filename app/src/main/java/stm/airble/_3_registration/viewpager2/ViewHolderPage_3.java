package stm.airble._3_registration.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._3_registration.RegistrationActivity;

public class ViewHolderPage_3 extends Fragment {

    TextView registration_page_3_TextView_1, registration_page_3_TextView_2, registration_page_3_TextView_3;
    TextView registration_page_3_SSID_TextView;
    EditText registration_page_3_PW_EditText;
    TextView registration_page_3_connect_TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_registration_page_3, container, false);

        // 아이디 맞춰주기
        registration_page_3_TextView_1 = rootView.findViewById(R.id.registration_page_3_TextView_1);
        registration_page_3_TextView_2 = rootView.findViewById(R.id.registration_page_3_TextView_2);
        registration_page_3_TextView_3 = rootView.findViewById(R.id.registration_page_3_TextView_3);
        registration_page_3_SSID_TextView = rootView.findViewById(R.id.registration_page_3_SSID_TextView);
        registration_page_3_PW_EditText = rootView.findViewById(R.id.registration_page_3_PW_EditText);
        registration_page_3_connect_TextView = rootView.findViewById(R.id.registration_page_3_connect_TextView);

        ((RegistrationActivity)RegistrationActivity.registration_context).Page3_SSID_TextView = registration_page_3_SSID_TextView;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page3_PW_EditText = registration_page_3_PW_EditText;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page3_connect_TextView = registration_page_3_connect_TextView;

        ((RegistrationActivity)RegistrationActivity.registration_context).Page3_Setting();

        return rootView;
    }
}