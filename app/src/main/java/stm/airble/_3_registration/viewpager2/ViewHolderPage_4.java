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

public class ViewHolderPage_4 extends Fragment {

    TextView registration_page_4_TextView_1, registration_page_4_TextView_2, registration_page_4_TextView_3;
    EditText registration_page_4_EditText;
    TextView registration_page_4_add_airble_TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_registration_page_4, container, false);

        // 아이디 맞춰주기
        registration_page_4_TextView_1 = rootView.findViewById(R.id.registration_page_4_TextView_1);
        registration_page_4_TextView_2 = rootView.findViewById(R.id.registration_page_4_TextView_2);
        registration_page_4_TextView_3 = rootView.findViewById(R.id.registration_page_4_TextView_3);
        registration_page_4_EditText = rootView.findViewById(R.id.registration_page_4_EditText);
        registration_page_4_add_airble_TextView = rootView.findViewById(R.id.registration_add_airble_TextView);

        ((RegistrationActivity)RegistrationActivity.registration_context).Page4_NickName_EditText = registration_page_4_EditText;
        ((RegistrationActivity)RegistrationActivity.registration_context).Page4_Add_Device_TextView = registration_page_4_add_airble_TextView;

        ((RegistrationActivity)RegistrationActivity.registration_context).Page4_Setting();

        return rootView;
    }
}