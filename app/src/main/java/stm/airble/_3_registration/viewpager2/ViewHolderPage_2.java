package stm.airble._3_registration.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;
import stm.airble._3_registration.RegistrationActivity;

public class ViewHolderPage_2 extends Fragment {

    TextView registration_page_2_TextView_1, registration_page_2_TextView_2, registration_page_2_TextView_3;
    RecyclerView registration_page_2_RecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_registration_page_2, container, false);

        // 아이디 맞춰주기
        registration_page_2_TextView_1 = rootView.findViewById(R.id.registration_page_2_TextView_1);
        registration_page_2_TextView_2 = rootView.findViewById(R.id.registration_page_2_TextView_2);
        registration_page_2_TextView_3 = rootView.findViewById(R.id.registration_page_2_TextView_3);
        registration_page_2_RecyclerView = rootView.findViewById(R.id.registration_page_2_RecyclerView);

        ((RegistrationActivity)RegistrationActivity.registration_context).Page2_router_RecyclerView = registration_page_2_RecyclerView;

        ((RegistrationActivity)RegistrationActivity.registration_context).Page2_Setting();

        return rootView;
    }
}