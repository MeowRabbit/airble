package stm.airble._4_main._1_home.viewpager2;

import static stm.airble._0_public.Public_Values.CO;
import static stm.airble._0_public.Public_Values.CO2;
import static stm.airble._0_public.Public_Values.PM;
import static stm.airble._0_public.Public_Values.VOCs;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._1_home._1_HomeFragment;
import stm.airble._4_main._1_home.viewpager2.viewpager2.Main_Home_Gauge_ViewPagerAdapter;
import stm.airble._4_main._1_home.viewpager2.viewpager2._1_Home_Gauge_ViewHolderPage;

public class _1_Home_ViewHolderPage extends RecyclerView.ViewHolder {

    public boolean connect_on = false;

    public ViewPager2 device_sensor_ViewPager2;
    public ArrayList<_1_Home_Gauge_ViewHolderPage> device_sensor_ViewPager2_ViewHolder;
    public ImageButton left_arrow_ImageButton,right_arrow_ImageButton;

    private RelativeLayout matter_co_block_RelativeLayout, matter_co2_block_RelativeLayout, matter_vocs_block_RelativeLayout,matter_pm_block_RelativeLayout;
    public TextView pm_value_TextView, vocs_value_TextView, co_value_TextView, co2_value_TextView, temp_value_TextView, humi_value_TextView;
    public TextView pm_status_TextView, vocs_status_TextView, co_status_TextView, co2_status_TextView;

    _1_Home_ViewHolderPage(View itemView) {
        super(itemView);

        // 아이디 맞춰주기
        device_sensor_ViewPager2 = itemView.findViewById(R.id.main_home_ViewPager2);
        left_arrow_ImageButton = itemView.findViewById(R.id.main_home_ViewPager2_left_arrow_ImageButton);
        right_arrow_ImageButton = itemView.findViewById(R.id.main_home_ViewPager2_right_arrow_ImageButton);

        matter_co_block_RelativeLayout = itemView.findViewById(R.id.main_home_matter_co_block_RelativeLayout);
        matter_co2_block_RelativeLayout = itemView.findViewById(R.id.main_home_matter_co2_block_RelativeLayout);
        matter_vocs_block_RelativeLayout = itemView.findViewById(R.id.main_home_matter_vocs_block_RelativeLayout);
        matter_pm_block_RelativeLayout = itemView.findViewById(R.id.main_home_matter_pm_block_RelativeLayout);
        pm_value_TextView = itemView.findViewById(R.id.main_home_matter_pm_value_TextView);
        vocs_value_TextView = itemView.findViewById(R.id.main_home_matter_vocs_value_TextView);
        co_value_TextView = itemView.findViewById(R.id.main_home_matter_co_value_TextView);
        co2_value_TextView = itemView.findViewById(R.id.main_home_matter_co2_value_TextView);
        temp_value_TextView = itemView.findViewById(R.id.main_home_matter_temp_value_TextView);
        humi_value_TextView = itemView.findViewById(R.id.main_home_matter_humi_value_TextView);
        pm_status_TextView = itemView.findViewById(R.id.main_home_matter_pm_status_TextView);
        vocs_status_TextView = itemView.findViewById(R.id.main_home_matter_vocs_status_TextView);
        co_status_TextView = itemView.findViewById(R.id.main_home_matter_co_status_TextView);
        co2_status_TextView = itemView.findViewById(R.id.main_home_matter_co2_status_TextView);

        device_sensor_ViewPager2_ViewHolder = new ArrayList<>();
    }

    public void onBind(int position) {
        ViewPager_Setting(position);
        ((_1_HomeFragment)_1_HomeFragment.home_context).device_ViewPager2_ViewHolder.add(position,this);

        // 초기 값 비우기
        vocs_value_TextView.setText("");
        co2_value_TextView.setText("");
        co_value_TextView.setText("");
        pm_value_TextView.setText("");
        temp_value_TextView.setText("");
        humi_value_TextView.setText("");

        vocs_status_TextView.setText("");
        co2_status_TextView.setText("");
        co_status_TextView.setText("");
        pm_status_TextView.setText("");

        // 게이지 ViewPager
        {
            left_arrow_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    device_sensor_ViewPager2.setCurrentItem(device_sensor_ViewPager2.getCurrentItem() - 1);
                }
            });

            right_arrow_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    device_sensor_ViewPager2.setCurrentItem(device_sensor_ViewPager2.getCurrentItem() + 1);
                }
            });

        }
        // 하단 터치 관련
        {

            matter_vocs_block_RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_UP:
                        {
                            ((MainActivity)MainActivity.Main_Context).Change_Fragment_2_Graph(VOCs);
                        }
                        break;
                    }
                    return true;
                }
            });
            matter_co2_block_RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){

                        case MotionEvent.ACTION_UP:
                        {
                            Log.d("Sans", "머지");
                            ((MainActivity)MainActivity.Main_Context).Change_Fragment_2_Graph(CO2);
                        }
                        break;
                    }
                    return true;
                }
            });
            matter_co_block_RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_UP:
                        {
                            ((MainActivity)MainActivity.Main_Context).Change_Fragment_2_Graph(CO);
                        }
                        break;
                    }
                    return true;
                }
            });


            matter_pm_block_RelativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){

                        case MotionEvent.ACTION_UP:
                        {
                            ((MainActivity)MainActivity.Main_Context).Change_Fragment_2_Graph(PM);
                        }
                        break;
                    }
                    return true;
                }
            });


        }

    }

    public void ViewPager_Setting(int device_position){
        device_sensor_ViewPager2.setAdapter(new Main_Home_Gauge_ViewPagerAdapter(device_position));
        device_sensor_ViewPager2.setOffscreenPageLimit(7);
        device_sensor_ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if(((_1_HomeFragment)_1_HomeFragment.home_context).home_start){
                    left_arrow_ImageButton.setVisibility(View.GONE);
                    right_arrow_ImageButton.setVisibility(View.GONE);
                    device_sensor_ViewPager2.setCurrentItem(0, false);
                }else{
                    if(position <= 1){
                        left_arrow_ImageButton.setVisibility(View.GONE);
                    }else if(position >= 5){
                        right_arrow_ImageButton.setVisibility(View.GONE);
                    }else{
                        left_arrow_ImageButton.setVisibility(View.VISIBLE);
                        right_arrow_ImageButton.setVisibility(View.VISIBLE);
                    }

                    if (position <= 0) {
                        device_sensor_ViewPager2.setCurrentItem(1, true);
                    } else if (position >= 6) {
                        device_sensor_ViewPager2.setCurrentItem(5, true);
                    }
                }

            }
        });
    }

}