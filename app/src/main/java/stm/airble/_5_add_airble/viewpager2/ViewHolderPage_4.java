package stm.airble._5_add_airble.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._5_add_airble.AddAirbleActivity;

public class ViewHolderPage_4 extends Fragment {

    TextView add_airble_page_4_TextView_1, add_airble_page_4_TextView_2, add_airble_page_4_TextView_3;
    EditText add_airble_page_4_EditText;
    TextView add_airble_page_4_add_airble_TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_add_airble_page_4, container, false);

        // 아이디 맞춰주기
        add_airble_page_4_TextView_1 = rootView.findViewById(R.id.add_airble_page_4_TextView_1);
        add_airble_page_4_TextView_2 = rootView.findViewById(R.id.add_airble_page_4_TextView_2);
        add_airble_page_4_TextView_3 = rootView.findViewById(R.id.add_airble_page_4_TextView_3);
        add_airble_page_4_EditText = rootView.findViewById(R.id.add_airble_page_4_EditText);
        add_airble_page_4_add_airble_TextView = rootView.findViewById(R.id.add_airble_add_airble_TextView);

        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page4_NickName_EditText = add_airble_page_4_EditText;
        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page4_Add_Device_TextView = add_airble_page_4_add_airble_TextView;

        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page4_Setting();

        return rootView;
    }
}