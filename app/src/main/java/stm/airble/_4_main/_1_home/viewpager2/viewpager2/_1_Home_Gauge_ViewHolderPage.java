package stm.airble._4_main._1_home.viewpager2.viewpager2;

import static stm.airble._0_public.Public_Values.*;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._1_home.HalfCircleDonutGraph;
import stm.airble._4_main._1_home.RoundLineGraph;
import stm.airble._4_main._1_home._1_HomeFragment;
import stm.airble._4_main._5_setting.dialog.Change_Place_Dialog_Activity;

public class _1_Home_Gauge_ViewHolderPage extends RecyclerView.ViewHolder {

    // 기기 데이터 용
    public RelativeLayout gauge_RelativeLayout;
    public RelativeLayout gauge_matter_outside_RelativeLayout;
    public RelativeLayout gauge_matter_inside_RelativeLayout;
    public TextView gauge_matter_TextView;
    public TextView gauge_matter_trans_TextView;
    public TextView gauge_matter_value_TextView;
    public TextView gauge_matter_value_unit_TextView;
    public TextView gauge_matter_value_status_TextView;

    public HalfCircleDonutGraph graph;

    // 외부 데이터 용
    public RelativeLayout gauge_outside_pm_outside_RelativeLayout;
    public RelativeLayout gauge_outside_pm_inside_RelativeLayout;
    public LinearLayout gauge_outside_graph_LinearLayout, gauge_outside_pm_LinearLayout, gauge_outside_temp_LinearLayout, gauge_outside_humi_LinearLayout;
    public TextView gauge_outside_place_TextView, gauge_outside_pm_TextView, gauge_outside_temp_TextView, gauge_outside_humi_TextView;
    public TextView gauge_outside_place_value_TextView, gauge_outside_pm_value_TextView, gauge_outside_temp_value_TextView, gauge_outside_humi_value_TextView;

    public RoundLineGraph pm_graph, temp_graph, humi_graph;

    View view;

    _1_Home_Gauge_ViewHolderPage(View itemView) {
        super(itemView);
        view = itemView;

        gauge_RelativeLayout = itemView.findViewById(R.id.main_home_Viewpager2_RelativeLayout);
        gauge_matter_outside_RelativeLayout = itemView.findViewById(R.id.main_home_Viewpager2_matter_outside_RelativeLayout);
        gauge_matter_inside_RelativeLayout = itemView.findViewById(R.id.main_home_Viewpager2_matter_inside_RelativeLayout);
        gauge_matter_TextView = itemView.findViewById(R.id.main_home_Viewpager2_matter_TextView);
        gauge_matter_trans_TextView = itemView.findViewById(R.id.main_home_Viewpager2_matter_trans_TextView);
        gauge_matter_value_TextView = itemView.findViewById(R.id.main_home_Viewpager2_matter_value_TextView);
        gauge_matter_value_unit_TextView = itemView.findViewById(R.id.main_home_Viewpager2_matter_value_unit_TextView);
        gauge_matter_value_status_TextView = itemView.findViewById(R.id.main_home_Viewpager2_matter_value_status_TextView);

        graph = new HalfCircleDonutGraph( MainActivity.Main_Context, 25,180,0, 0);
        RelativeLayout.LayoutParams graph_param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        graph_param.addRule(RelativeLayout.CENTER_HORIZONTAL);
        graph_param.setMargins(0, (int)(((MainActivity.Main_Context).getResources().getDimension(R.dimen.public_8dp))),0,0);
        graph.setLayoutParams(graph_param);

        gauge_matter_outside_RelativeLayout.removeAllViews();
        gauge_matter_outside_RelativeLayout.addView(graph);
        gauge_matter_outside_RelativeLayout.addView(gauge_matter_inside_RelativeLayout);

        gauge_outside_pm_outside_RelativeLayout = itemView.findViewById(R.id.main_home_Viewpager2_outside_pm_outside_RelativeLayout);
        gauge_outside_pm_inside_RelativeLayout = itemView.findViewById(R.id.main_home_Viewpager2_outside_pm_inside_RelativeLayout);
        gauge_outside_place_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_place_TextView);
        gauge_outside_place_value_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_place_value_TextView);
        gauge_outside_graph_LinearLayout = itemView.findViewById(R.id.main_home_ViewPager2_outside_graph_LinearLayout);
        gauge_outside_pm_LinearLayout = itemView.findViewById(R.id.main_home_ViewPager2_outside_pm_LinearLayout);
        gauge_outside_pm_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_pm_TextView);
        gauge_outside_pm_value_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_pm_value_TextView);
        gauge_outside_temp_LinearLayout = itemView.findViewById(R.id.main_home_ViewPager2_outside_temp_LinearLayout);
        gauge_outside_temp_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_temp_TextView);
        gauge_outside_temp_value_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_temp_value_TextView);
        gauge_outside_humi_LinearLayout = itemView.findViewById(R.id.main_home_ViewPager2_outside_humi_LinearLayout);
        gauge_outside_humi_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_humi_TextView);
        gauge_outside_humi_value_TextView = itemView.findViewById(R.id.main_home_ViewPager2_outside_humi_value_TextView);

        LinearLayout.LayoutParams pm_param, temp_param, humi_param;
        pm_graph = new RoundLineGraph(MainActivity.Main_Context, 16, PM_MAX,0);
        pm_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        pm_param.setMargins(0,0,0, (int)(((MainActivity.Main_Context).getResources().getDimension(R.dimen.public_2dp))));
        pm_graph.setLayoutParams(pm_param);
        temp_graph = new RoundLineGraph(MainActivity.Main_Context, 16,1000,0);
        temp_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        temp_param.setMargins(0,0,0, (int)(((MainActivity.Main_Context).getResources().getDimension(R.dimen.public_2dp))));
        temp_graph.setLayoutParams(temp_param);
        humi_graph = new RoundLineGraph(MainActivity.Main_Context, 16,1000,0);
        humi_param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        humi_param.setMargins(0,0,0, (int)(((MainActivity.Main_Context).getResources().getDimension(R.dimen.public_2dp))));
        humi_graph.setLayoutParams(humi_param);

        gauge_outside_pm_LinearLayout.addView(pm_graph);
        gauge_outside_temp_LinearLayout.addView(temp_graph);
        gauge_outside_humi_LinearLayout.addView(humi_graph);

    }

    public void onBind(int device_position, int matter) {

        ((_1_HomeFragment)_1_HomeFragment.home_context).device_ViewPager2_ViewHolder.get(device_position).device_sensor_ViewPager2_ViewHolder.add(matter, this);

        if(matter == 0 || matter == 6){
            gauge_RelativeLayout.removeAllViews();
        }else{
            switch (matter - 2){
                case VOCs:
                    Setting_TextView_Value("VOCs", "휘발성유기화합물", "㎎/㎥", VOCs_MAX);
                    break;

                case CO2:
                    Setting_TextView_Value("CO₂", "이산화탄소", "ppm", CO2_MAX);
                    break;

                case CO:
                    Setting_TextView_Value("CO", "일산화탄소", "ppm", CO_MAX);
                    break;

                case PM:
                    Setting_TextView_Value("PM2.5", "초미세먼지", "㎍/㎥", PM_MAX);
                    break;

                case -1:
                    gauge_outside_pm_outside_RelativeLayout.setVisibility(View.VISIBLE);
                    break;

            }
        }

        gauge_outside_place_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Place();
            }
        });

        gauge_outside_place_value_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change_Place();
            }
        });

    }

    public void Setting_TextView_Value(String matter_main, String matter_sub, String value_unit, int Graph_Max_value){
        gauge_outside_pm_outside_RelativeLayout.setVisibility(View.GONE);
        gauge_matter_TextView.setText(matter_main);
        gauge_matter_trans_TextView.setText(matter_sub);
        gauge_matter_value_TextView.setText("0");
        gauge_matter_value_unit_TextView.setText(value_unit);
        gauge_matter_value_status_TextView.setText("좋음");
        graph.setMax_value(Graph_Max_value);
    }

    public void Change_Place(){
        //((MainActivity)MainActivity.Main_Context).select_setting_airble_num = -1;
        //Fragment_Page = -1;
        Intent intent = new Intent(MainActivity.Main_Context, Change_Place_Dialog_Activity.class);
        ((_1_HomeFragment)_1_HomeFragment.home_context).device_place_ActivityResultLauncher.launch(intent);

    }

}