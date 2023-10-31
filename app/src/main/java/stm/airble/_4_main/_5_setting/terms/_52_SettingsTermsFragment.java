package stm.airble._4_main._5_setting.terms;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class _52_SettingsTermsFragment extends Fragment {

    Fragment setting_terms_Fragment;

    public DrawerLayout settings_terms_DrawerLayout;
    ImageButton back_Button;
    LinearLayout terms_service_LinearLayout, privacy_policy_LinearLayout;

    public LinearLayout settings_terms_drawer;
    ImageButton drawer_back_Button;
    TextView drawer_title_TextView;
    LinearLayout drawer_terms_service_LinearLayout, drawer_privacy_policy_LinearLayout;
    LinearLayout drawer_terms_service_inner_LinearLayout, drawer_privacy_policy_inner_LinearLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_terms, container, false);
        setting_terms_Fragment = this;

        // 아이디 맞춰주기
        {
            settings_terms_DrawerLayout = view.findViewById(R.id.settings_terms_DrawerLayout);
            back_Button = view.findViewById(R.id.back_Button);
            terms_service_LinearLayout = view.findViewById(R.id.settings_terms_service_LinearLayout);
            privacy_policy_LinearLayout = view.findViewById(R.id.settings_privacy_policy_LinearLayout);

            settings_terms_drawer = view.findViewById(R.id.settings_drawer);
            drawer_back_Button = view.findViewById(R.id.settings_drawer_back_Button);
            drawer_title_TextView = view.findViewById(R.id.settings_drawer_title_TextView);
            drawer_terms_service_LinearLayout = view.findViewById(R.id.settings_drawer_terms_service_LinearLayout);
            drawer_privacy_policy_LinearLayout = view.findViewById(R.id.settings_drawer_privacy_policy_LinearLayout);
            drawer_terms_service_inner_LinearLayout = view.findViewById(R.id.settings_drawer_terms_service_inner_LinearLayout);
            drawer_privacy_policy_inner_LinearLayout = view.findViewById(R.id.settings_drawer_privacy_policy_inner_LinearLayout);

        }

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_5_Setting();
            }
        });

        settings_terms_DrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        settings_terms_DrawerLayout.setScrimColor(Color.TRANSPARENT);
        Point size = new Point();
        ((MainActivity)MainActivity.Main_Context).getWindowManager().getDefaultDisplay().getRealSize(size);
        settings_terms_drawer.getLayoutParams().width = size.x;
        settings_terms_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        terms_service_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_title_TextView.setText("서비스 이용약관");
                settings_terms_DrawerLayout.openDrawer(settings_terms_drawer);
                drawer_terms_service_LinearLayout.setVisibility(View.VISIBLE);
                drawer_privacy_policy_LinearLayout.setVisibility(View.GONE);
            }
        });

        privacy_policy_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer_title_TextView.setText("개인정보 수집·이용 동의");
                settings_terms_DrawerLayout.openDrawer(settings_terms_drawer);
                drawer_terms_service_LinearLayout.setVisibility(View.GONE);
                drawer_privacy_policy_LinearLayout.setVisibility(View.VISIBLE);
            }
        });


        drawer_back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_terms_DrawerLayout.closeDrawer(settings_terms_drawer);
            }
        });

        Setting_Privacy_Policy();
        Setting_Service();

        return view;
    }

    private void Setting_Privacy_Policy(){
        ArrayList<String> privacy_policy_array = new ArrayList<>();
        Collections.addAll(privacy_policy_array, getResources().getStringArray(R.array.settings_terms_privacy_policy));

        for(int i = 0; i < privacy_policy_array.size(); i++){
            TextView textView = new TextView(setting_terms_Fragment.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.public_10dp), 0 , (int) getResources().getDimension(R.dimen.public_10dp) * 2);
            textView.setText(privacy_policy_array.get(i));
            textView.setTextSize(Dimension.DP,40);
            textView.setTextColor(Color.rgb(0x50, 0x8E, 0xF9));
            textView.setLayoutParams(layoutParams);
            drawer_privacy_policy_inner_LinearLayout.addView(textView);
        }
    }


    private void Setting_Service(){
        ArrayList<String> service_array = new ArrayList<>();
        Collections.addAll(service_array, getResources().getStringArray(R.array.settings_terms_service));

        for(int i = 0; i < service_array.size(); i++){
            TextView textView = new TextView(setting_terms_Fragment.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.public_10dp), 0 , (int) getResources().getDimension(R.dimen.public_10dp) * 2);
            textView.setText(service_array.get(i));
            textView.setTextSize(Dimension.DP,40);
            textView.setTextColor(Color.rgb(0x50, 0x8E, 0xF9));
            textView.setLayoutParams(layoutParams);
            drawer_terms_service_inner_LinearLayout.addView(textView);
        }
    }
}
