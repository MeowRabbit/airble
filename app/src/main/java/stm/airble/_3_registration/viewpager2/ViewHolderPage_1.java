package stm.airble._3_registration.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._3_registration.RegistrationActivity;

public class ViewHolderPage_1 extends Fragment {

    TextView registration_page_1_TextView_1, registration_page_1_TextView_2, registration_page_1_TextView_3, registration_page_1_TextView_4, registration_find_wifi_TextView;
    TextView shared_TextView;
    LinearLayout registration_next_LinearLayout;
    TextView registration_next_TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_registration_page_1, container, false);

        // 아이디 맞춰주기
        registration_page_1_TextView_1 = rootView.findViewById(R.id.registration_page_1_TextView_1);
        registration_page_1_TextView_2 = rootView.findViewById(R.id.registration_page_1_TextView_2);
        registration_page_1_TextView_3 = rootView.findViewById(R.id.registration_page_1_TextView_3);
        registration_page_1_TextView_4 = rootView.findViewById(R.id.registration_page_1_TextView_4);
        registration_find_wifi_TextView = rootView.findViewById(R.id.registration_find_wifi_TextView);
        registration_next_LinearLayout = rootView.findViewById(R.id.registration_next_LinearLayout);
        registration_next_TextView = rootView.findViewById(R.id.registration_next_TextView);
        shared_TextView = rootView.findViewById(R.id.registration_shared_TextView);

        ((RegistrationActivity)RegistrationActivity.registration_context).Page1_connect_TextView = registration_page_1_TextView_4;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page1_find_wifi_TextView = registration_find_wifi_TextView;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page1_shared_TextView = shared_TextView;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page1_next_btn = registration_next_LinearLayout;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page1_next_TextView = registration_next_TextView;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page1_Setting();

        return rootView;
    }
}