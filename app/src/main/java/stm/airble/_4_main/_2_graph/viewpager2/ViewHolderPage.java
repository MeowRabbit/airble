package stm.airble._4_main._2_graph.viewpager2;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;

import stm.airble.R;
import stm.airble._4_main._2_graph._2_GraphFragment;

public class ViewHolderPage extends RecyclerView.ViewHolder {
    TextView title_TextView, matter_TextView;
    LineChart graph_matter_LineChart;

    View view;

    ViewHolderPage(View itemView) {
        super(itemView);
        view = itemView;
        title_TextView = itemView.findViewById(R.id.main_graph_ViewPager2_title_TextView);
        matter_TextView = itemView.findViewById(R.id.main_graph_ViewPager2_matter_TextView);
        graph_matter_LineChart = itemView.findViewById(R.id.main_graph_ViewPager2_matter_LineChart);
    }

    public void onBind(int position) {

        // FINE_DUST = 0, VOCs = 1, CO = 2, CO2 = 3, Temperature = 4, Humidity = 5
        ((_2_GraphFragment)_2_GraphFragment.graph_context).device_sensor_ViewPager2_LineChart.add(position, graph_matter_LineChart);
        ((_2_GraphFragment)_2_GraphFragment.graph_context).graph_title_TextView_ArrayList.add(position, title_TextView);
        ((_2_GraphFragment)_2_GraphFragment.graph_context).graph_matter_TextView_ArrayList.add(position, matter_TextView);

    }


}