package stm.airble._4_main._5_setting.viewpager2;

import static stm.airble._0_public.Public_Values.*;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class ViewHolderPage extends RecyclerView.ViewHolder {

    View view;

    RelativeLayout settings_ViewPager2_RelativeLayout;
    LinearLayout settings_ViewPager2_device_outside_LinearLayout, settings_ViewPager2_device_inside_LinearLayout;
    TextView settings_ViewPager2_device_TextView;
    View settings_device_rename_View, settings_device_main_select_View;

    ViewHolderPage(View itemView) {
        super(itemView);
        view = itemView;

        // 아이디 맞춰주기
        settings_ViewPager2_RelativeLayout = itemView.findViewById(R.id.settings_ViewPager2_RelativeLayout);
        settings_ViewPager2_device_outside_LinearLayout = itemView.findViewById(R.id.settings_ViewPager2_device_outside_LinearLayout);
        settings_ViewPager2_device_inside_LinearLayout = itemView.findViewById(R.id.settings_ViewPager2_device_inside_LinearLayout);
        settings_ViewPager2_device_TextView = itemView.findViewById(R.id.settings_ViewPager2_device_TextView);
        settings_device_rename_View = itemView.findViewById(R.id.settings_device_rename_View);
        settings_device_main_select_View = itemView.findViewById(R.id.settings_device_main_select_View);

        settings_device_rename_View.setVisibility(View.GONE);
        settings_device_main_select_View.setVisibility(View.GONE);

    }

    public void onBind(int position) {
        settings_ViewPager2_device_TextView.setText(APP_Airble_Model_Array.get(position).getNick_Name());

        settings_ViewPager2_device_outside_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).select_setting_airble_num = position;
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_51_Setting();
            }
        });

    }


}