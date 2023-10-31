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

public class ViewHolderPage_3 extends Fragment {

    TextView add_airble_page_3_TextView_1, add_airble_page_3_TextView_2, add_airble_page_3_TextView_3;
    TextView add_airble_page_3_SSID_TextView;
    EditText add_airble_page_3_PW_EditText;
    TextView add_airble_page_3_connect_TextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_add_airble_page_3, container, false);

        // 아이디 맞춰주기
        add_airble_page_3_TextView_1 = rootView.findViewById(R.id.add_airble_page_3_TextView_1);
        add_airble_page_3_TextView_2 = rootView.findViewById(R.id.add_airble_page_3_TextView_2);
        add_airble_page_3_TextView_3 = rootView.findViewById(R.id.add_airble_page_3_TextView_3);
        add_airble_page_3_SSID_TextView = rootView.findViewById(R.id.add_airble_page_3_SSID_TextView);
        add_airble_page_3_PW_EditText = rootView.findViewById(R.id.add_airble_page_3_PW_EditText);
        add_airble_page_3_connect_TextView = rootView.findViewById(R.id.add_airble_page_3_connect_TextView);

        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page3_SSID_TextView = add_airble_page_3_SSID_TextView;
        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page3_PW_EditText = add_airble_page_3_PW_EditText;
        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page3_connect_TextView = add_airble_page_3_connect_TextView;

        ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Page3_Setting();

        return rootView;
    }
}
/*
public class ViewHolderPage_3 extends Fragment {

    TextView add_airble_page_3_TextView_1, add_airble_page_3_TextView_2, add_airble_page_3_TextView_3;
    EditText add_airble_page_3_EditText;
    TextView add_airble_add_airble_TextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_add_airble_page_3, container, false);

        // 아이디 맞춰주기
        add_airble_page_3_TextView_1 = rootView.findViewById(R.id.add_airble_page_3_TextView_1);
        add_airble_page_3_TextView_2 = rootView.findViewById(R.id.add_airble_page_3_TextView_2);
        add_airble_page_3_TextView_3 = rootView.findViewById(R.id.add_airble_page_3_TextView_3);
        add_airble_page_3_EditText = rootView.findViewById(R.id.add_airble_page_3_EditText);
        add_airble_add_airble_TextView = rootView.findViewById(R.id.add_airble_add_airble_TextView);

        add_airble_page_3_EditText.setHint(AddAirbleActivity.add_SSID);
        add_airble_add_airble_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_airble_page_3_EditText.getText().toString().trim().split("\"")[1];
                if(name.length() > 10 || name.contains(",")){
                    Start_Popup(AddAirbleActivity.add_airble_context, "사용할 수 없는 이름입니다.", "1~10자리/특수문자 ',' 사용 불가능");
                }else {
                    ((AddAirbleActivity) AddAirbleActivity.add_airble_context).Start_Main(name);
                }

            }
        });


        return rootView;
    }
}
*/