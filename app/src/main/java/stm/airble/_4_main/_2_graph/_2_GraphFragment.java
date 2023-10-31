package stm.airble._4_main._2_graph;

import static stm.airble.R.font.gothic_a1_700_bold;
import static stm.airble._4_main.MainActivity.Main_Loading;
import static stm.airble._0_public.Public_Values.*;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._2_graph.viewpager2.Graph_Marker;
import stm.airble._4_main._2_graph.viewpager2.Main_Graph_ViewPagerAdapter;
import stm.airble._0_public.GET_RequestHttpURLConnection;


public class _2_GraphFragment extends Fragment implements OnChartValueSelectedListener {

    public static Fragment graph_context;

    // 상단 기기 선택 관련
    private Button device_select_Button;
    public boolean device_list_open;
    public boolean device_list_running;
    private MutableLiveData<Long> device_select_Mutable;
    private Observer<Long> device_select_Observer;
    private Observable<Long> device_select_Observable;
    private RelativeLayout device_outside_list_LinearLayout;
    private HorizontalScrollView device_list_HorizontalScrollView;
    private LinearLayout device_inside_list_LinearLayout;
    private TextView device_name_TextView;

    // 캘린더
    SimpleDateFormat graph_date_format;
    LinearLayout graph_calendar_LinearLayout;
    RelativeLayout graph_calender_RelativeLayout;
    TextView graph_calendar_TextView;
    ImageButton graph_before_month_Button, graph_next_month_Button;
    MaterialCalendarView graph_calender;

    int month_max_day;
    Calendar min_calendar;
    CalendarSelectDecorator selectDecorator;

    // 그래프
    public static int select_matter;
    LinearLayout graph_graph_LinearLayout;
    public ViewPager2 graph_ViewPager2;
    public int graph_load_page_count;
    public int graph_day_type;
    public ArrayList<LineChart> device_sensor_ViewPager2_LineChart;
    public ArrayList<TextView> graph_title_TextView_ArrayList, graph_matter_TextView_ArrayList;

    ArrayList<Integer> co_data_Array, co2_data_Array, voc_data_Array, pm_data_Array, temp_data_Array, humi_data_Array;
    ArrayList<ArrayList<Integer>> graph_day_data, graph_month_data;

    boolean first_graph_page_start;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        graph_context = this;
        graph_load_page_count = 0;
        first_graph_page_start = true;

        // 아이디 맞춰주기
        {
            device_select_Button = view.findViewById(R.id.main_graph_device_select_Button);
            device_outside_list_LinearLayout = view.findViewById(R.id.main_graph_device_outside_list_LinearLayout);
            device_list_HorizontalScrollView = view.findViewById(R.id.main_graph_device_list_HorizontalScrollView);
            device_inside_list_LinearLayout = view.findViewById(R.id.main_graph_device_inside_list_LinearLayout);
            device_name_TextView = view.findViewById(R.id.main_graph_device_name_TextView);

            graph_date_format = new SimpleDateFormat("yyyyMMdd");
            graph_calendar_LinearLayout = view.findViewById(R.id.main_graph_calendar_LinearLayout);
            graph_calender_RelativeLayout = view.findViewById(R.id.main_graph_calendar_RelativeLayout);
            graph_calendar_TextView = view.findViewById(R.id.main_graph_calendar_TextView);
            graph_before_month_Button = view.findViewById(R.id.main_graph_before_month_Button);
            graph_next_month_Button = view.findViewById(R.id.main_graph_next_month_Button);

            graph_calender = view.findViewById(R.id.main_graph_MaterialCalendarView);

            graph_graph_LinearLayout = view.findViewById(R.id.main_graph_graph_LinearLayout);
            graph_ViewPager2 = view.findViewById(R.id.main_graph_ViewPager2);
            device_sensor_ViewPager2_LineChart = new ArrayList<>();
            graph_title_TextView_ArrayList = new ArrayList<>();
            graph_matter_TextView_ArrayList = new ArrayList<>();

            co_data_Array = new ArrayList<>();
            co2_data_Array = new ArrayList<>();
            voc_data_Array = new ArrayList<>();
            pm_data_Array = new ArrayList<>();
            temp_data_Array = new ArrayList<>();
            humi_data_Array = new ArrayList<>();

            graph_day_data = new ArrayList<>();
            graph_month_data = new ArrayList<>();

        }

        View touch_window_View = view.findViewById(R.id.main_graph_touch_window_View);

        touch_window_View.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("Sans", "탓취");
                    if(device_list_open){
                        if (!device_list_running) {
                            device_list_running = true;
                            device_list_HorizontalScrollView.setScrollX(0);
                            device_select_Observable.subscribeOn(Schedulers.io()).subscribe(device_select_Observer);
                        }
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Main_Loading.Stop_Loading();
        // 기기 선택
        {
            device_list_running = false;
            device_list_open = false;
            device_select_Mutable = new MutableLiveData<>();
            device_select_Mutable.observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Long>() {
                @Override
                public void onChanged(Long aLong) {
                    ViewGroup.LayoutParams layoutParams = device_outside_list_LinearLayout.getLayoutParams();
                    layoutParams.width = Math.toIntExact(aLong);
                    device_outside_list_LinearLayout.setLayoutParams(layoutParams);
                }
            });

            device_select_Observer = new Observer<Long>() {
                Display display;
                Point size;

                double max_width;
                double min_width;

                double unit;
                double width;
                double inner_width;

                @Override
                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                    try {
                        display = getActivity().getWindowManager().getDefaultDisplay();
                        size = new Point();
                        display.getRealSize(size);

                        max_width = (size.x - ( MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp) * 6.9 ));
                        min_width = MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp) * 2.0;

                        inner_width = (APP_Airble_Model_Array.size() * MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp) * 4.7)  + (MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp) * 3.6);
                        if(inner_width < max_width){
                            max_width = inner_width;
                        }

                        unit = (max_width - min_width) / 100.0;

                        if (device_list_open) {
                            width = max_width;
                        } else {
                            width = min_width;
                        }
                        device_outside_list_LinearLayout.setVisibility(View.VISIBLE);
                        device_select_Mutable.postValue(Math.round(width));
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onNext(@io.reactivex.rxjava3.annotations.NonNull Long aLong) {
                    try {
                        if (device_list_open) {
                            //width = width - (unit / 100.0 * (100.0 - aLong));
                            device_select_Mutable.postValue(Math.round( min_width + (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                        } else {
                            //width = width + (unit / 100.0 * (100.0 - aLong));
                            device_select_Mutable.postValue(Math.round( max_width - (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                        }
                        //device_select_Mutable.postValue(Math.round(width));
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onComplete() {
                    try {
                        if (device_list_open) {
                            device_list_open = false;
                            width = min_width;
                            device_outside_list_LinearLayout.setVisibility(View.INVISIBLE);
                        } else {
                            device_list_open = true;
                            width = max_width;
                        }
                        device_select_Mutable.postValue(Math.round(width));
                        device_list_running = false;
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                }
            };

            device_select_Observable = Observable.interval(3, TimeUnit.MILLISECONDS).take(100);

            device_select_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!device_list_running) {
                        device_list_running = true;
                        device_list_HorizontalScrollView.setScrollX(0);
                        device_select_Observable.subscribeOn(Schedulers.io()).subscribe(device_select_Observer);
                    }
                }
            });

            Device_List_Refresh();
        }

        // 캘린더
        {
            min_calendar = Calendar.getInstance();
            min_calendar.set(Calendar.YEAR, 2023);
            min_calendar.set(Calendar.MONTH, 0);
            min_calendar.set(Calendar.DAY_OF_MONTH, 1);
            graph_calender.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
            graph_calender.state().edit().
                    setMinimumDate(min_calendar).
                    commit();
            graph_calender.setWeekDayTextAppearance(R.style.graph_calendar_date_style);
            graph_calender.setAllowClickDaysOutsideCurrentMonth(true);
            graph_calender.setSelectionColor(Color.rgb(0x42,0x85,0xFA));
            graph_calender.setSelectedDate(new Date(System.currentTimeMillis()));


            graph_calendar_TextView.setText(graph_calender.getCurrentDate().getYear() + "년 " + ( graph_calender.getCurrentDate().getMonth() + 1 ) + "월" );
            selectDecorator = new CalendarSelectDecorator(this, graph_calender.getSelectedDate());
            graph_calender.addDecorators(
                    new CalendarDecorator(),
                    new CalendarCurrentDecorator(),
                    selectDecorator
            );

            graph_calender.setTopbarVisible(false);
            graph_calender.setPagingEnabled(false);


            graph_calender.setOnMonthChangedListener(new OnMonthChangedListener() {
                @Override
                public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                    graph_calendar_TextView.setText(widget.getCurrentDate().getYear() + "년 " + ( widget.getCurrentDate().getMonth() + 1 ) + "월" );
                    graph_calender.invalidateDecorators();
                    if(date.getYear() <= 2023 && date.getMonth() < 1){
                        graph_before_month_Button.setVisibility(View.INVISIBLE);
                    }else{
                        graph_before_month_Button.setVisibility(View.VISIBLE);
                    }
                }
            });

            graph_calender.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                    widget.removeDecorator(selectDecorator);
                    selectDecorator = new CalendarSelectDecorator(_2_GraphFragment.this, date);
                    widget.addDecorator(selectDecorator);
                    graph_day_type = GRAPH_TYPE_DAY;
                    Get_Data_Day_Graph(graph_date_format.format(graph_calender.getSelectedDate().getDate()));
                }
            });


            graph_calendar_TextView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {

                    //graph_year_change_LinearLayout.setVisibility(View.VISIBLE);
                    //graph_choice_year = graph_calender.getCurrentDate().getYear();
                    //graph_year_TextView.setText(graph_choice_year + "년");
                    //if (graph_choice_year == 2023) {
                    //    graph_before_Button.setVisibility(View.INVISIBLE);
                    //} else {
                    //    graph_before_Button.setVisibility(View.VISIBLE);
                    //}
                    //Refresh_Calendar_Check_Month();

                    //if(graph_day_type == GRAPH_TYPE_DAY) {
                        graph_day_type = GRAPH_TYPE_MONTH;
                        for (int i = 0; i < graph_title_TextView_ArrayList.size(); i++) {
                            graph_title_TextView_ArrayList.get(i).setText((graph_calender.getCurrentDate().getMonth() + 1) + "월 그래프");
                        }
                        Get_Data_Month_Graph(graph_date_format.format(graph_calender.getCurrentDate().getDate()));
                    //}
                }
            });

            graph_before_month_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = graph_calender.getCurrentDate().getCalendar();
                    calendar.add(Calendar.MONTH, -1);
                    graph_calender.setCurrentDate(new CalendarDay(calendar));
                }
            });

            graph_next_month_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = graph_calender.getCurrentDate().getCalendar();
                    calendar.add(Calendar.MONTH, 1);
                    graph_calender.setCurrentDate(new CalendarDay(calendar));
                }
            });

        }

        // 그래프
        {
            graph_ViewPager2.setAdapter(new Main_Graph_ViewPagerAdapter());
            graph_ViewPager2.setOffscreenPageLimit(6);
            graph_ViewPager2.setCurrentItem(0,false);

            graph_ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    for(int i = 0; i < 6; i++){
                        try{
                            if(position == i){
                                device_sensor_ViewPager2_LineChart.get(i).setDrawMarkers(true);
                            }else{
                                device_sensor_ViewPager2_LineChart.get(i).setDrawMarkers(false);
                            }
                            device_sensor_ViewPager2_LineChart.get(i).invalidate();
                        }catch (Exception e){
                            Log.d("Sans", "으악 팅김");
                        }
                    }
                }
            });

            Get_Data_Day_Graph(graph_date_format.format(graph_calender.getSelectedDate().getDate()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // 그래프
        {
            for(int i = 0; i < graph_title_TextView_ArrayList.size(); i++) {
                Log.d("Sans", graph_calender.getSelectedDate().getDay() + " " + graph_calender.getSelectedDate().getMonth());
            }
        }
    }

    private void Set_Day_Line_Chart(LineChart lineChart, ArrayList<Integer> data_array, int matter) {
        int[] colors = { ContextCompat.getColor(MainActivity.Main_Context, R.color.status_good_color), ContextCompat.getColor(MainActivity.Main_Context, R.color.status_good_color),
                ContextCompat.getColor(MainActivity.Main_Context, R.color.status_soso_color),ContextCompat.getColor(MainActivity.Main_Context, R.color.status_soso_color),
                ContextCompat.getColor(MainActivity.Main_Context, R.color.status_bad_color),ContextCompat.getColor(MainActivity.Main_Context, R.color.status_bad_color),
                ContextCompat.getColor(MainActivity.Main_Context, R.color.status_very_bad_color),ContextCompat.getColor(MainActivity.Main_Context, R.color.status_very_bad_color)};
        float[] pos = { 0.0f, 0.25f,
                0.25f, 0.5f,
                0.5f, 0.75f,
                0.75f, 1.0f };

        lineChart.setDrawMarkers(false);

        ArrayList<Entry> values = new ArrayList<>();
        int max_value = 0;
        if(matter != TEMPERATURE && matter != HUMIDITY) {
            for (int i = 0; i < data_array.size(); i++) {
                values.add(new Entry(i, data_array.get(i)));

                if (max_value < data_array.get(i)) {
                    max_value = data_array.get(i);
                }
            }
        }else{
            for (int i = 0; i < data_array.size(); i++) {
                values.add(new Entry(i, data_array.get(i) / 10f));

                if (max_value < data_array.get(i)) {
                    max_value = data_array.get(i);
                }
            }
        }

        max_value = (int)(max_value * 1.2);

        LineDataSet lineDataSet;
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            lineDataSet.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            lineDataSet = new LineDataSet(values, "");

            lineDataSet.setLineWidth(MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
            lineDataSet.setValueTextColor(Color.rgb(0, 0, 255));
            lineDataSet.setDrawValues(false); // 라인 값 보이게 하기
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);   // 라인 선 모드
            lineDataSet.setDrawFilled(false);  // 라인 아래의 공간 채우기
            //lineDataSet.setFillColor(Color.argb(0,255,255,255));
            lineDataSet.setDrawCircles(false); // 라인의 점을 표시
            //lineDataSet.enableDashedHighlightLine(10f, 10f, 0f);
            lineDataSet.setHighLightColor(Color.argb(0,0,0,0));


            lineDataSet.setDrawIcons(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet); // add the data sets

            // create a data object with the data sets
            LineData lineData = new LineData(dataSets);
            // set data
            lineChart.setData(lineData);
        }

        YAxis yAxis = lineChart.getAxisLeft();
        {
            yAxis.setDrawLabels(true);   // 좌측 숫자 보기 기능
            yAxis.setDrawGridLines(true);    // 가로 가이드라인 보기 기능
            yAxis.setAxisMinimum(0);

            float pos_value = 0.0f;

            switch (matter){
                case PM:
                    if(max_value < PM_GOOD) {
                        max_value = PM_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < PM_SOSO) {
                        max_value = PM_SOSO;
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < PM_BAD) {
                        max_value = PM_BAD;
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) PM_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < PM_MAX) {
                        max_value = PM_MAX;
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) PM_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) PM_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) PM_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) PM_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;
                case VOCs:
                    if(max_value < VOCs_GOOD) {
                        max_value = VOCs_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < VOCs_SOSO) {
                        max_value = VOCs_SOSO;
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < VOCs_BAD) {
                        max_value = VOCs_BAD;
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) VOCs_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < VOCs_MAX) {
                        max_value = VOCs_MAX;
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) VOCs_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) VOCs_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) VOCs_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) VOCs_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;
                case CO:
                    if(max_value < CO_GOOD) {
                        max_value = CO_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO_SOSO) {
                        max_value = CO_SOSO;
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO_BAD) {
                        max_value = CO_BAD;
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO_MAX) {
                        max_value = CO_MAX;
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;
                case CO2:
                    if(max_value < CO2_GOOD) {
                        max_value = CO2_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO2_SOSO) {
                        max_value = CO2_SOSO;
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO2_BAD) {
                        max_value = CO2_BAD;
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO2_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO2_MAX) {
                        max_value = CO2_MAX;
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO2_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO2_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO2_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO2_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;

                case TEMPERATURE:
                case HUMIDITY:
                    max_value = (max_value / 100) * 10 + 5;
                    break;
            }
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(max_value);
            yAxis.setLabelCount(5, true);
            yAxis.setTextColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.app_main_font_color));
            yAxis.setTypeface(ResourcesCompat.getFont(MainActivity.Main_Context, gothic_a1_700_bold));
            yAxis.setTextSize(10);
            yAxis.setAxisLineWidth(MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
            yAxis.setAxisLineColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.background_dark_blue));
            yAxis.enableGridDashedLine(15f, 10f, 0f);
            yAxis.setGridColor(Color.argb(0x4D, 0x49, 0x8E, 0xFA));
            yAxis.setGridLineWidth((float) (MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp) * 0.5));

        }

        XAxis xAxis = lineChart.getXAxis();
        {
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAvoidFirstLastClipping(true);
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(24f);
            xAxis.setLabelCount(13, true);
            xAxis.setTextColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.app_main_font_color));
            xAxis.setTypeface(ResourcesCompat.getFont(MainActivity.Main_Context,gothic_a1_700_bold));
            xAxis.setTextSize(10);
            xAxis.setAxisLineWidth(MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
            xAxis.setAxisLineColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.background_dark_blue));
            xAxis.enableGridDashedLine(15f, 10f, 0f);
            xAxis.setGridColor(Color.argb(0x4D, 0x49, 0x8E, 0xFA));
            xAxis.setGridLineWidth((float) (MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp) * 0.5));
        }

        lineChart.getDescription().setText("Line Chart Test 1");;
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.setScaleEnabled(false);    // 확대 축소 기능
        lineChart.setDoubleTapToZoomEnabled(false); // 두번 탭으로 확대 기능
        lineChart.getLegend().setEnabled(false); // 하단 값 제목 지우기
        lineChart.setHighlightPerDragEnabled(false); // 하이라이트 드래그 기능
        lineChart.setPaddingRelative((int)MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp),0,(int)MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp),0);

        //라인 둥글게 (X축)
        Paint paint = lineChart.getRendererXAxis().getPaintAxisLine();
        paint.setStrokeCap(Paint.Cap.ROUND);

        //라인 둥글게 (X축 가이드라인)
        paint = lineChart.getRendererXAxis().getPaintGrid();
        paint.setStrokeCap(Paint.Cap.ROUND);

        //라인 둥글게 (Y축)
        paint = lineChart.getRendererLeftYAxis().getPaintAxisLine();
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint = lineChart.getRenderer().getPaintRender();
        paint.setStrokeCap(Paint.Cap.ROUND);

        if(matter != TEMPERATURE && matter != HUMIDITY){
            paint.setShader(new LinearGradient(0, (device_sensor_ViewPager2_LineChart.get(0).getMeasuredHeight() - MainActivity.Main_Context.getResources().getDimensionPixelOffset(R.dimen.public_10dp)), 0, MainActivity.Main_Context.getResources().getDimensionPixelOffset(R.dimen.public_10dp), colors, pos, Shader.TileMode.REPEAT));
        }else if(matter == TEMPERATURE){
            lineDataSet.setColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.status_bad_color));
        }else{
            lineDataSet.setColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.status_good_color));
        }

        Graph_Marker marker = new Graph_Marker(MainActivity.Main_Context, R.layout.fragment_graph_graph_marker, MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
        marker.setChartView(lineChart);

        lineChart.setMarker(marker);

        lineChart.notifyDataSetChanged();
        lineChart.invalidate();

        lineChart.setOnChartValueSelectedListener(this);

        Refresh_Graph_ViewPager2(lineChart, matter);

    }



    private void Set_Month_Line_Chart(LineChart lineChart, ArrayList<Integer> data_array, int matter){

        int[] colors = {ContextCompat.getColor(MainActivity.Main_Context, R.color.status_good_color), ContextCompat.getColor(MainActivity.Main_Context, R.color.status_good_color),
                ContextCompat.getColor(MainActivity.Main_Context, R.color.status_soso_color),ContextCompat.getColor(MainActivity.Main_Context, R.color.status_soso_color),
                ContextCompat.getColor(MainActivity.Main_Context, R.color.status_bad_color),ContextCompat.getColor(MainActivity.Main_Context, R.color.status_bad_color),
                ContextCompat.getColor(MainActivity.Main_Context, R.color.status_very_bad_color),ContextCompat.getColor(MainActivity.Main_Context, R.color.status_very_bad_color)};
        float[] pos = { 0.0f, 0.25f,
                0.25f, 0.5f,
                0.5f, 0.75f,
                0.75f, 1.0f };

        lineChart.setDrawMarkers(false);

        ArrayList<Entry> values = new ArrayList<>();
        int max_value = 0;
        if(matter != TEMPERATURE && matter != HUMIDITY) {
            for (int i = 0; i < data_array.size(); i++) {
                values.add(new Entry(i, data_array.get(i)));

                if (max_value < data_array.get(i)) {
                    max_value = data_array.get(i);
                }
            }
        }else{
            for (int i = 0; i < data_array.size(); i++) {
                values.add(new Entry(i, data_array.get(i) / 10f));

                if (max_value < data_array.get(i)) {
                    max_value = data_array.get(i);
                }
            }
        }

        max_value = (int)(max_value * 1.2);

        LineDataSet lineDataSet;
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            lineDataSet.notifyDataSetChanged();
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            lineDataSet = new LineDataSet(values, "");

            lineDataSet.setLineWidth(MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
            lineDataSet.setValueTextColor(Color.rgb(0, 0, 255));
            lineDataSet.setDrawValues(false); // 라인 값 보이게 하기
            lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);   // 라인 선 모드
            lineDataSet.setDrawFilled(false);  // 라인 아래의 공간 채우기
            //lineDataSet.setFillColor(Color.argb(0,255,255,255));
            lineDataSet.setDrawCircles(false); // 라인의 점을 표시
            //lineDataSet.enableDashedHighlightLine(10f, 10f, 0f);
            lineDataSet.setHighLightColor(Color.argb(0,0,0,0));


            lineDataSet.setDrawIcons(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet); // add the data sets

            // create a data object with the data sets
            LineData lineData = new LineData(dataSets);
            // set data
            lineChart.setData(lineData);
        }

        YAxis yAxis = lineChart.getAxisLeft();
        {
            yAxis.setDrawLabels(true);   // 좌측 숫자 보기 기능
            yAxis.setDrawGridLines(true);    // 가로 가이드라인 보기 기능
            yAxis.setAxisMinimum(0);

            float pos_value = 0.0f;

            switch (matter){
                case PM:
                    if(max_value < PM_GOOD) {
                        max_value = PM_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < PM_SOSO) {
                        max_value = PM_SOSO;
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < PM_BAD) {
                        max_value = PM_BAD;
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) PM_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < PM_MAX) {
                        max_value = PM_MAX;
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) PM_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) PM_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) PM_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) PM_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) PM_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;
                case VOCs:
                    if(max_value < VOCs_GOOD) {
                        max_value = VOCs_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < VOCs_SOSO) {
                        max_value = VOCs_SOSO;
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < VOCs_BAD) {
                        max_value = VOCs_BAD;
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) VOCs_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < VOCs_MAX) {
                        max_value = VOCs_MAX;
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) VOCs_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) VOCs_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) VOCs_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) VOCs_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) VOCs_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;
                case CO:
                    if(max_value < CO_GOOD) {
                        max_value = CO_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO_SOSO) {
                        max_value = CO_SOSO;
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO_BAD) {
                        max_value = CO_BAD;
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO_MAX) {
                        max_value = CO_MAX;
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) CO_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;
                case CO2:
                    if(max_value < CO2_GOOD) {
                        max_value = CO2_GOOD;
                        pos[1] = 1.0f;
                        pos[2] = 1.0f;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO2_SOSO) {
                        max_value = CO2_SOSO;
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos[3] = 1.0f;
                        pos[4] = 1.0f;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO2_BAD) {
                        max_value = CO2_BAD;
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO2_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos[5] = 1.0f;
                        pos[6] = 1.0f;
                    }
                    else if(max_value < CO2_MAX) {
                        max_value = CO2_MAX;
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO2_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO2_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }else{
                        pos_value = (float) CO2_GOOD / (float) max_value;
                        pos[1] = pos_value;
                        pos[2] = pos_value;
                        pos_value = (float) CO2_SOSO / (float) max_value;
                        pos[3] = pos_value;
                        pos[4] = pos_value;
                        pos_value = (float) CO2_BAD / (float) max_value;
                        pos[5] = pos_value;
                        pos[6] = pos_value;
                    }
                    break;

                case TEMPERATURE:
                case HUMIDITY:
                    max_value = (max_value / 100) * 10 + 5;
                    break;
            }
            yAxis.setAxisMinimum(0f);
            yAxis.setAxisMaximum(max_value);
            yAxis.setLabelCount(5, true);
            yAxis.setTextColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.app_main_font_color));
            yAxis.setTypeface(ResourcesCompat.getFont(MainActivity.Main_Context, gothic_a1_700_bold));
            yAxis.setTextSize(10);
            yAxis.setAxisLineWidth(MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
            yAxis.setAxisLineColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.background_dark_blue));
            yAxis.enableGridDashedLine(15f, 10f, 0f);
            yAxis.setGridColor(Color.argb(0x4D, 0x49, 0x8E, 0xFA));
            yAxis.setGridLineWidth((float) (MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp) * 0.5));

        }

        XAxis xAxis = lineChart.getXAxis();
        {
            Calendar calendar = graph_calender.getCurrentDate().getCalendar();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            int max_day = calendar.get(Calendar.DAY_OF_MONTH);

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAvoidFirstLastClipping(true);
            xAxis.setAxisMinimum(1f);
            xAxis.setAxisMaximum(max_day);
            xAxis.setLabelCount(13, true);
            xAxis.setTextColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.app_main_font_color));
            xAxis.setTypeface(ResourcesCompat.getFont(MainActivity.Main_Context,gothic_a1_700_bold));
            xAxis.setTextSize(10);
            xAxis.setAxisLineWidth(MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
            xAxis.setAxisLineColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.background_dark_blue));
            xAxis.enableGridDashedLine(15f, 10f, 0f);
            xAxis.setGridColor(Color.argb(0x4D, 0x49, 0x8E, 0xFA));
            xAxis.setGridLineWidth((float) (MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp) * 0.5));
        }

        lineChart.getDescription().setText("Line Chart Test 1");;
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.setScaleEnabled(false);    // 확대 축소 기능
        lineChart.setDoubleTapToZoomEnabled(false); // 두번 탭으로 확대 기능
        lineChart.getLegend().setEnabled(false); // 하단 값 제목 지우기
        lineChart.setHighlightPerDragEnabled(false); // 하이라이트 드래그 기능
        lineChart.setPaddingRelative((int)MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp),0,(int)MainActivity.Main_Context.getResources().getDimension(R.dimen.public_10dp),0);

        //라인 둥글게 (X축)
        Paint paint = lineChart.getRendererXAxis().getPaintAxisLine();
        paint.setStrokeCap(Paint.Cap.ROUND);

        //라인 둥글게 (X축 가이드라인)
        paint = lineChart.getRendererXAxis().getPaintGrid();
        paint.setStrokeCap(Paint.Cap.ROUND);

        //라인 둥글게 (Y축)
        paint = lineChart.getRendererLeftYAxis().getPaintAxisLine();
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint = lineChart.getRenderer().getPaintRender();
        paint.setStrokeCap(Paint.Cap.ROUND);

        if(matter != TEMPERATURE && matter != HUMIDITY){
            paint.setShader(new LinearGradient(0, (device_sensor_ViewPager2_LineChart.get(0).getMeasuredHeight() - MainActivity.Main_Context.getResources().getDimensionPixelOffset(R.dimen.public_10dp)), 0, MainActivity.Main_Context.getResources().getDimensionPixelOffset(R.dimen.public_10dp), colors, pos, Shader.TileMode.REPEAT));
        }else if(matter == TEMPERATURE){
            lineDataSet.setColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.status_bad_color));
        }else{
            lineDataSet.setColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.status_good_color));
        }

        Graph_Marker marker = new Graph_Marker(MainActivity.Main_Context, R.layout.fragment_graph_graph_marker, MainActivity.Main_Context.getResources().getDimension(R.dimen.public_1dp));
        marker.setChartView(lineChart);

        lineChart.setMarker(marker);

        lineChart.notifyDataSetChanged();
        lineChart.invalidate();

        lineChart.setOnChartValueSelectedListener(this);

        Refresh_Graph_ViewPager2(lineChart, matter);

    }

    void Refresh_Graph_ViewPager2(LineChart lineChart, int matter){
        switch (graph_day_type){
            case GRAPH_TYPE_MONTH:
                for(int i = 0; i < graph_title_TextView_ArrayList.size(); i++){
                    graph_title_TextView_ArrayList.get(i).setText(( graph_calender.getCurrentDate().getMonth() + 1 ) + "월 그래프");
                }
                break;

            case GRAPH_TYPE_DAY:
                for(int i = 0; i < graph_title_TextView_ArrayList.size(); i++){
                    graph_title_TextView_ArrayList.get(i).setText(graph_calender.getSelectedDate().getDay() + "일 그래프");
                }
                break;
        }

        switch (matter) {
            case PM:
                lineChart.getDescription().setText("Fine_Dust");
                lineChart.getDescription().setEnabled(false);
                graph_matter_TextView_ArrayList.get(matter).setText("미세먼지");
                lineChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((MainActivity)MainActivity.Main_Context).Change_Page_Graph_Fragment(FINE_DUST);
                    }
                });
                break;

            case VOCs:
                lineChart.getDescription().setText("VOCs");
                lineChart.getDescription().setEnabled(false);
                graph_matter_TextView_ArrayList.get(matter).setText("휘발성 유기 화합물");
                lineChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((MainActivity)MainActivity.Main_Context).Change_Page_Graph_Fragment(VOCs);
                    }
                });
                break;

            case CO:
                lineChart.getDescription().setText("CO");
                lineChart.getDescription().setEnabled(false);
                graph_matter_TextView_ArrayList.get(matter).setText("일산화탄소");
                lineChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((MainActivity)MainActivity.Main_Context).Change_Page_Graph_Fragment(CO);
                    }
                });
                break;

            case CO2:
                lineChart.getDescription().setText("CO2");
                lineChart.getDescription().setEnabled(false);
                graph_matter_TextView_ArrayList.get(matter).setText("이산화탄소");
                lineChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((MainActivity)MainActivity.Main_Context).Change_Page_Graph_Fragment(CO2);
                    }
                });
                break;

            case TEMPERATURE:
                lineChart.getDescription().setText("Temperature");
                lineChart.getDescription().setEnabled(false);
                graph_matter_TextView_ArrayList.get(matter).setText("온도");
                lineChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((MainActivity)MainActivity.Main_Context).Change_Page_Graph_Fragment(TEMPERATURE);
                    }
                });
                break;

            case HUMIDITY:
                lineChart.getDescription().setText("Humidity");
                lineChart.getDescription().setEnabled(false);
                graph_matter_TextView_ArrayList.get(matter).setText("습도");
                lineChart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //((MainActivity)MainActivity.Main_Context).Change_Page_Graph_Fragment(HUMIDITY);
                    }
                });
                break;
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.d("Sans","e.getX = " + e.getX());
        Log.d("Sans","e.getY = " + e.getY());
        Log.d("Sans","Highlight = " + device_sensor_ViewPager2_LineChart.get(0).getHighlighter().getHighlight(e.getX(),e.getY()));
    }

    @Override
    public void onNothingSelected() {
        Log.d("Sans","낫띵");
    }

    /**
     * 캘린더 Decorator 옵션 클래스 들
     */

    /**
     * 전체 달력 색 입히기
     */
    public class CalendarDecorator implements DayViewDecorator {

        public CalendarDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new TextAppearanceSpan(MainActivity.Main_Context, R.style.graph_calendar_date_style));
            view.addSpan(new ForegroundColorSpan(Color.rgb(0x8D, 0x8D, 0x8D)));
        }
    }

    /**
     * 현제 달 달력 색 입히기
     */
    public class CalendarCurrentDecorator implements DayViewDecorator {


        public CalendarCurrentDecorator() {
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return graph_calender.getCurrentDate().getMonth() == day.getMonth();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new TextAppearanceSpan(MainActivity.Main_Context, R.style.graph_calendar_date_style));
        }
    }

    /**
     * 선택된 날 달력 색 입히기
     */
    public class CalendarSelectDecorator implements DayViewDecorator {

        private final Drawable drawable;
        private final CalendarDay calendar;

        public CalendarSelectDecorator(_2_GraphFragment context, CalendarDay calendarDay) {
            drawable = ContextCompat.getDrawable(MainActivity.Main_Context, R.drawable.calendar_selector);
            calendar = calendarDay;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return graph_calender.getSelectedDate().getMonth() == day.getMonth() && calendar.getDay() == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.rgb(255,255,255)));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // 커스텀 함수
    public void Device_List_Refresh(){
        device_inside_list_LinearLayout.removeAllViews();
        device_name_TextView.setText(APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_airble_num).getNick_Name());

        for(int i = 0; i < APP_Airble_Model_Array.size(); i++) {
            int select_device_num = i;
            Button device_Button = new Button(MainActivity.Main_Context);
            LinearLayout.LayoutParams device_Button_LayoutParms = new LinearLayout.LayoutParams(MainActivity.Main_Context.getResources().getDimensionPixelOffset(R.dimen.public_10dp) * 4, getResources().getDimensionPixelOffset(R.dimen.public_10dp) * 4);
            device_Button_LayoutParms.gravity = Gravity.CENTER_VERTICAL;
            device_Button.setBackground(ResourcesCompat.getDrawable(MainActivity.Main_Context.getResources(), R.drawable.home_device_block, null));
            if(APP_Airble_Model_Array.get(i).getNick_Name().length() > 2){
                device_Button.setText(APP_Airble_Model_Array.get(i).getNick_Name().substring(0, 2));
            }else{
                device_Button.setText(APP_Airble_Model_Array.get(i).getNick_Name());
            }
            if(i == ((MainActivity) MainActivity.Main_Context).select_airble_num){
                device_Button.setTypeface(ResourcesCompat.getFont(MainActivity.Main_Context, R.font.gothic_a1_900_black));
                device_Button.setTextSize(Dimension.DP, 30);
                device_Button.setTextColor(ContextCompat.getColor(MainActivity.Main_Context, R.color.home_device_select_font_color));
            }else{
                device_Button.setTypeface(ResourcesCompat.getFont(MainActivity.Main_Context, R.font.gothic_a1_700_bold));
                device_Button.setTextSize(Dimension.DP, 30);
                device_Button.setTextColor(Color.argb(0x70,0x42,0x85,0xFA));
            }
            device_Button_LayoutParms.setMargins(MainActivity.Main_Context.getResources().getDimensionPixelOffset(R.dimen.public_7dp), 0, 0, 0);
            device_Button.setLayoutParams(device_Button_LayoutParms);
            device_Button.setStateListAnimator(null);

            device_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!device_list_running) {
                        //device_list_running = true;
                        ((MainActivity) MainActivity.Main_Context).select_airble_num = select_device_num;
                        //device_select_Observable.subscribeOn(Schedulers.io()).subscribe(device_select_Observer);
                        // 바뀐 후 바로 갱신 1회
                        //                           Setting_Data();
                        Get_Data_Day_Graph(graph_date_format.format(graph_calender.getSelectedDate().getDate()));
                        Device_List_Refresh();
                    }

                }
            });

            device_inside_list_LinearLayout.addView(device_Button);

        }
    }

    public void Get_Data_Day_Graph(String date){

        String mac = APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_airble_num).getMAC_Address();

        try{
            String server_url = Server_Domain + "airble_test?num=29&mac=" + mac + "&date=" + date;
            URL url = new URL(server_url);
            new Get_Graph_Data_HttpConnection().execute(url);
            Main_Loading.Start_Loading();
        } catch (Exception e) {
            Main_Loading.Stop_Loading();
        }

    }

    void Get_Data_Month_Graph(String date){

        String mac = APP_Airble_Model_Array.get(((MainActivity)MainActivity.Main_Context).select_airble_num).getMAC_Address();

        try{
            String server_url = Server_Domain + "airble_test?num=30&mac=" + mac + "&date=" + date;
            URL url = new URL(server_url);
            new Get_Graph_Data_HttpConnection().execute(url);
            Main_Loading.Start_Loading();
        } catch (Exception e) {
            Main_Loading.Stop_Loading();
        }
    }

    //서버에 연결하는 코딩
    private class Get_Graph_Data_HttpConnection extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String data = "";
            if (urls.length == 0) {
                return " URL is empty";
            }
            try {
                GET_RequestHttpURLConnection connection = new GET_RequestHttpURLConnection();
                data = connection.request(urls[0]);
            } catch (Exception e) {
                data = e.getMessage();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            if (data != null) { //연결성공
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                //String code = data;
                switch (code){
                    // Airble 하루 데이터 가져오기
                    case "S29":
                    {
                        co_data_Array.clear();
                        co2_data_Array.clear();;
                        voc_data_Array.clear();
                        pm_data_Array.clear();
                        temp_data_Array.clear();
                        humi_data_Array.clear();

                        graph_day_data.clear();

                        ArrayList<Integer> co_Array = new ArrayList<>();
                        ArrayList<Integer> co2_Array = new ArrayList<>();
                        ArrayList<Integer> voc_Array = new ArrayList<>();
                        ArrayList<Integer> pm_Array = new ArrayList<>();
                        ArrayList<Integer> temp_Array = new ArrayList<>();
                        ArrayList<Integer> humi_Array = new ArrayList<>();

                        String day_data[] = data.split(",,");
                        int num = 1;
                        for(int i = 0; i < 24; i++){
                            if(num < day_data.length){
                                String matter_data[] = day_data[num].split(",");
                                int co_data = Integer.parseInt(matter_data[0]),
                                        co2_data = Integer.parseInt(matter_data[1]),
                                        voc_data = Integer.parseInt(matter_data[2]),
                                        pm_data = Integer.parseInt(matter_data[3]),
                                        temp_data = Integer.parseInt(matter_data[4]),
                                        humi_data = Integer.parseInt(matter_data[5]),
                                        hour = Integer.parseInt(matter_data[6].substring(matter_data[6].indexOf(" ") + 1, matter_data[6].indexOf(" ") + 3));


                                while(i < hour){
                                    co_Array.add(0);
                                    co2_Array.add(0);
                                    voc_Array.add(0);
                                    pm_Array.add(0);
                                    temp_Array.add(0);
                                    humi_Array.add(0);

                                    co_data_Array.add(-1);
                                    co2_data_Array.add(-1);
                                    voc_data_Array.add(-1);
                                    pm_data_Array.add(-1);
                                    temp_data_Array.add(-1);
                                    humi_data_Array.add(-1);

                                    i++;
                                }

                                co_Array.add(co_data);
                                co2_Array.add(co2_data);
                                voc_Array.add(voc_data);
                                pm_Array.add(pm_data);
                                temp_Array.add(temp_data);
                                humi_Array.add(humi_data);

                                co_data_Array.add(co_data);
                                co2_data_Array.add(co2_data);
                                voc_data_Array.add(voc_data);
                                pm_data_Array.add(pm_data);
                                temp_data_Array.add(temp_data);
                                humi_data_Array.add(humi_data);

                                num++;
                            }
                            /*
                            else{

                                co_Array.add(0);
                                co2_Array.add(0);
                                voc_Array.add(0);
                                pm_Array.add(0);
                                temp_Array.add(0);
                                humi_Array.add(0);

                                co_data_Array.add(-1);
                                co2_data_Array.add(-1);
                                voc_data_Array.add(-1);
                                pm_data_Array.add(-1);
                                temp_data_Array.add(-1);
                                humi_data_Array.add(-1);
                            }

                             */
                        }
                        graph_day_data.add(voc_Array);
                        graph_day_data.add(co2_Array);
                        graph_day_data.add(co_Array);
                        graph_day_data.add(pm_Array);
                        graph_day_data.add(temp_Array);
                        graph_day_data.add(humi_Array);

                        for(int i = 0; i < 6; i++){
                            Set_Day_Line_Chart(device_sensor_ViewPager2_LineChart.get(i), graph_day_data.get(i), i);
                            if(i == graph_ViewPager2.getCurrentItem()){
                                device_sensor_ViewPager2_LineChart.get(i).setDrawMarkers(true);
                            }
                        }

                        for(int i = 0; i < graph_title_TextView_ArrayList.size(); i++) {
                            graph_title_TextView_ArrayList.get(i).setText(graph_calender.getSelectedDate().getDay() + "일 그래프");
                            //Graph_title_TextView_ClickListener(graph_title_TextView_ArrayList.get(i), graph_calender.getSelectedDate());
                        }

                        if(first_graph_page_start){
                            graph_ViewPager2.setCurrentItem(select_matter, false);
                            first_graph_page_start = false;
                        }

                        Main_Loading.Stop_Loading();

                    }
                    break;

                    case "S30":
                    {
                        Calendar calendar = graph_calender.getSelectedDate().getCalendar();
                        calendar.add(Calendar.MONTH, 1);
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        month_max_day = calendar.get(Calendar.DAY_OF_MONTH);

                        co_data_Array.clear();
                        co2_data_Array.clear();;
                        voc_data_Array.clear();
                        pm_data_Array.clear();
                        temp_data_Array.clear();
                        humi_data_Array.clear();

                        graph_month_data.clear();

                        ArrayList<Integer> co_Array = new ArrayList<>();
                        ArrayList<Integer> co2_Array = new ArrayList<>();
                        ArrayList<Integer> voc_Array = new ArrayList<>();
                        ArrayList<Integer> pm_Array = new ArrayList<>();
                        ArrayList<Integer> temp_Array = new ArrayList<>();
                        ArrayList<Integer> humi_Array = new ArrayList<>();

                        String month_data[] = data.split(",,");
                        int num = 1;
                        for(int i = 0; i < month_max_day; i++){
                            if(num < month_data.length){
                                String matter_data[] = month_data[num].split(",");
                                int co_data = Integer.parseInt(matter_data[0]),
                                        co2_data = Integer.parseInt(matter_data[1]),
                                        voc_data = Integer.parseInt(matter_data[2]),
                                        pm_data = Integer.parseInt(matter_data[3]),
                                        temp_data = Integer.parseInt(matter_data[4]),
                                        humi_data = Integer.parseInt(matter_data[5]),
                                        day = Integer.parseInt(matter_data[6]);

                                while(i < day){
                                    co_Array.add(0);
                                    co2_Array.add(0);
                                    voc_Array.add(0);
                                    pm_Array.add(0);
                                    temp_Array.add(0);
                                    humi_Array.add(0);

                                    co_data_Array.add(-1);
                                    co2_data_Array.add(-1);
                                    voc_data_Array.add(-1);
                                    pm_data_Array.add(-1);
                                    temp_data_Array.add(-1);
                                    humi_data_Array.add(-1);

                                    i++;
                                }

                                co_Array.add(co_data);
                                co2_Array.add(co2_data);
                                voc_Array.add(voc_data);
                                pm_Array.add(pm_data);
                                temp_Array.add(temp_data);
                                humi_Array.add(humi_data);

                                co_data_Array.add(co_data);
                                co2_data_Array.add(co2_data);
                                voc_data_Array.add(voc_data);
                                pm_data_Array.add(pm_data);
                                temp_data_Array.add(temp_data);
                                humi_data_Array.add(humi_data);

                                num++;
                            }
                        }
                        graph_month_data.add(voc_Array);
                        graph_month_data.add(co2_Array);
                        graph_month_data.add(co_Array);
                        graph_month_data.add(pm_Array);
                        graph_month_data.add(temp_Array);
                        graph_month_data.add(humi_Array);

                        for(int i = 0; i < 6; i++){
                            Set_Month_Line_Chart(device_sensor_ViewPager2_LineChart.get(i), graph_month_data.get(i), i);
                        }
/*
                        for(int i = 0; i < graph_title_TextView_ArrayList.size(); i++) {
                            graph_title_TextView_ArrayList.get(i).setText(graph_calender.getSelectedDate().getDay() + "일 그래프");
                            Graph_title_TextView_ClickListener(graph_title_TextView_ArrayList.get(i), graph_calender.getSelectedDate());
                        }

 */
                        Main_Loading.Stop_Loading();
                    }
                    break;

                }

            } else {  //연결실패
                Main_Loading.Stop_Loading();
                Toast.makeText(MainActivity.Main_Context, "인터넷 연결상태를 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
