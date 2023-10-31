package stm.airble._4_main._2_graph.viewpager2;

import static stm.airble._0_public.Public_Values.*;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import stm.airble.R;
import stm.airble._4_main._2_graph._2_GraphFragment;

public class Graph_Marker extends MarkerView {

    private double dp = 0.0;
    private TextView marker_status_TextView, marker_value_TextView, marker_unit_TextView;
    private View marker_point_View;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public Graph_Marker(Context context, int layoutResource, double dp) {
        super(context, layoutResource);

        marker_status_TextView = findViewById(R.id.main_graph_graph_marker_status_TextView);
        marker_value_TextView = findViewById(R.id.main_graph_graph_marker_value_TextView);
        marker_unit_TextView = findViewById(R.id.main_graph_graph_marker_unit_TextView);
        marker_point_View = findViewById(R.id.main_graph_graph_marker_point_View);
        this.dp = dp;

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        try{
            if (e instanceof CandleEntry) {
                CandleEntry ce = (CandleEntry) e;
                marker_value_TextView.setText(Utils.formatNumber(ce.getHigh(), 0, false) + "\n");
            } else {
                marker_value_TextView.setText(Utils.formatNumber(e.getY(), 0, false) + "\n");
            }
            Log.d("Sans", "marker_value_TextView.getText().toString() = '" + marker_value_TextView.getText().toString().trim() + "'");
            int maker_value = Integer.parseInt(marker_value_TextView.getText().toString().trim());

            switch (((_2_GraphFragment)_2_GraphFragment.graph_context).graph_ViewPager2.getCurrentItem()){
                case PM:
                    marker_unit_TextView.setText("㎍/㎥");
                    if(maker_value < PM_GOOD) {
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_good_block));
                        marker_status_TextView.setText("좋음");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_good_point));
                    }
                    else if(maker_value < PM_SOSO){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_soso_block));
                        marker_status_TextView.setText("보통");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_soso_point));
                    }
                    else if(maker_value < PM_BAD){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_bad_block));
                        marker_status_TextView.setText("나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_bad_point));
                    }
                    else{
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_very_bad_block));
                        marker_status_TextView.setText("매우나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_very_bad_point));
                    }
                    break;
                case VOCs:
                    marker_unit_TextView.setText("㎎/㎥");
                    if(maker_value < VOCs_GOOD) {
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_good_block));
                        marker_status_TextView.setText("좋음");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_good_point));
                    }
                    else if(maker_value < VOCs_SOSO){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_soso_block));
                        marker_status_TextView.setText("보통");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_soso_point));
                    }
                    else if(maker_value < VOCs_BAD){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_bad_block));
                        marker_status_TextView.setText("나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_bad_point));
                    }
                    else{
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_very_bad_block));
                        marker_status_TextView.setText("매우나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_very_bad_point));
                    }
                    break;
                case CO:
                    marker_unit_TextView.setText("ppm");
                    if(maker_value < CO_GOOD) {
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_good_block));
                        marker_status_TextView.setText("좋음");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_good_point));
                    }
                    else if(maker_value < CO_SOSO){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_soso_block));
                        marker_status_TextView.setText("보통");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_soso_point));
                    }
                    else if(maker_value < CO_BAD){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_bad_block));
                        marker_status_TextView.setText("나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_bad_point));
                    }
                    else{
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_very_bad_block));
                        marker_status_TextView.setText("매우나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_very_bad_point));
                    }
                    break;
                case CO2:
                    marker_unit_TextView.setText("ppm");
                    if(maker_value < CO2_GOOD) {
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_good_block));
                        marker_status_TextView.setText("좋음");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_good_point));
                    }
                    else if(maker_value < CO2_SOSO){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_soso_block));
                        marker_status_TextView.setText("보통");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_soso_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_soso_point));
                    }
                    else if(maker_value < CO2_BAD){
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_bad_block));
                        marker_status_TextView.setText("나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_bad_point));
                    }
                    else{
                        marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_very_bad_block));
                        marker_status_TextView.setText("매우나쁨");
                        marker_value_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_very_bad_color));
                        marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_very_bad_point));
                    }
                    break;

                case TEMPERATURE:
                    marker_unit_TextView.setText("℃");
                    marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_bad_block));
                    marker_status_TextView.setText("온도");
                    marker_value_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                    marker_value_TextView.setText(e.getY() + "\n");
                    marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_bad_color));
                    marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_bad_point));
                    break;

                case HUMIDITY:
                    marker_unit_TextView.setText("%");
                    marker_status_TextView.setBackground(getResources().getDrawable(R.drawable.status_good_block));
                    marker_status_TextView.setText("습도");
                    marker_value_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                    marker_value_TextView.setText(e.getY() + "\n");
                    marker_unit_TextView.setTextColor(getResources().getColor(R.color.status_good_color));
                    marker_point_View.setBackground(getResources().getDrawable(R.drawable.marker_status_good_point));
                    break;
            }
        }catch (Exception ex){

        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), Math.round(-getHeight() + (dp * 3)));
    }
}