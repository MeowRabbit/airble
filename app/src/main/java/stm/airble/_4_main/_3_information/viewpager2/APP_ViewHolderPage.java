package stm.airble._4_main._3_information.viewpager2;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class APP_ViewHolderPage extends Fragment {

    private MutableLiveData<Long> information_app_MutableLiveData;
    private Observable<Long> information_app_Observable;
    private Observer information_app_Observer;
    boolean information_app_list_running, information_app_list_open;
    Point window_size;
    int select_num;

    ArrayList<ConstraintLayout> information_app_Layout_Array;
    ArrayList<ScrollView> information_app_ScrollView_Array;
    ArrayList<View> information_app_Touch_Array;
    ArrayList<Entry> information_app_real_size_Array;

    ConstraintLayout information_inner_ConstraintLayout;

    ConstraintLayout information_app_version_out_ConstraintLayout, information_app_version_in_ConstraintLayout;
    TextView information_app_version_title_TextView;
    ScrollView information_app_version_ScrollView;
    TextView information_app_version_TextView_1, information_app_version_TextView_2;
    View information_app_version_Touch_View;

    ConstraintLayout test_1_out_ConstraintLayout, test_1_in_ConstraintLayout;
    TextView test_1_title_TextView;
    ScrollView test_1_ScrollView;
    TextView test_1_TextView_1, test_1_TextView_2;
    View test_1_Touch_View;

    ConstraintLayout test_2_out_ConstraintLayout, test_2_in_ConstraintLayout;
    TextView test_2_title_TextView;
    ScrollView test_2_ScrollView;
    TextView test_2_TextView_1, test_2_TextView_2;
    View test_2_Touch_View;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_information_inner_page_app, container, false);

        information_app_Layout_Array = new ArrayList<>();
        information_app_ScrollView_Array = new ArrayList<>();
        information_app_Touch_Array = new ArrayList<>();
        information_app_real_size_Array = new ArrayList<>();

        window_size = new Point();

        // 아이디 맞춰주기
        information_inner_ConstraintLayout = rootView.findViewById(R.id.information_inner_ConstraintLayout);

        information_app_version_out_ConstraintLayout = rootView.findViewById(R.id.information_app_version_out_ConstraintLayout);
        information_app_version_in_ConstraintLayout = rootView.findViewById(R.id.information_app_version_in_ConstraintLayout);
        information_app_version_title_TextView = rootView.findViewById(R.id.information_app_version_title_TextView);
        information_app_version_ScrollView = rootView.findViewById(R.id.information_app_version_ScrollView);
        information_app_version_TextView_1 = rootView.findViewById(R.id.information_app_version_TextView_1);
        information_app_version_TextView_2 = rootView.findViewById(R.id.information_app_version_TextView_2);
        information_app_version_Touch_View = rootView.findViewById(R.id.information_app_version_Touch_View);

        information_app_Layout_Array.add(information_app_version_out_ConstraintLayout);
        information_app_ScrollView_Array.add(information_app_version_ScrollView);
        information_app_Touch_Array.add(information_app_version_Touch_View);

        test_1_out_ConstraintLayout = rootView.findViewById(R.id.test_1_out_ConstraintLayout);
        test_1_in_ConstraintLayout = rootView.findViewById(R.id.test_1_in_ConstraintLayout);
        test_1_title_TextView = rootView.findViewById(R.id.test_1_title_TextView);
        test_1_ScrollView = rootView.findViewById(R.id.test_1_ScrollView);
        test_1_TextView_1 = rootView.findViewById(R.id.test_1_TextView_1);
        test_1_TextView_2 = rootView.findViewById(R.id.test_1_TextView_2);
        test_1_Touch_View = rootView.findViewById(R.id.test_1_Touch_View);

        information_app_Layout_Array.add(test_1_out_ConstraintLayout);
        information_app_ScrollView_Array.add(test_1_ScrollView);
        information_app_Touch_Array.add(test_1_Touch_View);

        test_2_out_ConstraintLayout = rootView.findViewById(R.id.test_2_out_ConstraintLayout);
        test_2_in_ConstraintLayout = rootView.findViewById(R.id.test_2_in_ConstraintLayout);
        test_2_title_TextView = rootView.findViewById(R.id.test_2_title_TextView);
        test_2_ScrollView = rootView.findViewById(R.id.test_2_ScrollView);
        test_2_TextView_1 = rootView.findViewById(R.id.test_2_TextView_1);
        test_2_TextView_2 = rootView.findViewById(R.id.test_2_TextView_2);
        test_2_Touch_View = rootView.findViewById(R.id.test_2_Touch_View);

        information_app_Layout_Array.add(test_2_out_ConstraintLayout);
        information_app_ScrollView_Array.add(test_2_ScrollView);
        information_app_Touch_Array.add(test_2_Touch_View);


/*
        information_app_version_Touch_View.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:         // 버튼을 다시 땜
                        v.setAlpha(1);
                        Log.d("Sans", "땜");
                        information_app_version_in_ConstraintLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.information_item_block_1));
                        break;
                    case MotionEvent.ACTION_MOVE:       // 버튼을 누르고 움직임
                        if(!v.isPressed()) {            // 버튼을 벗어날 경우
                            v.setAlpha(1);
                            Log.d("Sans", "벗어남");
                            information_app_version_in_ConstraintLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.information_item_block_1));
                        }else{
                            Log.d("Sans", "움직임");
                            v.setAlpha(0.65f);
                            information_app_version_in_ConstraintLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.information_item_block_2));
                        }
                        break;

                    case MotionEvent.ACTION_DOWN:       // 버튼을 누름
                        v.setAlpha(0.65f);
                        information_app_version_in_ConstraintLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.information_item_block_2));
                        Log.d("Sans", "ㄴ름");
                        break;

                }
                return false;
            }
        });

 */

        information_app_version_Touch_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Point point = new Point();
                ((MainActivity)MainActivity.Main_Context).getWindowManager().getDefaultDisplay().getRealSize(point);
                information_app_version_ScrollView.getLayoutParams().width = point.x - Math.round(getResources().getDimension(R.dimen.public_10dp) * 6.6f);
                information_app_version_ScrollView.invalidate();
                information_app_version_ScrollView.requestLayout();
            }
        });


        // 리스트 On/off
        {
            information_app_MutableLiveData = new MutableLiveData<>();
            information_app_MutableLiveData.observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<Long>() {
                @Override
                public void onChanged(Long aLong) {
                    ((MainActivity)MainActivity.Main_Context).getWindowManager().getDefaultDisplay().getRealSize(window_size);

                    int sum_width = 0;
                    ConstraintLayout first_layout = null;
                    ConstraintLayout.LayoutParams layoutParams = null;
                    for(int i = 0; i < information_app_Layout_Array.size(); i++){
                        if(first_layout == null){
                            first_layout = information_app_Layout_Array.get(i);
                            sum_width = (int)(getResources().getDimension(R.dimen.public_10dp) * 1.8f);
                        }
                        //information_app_real_size_Array.add(new Entry(information_app_Layout_Array.get(i).getMeasuredWidth(),information_app_Layout_Array.get(i).getMeasuredHeight()));
                        Log.d("Sans", "information_app_MutableLiveData x = " + information_app_Layout_Array.get(i).getMeasuredWidth() + " y = " + information_app_Layout_Array.get(i).getMeasuredHeight());
                        sum_width += information_app_Layout_Array.get(i).getMeasuredWidth() + (int)getResources().getDimension(R.dimen.public_4dp);
                        if(sum_width > window_size.x){
                            Log.d("Sans", "sum_width = " + sum_width + ", window_size.x = " + window_size.x);
                            sum_width = (int)(getResources().getDimension(R.dimen.public_10dp) * 1.8f);
                            layoutParams = (ConstraintLayout.LayoutParams) information_app_Layout_Array.get(i).getLayoutParams();
                            layoutParams.topToBottom = first_layout.getId();
                            layoutParams.leftToRight = R.id.standard_view;
                            layoutParams.leftMargin = (int)getResources().getDimension(R.dimen.public_9dp);
                            information_app_Layout_Array.get(i).setLayoutParams(layoutParams);

                            for(int j = i+1; j < information_app_Layout_Array.size(); j++){
                                Log.d("Sans", j + "");
                                layoutParams = (ConstraintLayout.LayoutParams) information_app_Layout_Array.get(j).getLayoutParams();
                                layoutParams.topToBottom = first_layout.getId();
                                information_app_Layout_Array.get(j).setLayoutParams(layoutParams);
                            }

                            first_layout = information_app_Layout_Array.get(i);
                        }
                    }
                    /*
                    for(int i = 0; i < information_app_Layout_Array.size(); i++){
                        if(select_num == i){    // 선택한 정보

                            if (information_app_list_open) {
                                //width = width - (unit / 100.0 * (100.0 - aLong));
                                information_app_MutableLiveData.postValue(Math.round( min_width + (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                            } else {
                                //width = width + (unit / 100.0 * (100.0 - aLong));
                                information_app_MutableLiveData.postValue(Math.round( max_width - (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                            }
                        }else{  // 나머지 정보
                            if (information_app_list_open) {    // 열람중일경우 다시 늘리기
                                //width = width - (unit / 100.0 * (100.0 - aLong));
                                information_app_MutableLiveData.postValue(Math.round( min_width + (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                            } else {    //열람중이 아닐경우 크기 줄이기
                                //width = width + (unit / 100.0 * (100.0 - aLong));
                                information_app_MutableLiveData.postValue(Math.round( max_width - (( unit / 100.0 * ( 100 - aLong )) * (100 - aLong))));
                            }
                        }
                    }

                    */

                    if(aLong == 101){
                        try {
                            Log.d("Sans", "출발 시작");
                            ((MainActivity)MainActivity.Main_Context).getWindowManager().getDefaultDisplay().getRealSize(window_size);
                            //min_width = 0;

                            // unit = (max_width - min_width) / 100.0;

                            for(int i = 0; i< information_app_Layout_Array.size(); i++){
                                Log.d("Sans", "시작 information_app_Layout_Array = " + i);
                                if(i == select_num){
                                    if (information_app_list_open) {
                                        information_app_ScrollView_Array.get(i).getLayoutParams().width = (int)Math.round((window_size.x - ( getResources().getDimension(R.dimen.public_10dp) * 6.6f )));
                                        information_app_ScrollView_Array.get(i).getLayoutParams().height = (int)Math.round((window_size.y));
                                    } else {
                                        information_app_ScrollView_Array.get(i).getLayoutParams().width = (int)information_app_real_size_Array.get(i).getX();
                                        information_app_ScrollView_Array.get(i).getLayoutParams().height = 0;
                                    }
                                }else{
                                    if (information_app_list_open) {
                                        information_app_Layout_Array.get(i).getLayoutParams().width = 0;
                                        information_app_Layout_Array.get(i).getLayoutParams().height = 0;
                                    } else {
                                        information_app_Layout_Array.get(i).getLayoutParams().width = (int)information_app_real_size_Array.get(i).getX();
                                        information_app_Layout_Array.get(i).getLayoutParams().height = 0;
                                    }
                                }

                                information_app_ScrollView_Array.get(i).invalidate();
                                information_app_ScrollView_Array.get(i).requestLayout();
                                information_app_Layout_Array.get(i).invalidate();
                                information_app_Layout_Array.get(i).requestLayout();
                            }


                        } catch (Exception e) {

                            Log.d("Sans", "시작 에러!");
                        }
                    }else if(aLong == 102){
                        try {
                            Log.d("Sans", "끝");
                            for(int i = 0; i < information_app_Layout_Array.size(); i++){
                                Log.d("Sans", "끝 information_app_Layout_Array = " + i);
                                if(i == select_num){
                                    if (information_app_list_open) {
                                        information_app_ScrollView_Array.get(i).getLayoutParams().width = (int)information_app_real_size_Array.get(i).getX();
                                        information_app_ScrollView_Array.get(i).getLayoutParams().height = 0;
                                    } else {
                                        information_app_ScrollView_Array.get(i).getLayoutParams().width = (int)Math.round((window_size.x - ( getResources().getDimension(R.dimen.public_10dp) * 6.6f )));
                                        information_app_ScrollView_Array.get(i).getLayoutParams().height = (int)Math.round((window_size.y));
                                    }
                                }else{
                                    if (information_app_list_open) {
                                        information_app_Layout_Array.get(i).getLayoutParams().width = (int)information_app_real_size_Array.get(i).getX();
                                    } else {
                                        information_app_Layout_Array.get(i).getLayoutParams().width = 0;
                                        information_app_Layout_Array.get(i).getLayoutParams().height = 0;
                                    }
                                }

                                Log.d("Sans", "information_app_real_size_Array.get(" + i + ") = " + information_app_real_size_Array.get(i));
                                information_app_ScrollView_Array.get(i).invalidate();
                                information_app_ScrollView_Array.get(i).requestLayout();
                                information_app_Layout_Array.get(i).invalidate();
                                information_app_Layout_Array.get(i).requestLayout();
                            }

                            if (information_app_list_open) {
                                information_app_list_open = false;
                            } else {
                                information_app_list_open = true;
                            }
                            //information_app_MutableLiveData.postValue(Math.round(width));
                            information_app_list_running = false;
                        } catch (Exception e) {
                            Log.d("Sans", "끝 에러!");

                        }
                    }else{

                    }
                }
            });

            information_app_Observer = new Observer<Long>() {

                double max_width;
                double min_width;

                double unit;
                double width;

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    //information_app_MutableLiveData.postValue(101L);
                }

                @Override
                public void onNext(@NonNull Long aLong) {
                    try {
                        information_app_MutableLiveData.postValue(aLong);
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onComplete() {
                    //information_app_MutableLiveData.postValue(102L);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }
            };

            information_app_Observable = Observable.interval(1, TimeUnit.MILLISECONDS).take(1);

        }

        Setting_Information();
        information_app_Observable.subscribeOn(Schedulers.io()).subscribe(information_app_Observer);

        return rootView;
    }

    void Setting_Information(){
        information_app_real_size_Array.clear();

        for(int i = 0; i<information_app_ScrollView_Array.size(); i++){
            int num = i;

            information_app_Touch_Array.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    select_num = num;
                    /*
                    if (!information_app_list_running) {
                        Log.d("Sans", "버튼 클릭 작동 시작 + " + select_num);
                        information_app_list_running = true;
                        information_app_Observable.subscribeOn(Schedulers.io()).subscribe(information_app_Observer);
                    }
                    */
                    for(int i = 0; i< information_app_Layout_Array.size(); i++){
                        if(select_num == i){
                            if (information_app_list_open) {
                                information_app_ScrollView_Array.get(i).getLayoutParams().width = 0;
                                information_app_ScrollView_Array.get(i).getLayoutParams().height = 0;
                            }else{
                                Log.d("Sans", "열려라");
                                ((MainActivity)MainActivity.Main_Context).getWindowManager().getDefaultDisplay().getRealSize(window_size);
                                information_app_ScrollView_Array.get(i).getLayoutParams().width = (int)Math.round((window_size.x - ( getResources().getDimension(R.dimen.public_10dp) * 6.6f )));
                                information_app_ScrollView_Array.get(i).getLayoutParams().height = (int)Math.round((window_size.y - ( getResources().getDimension(R.dimen.public_10dp) * 24f )));
                            }
                        }else{
                            if (information_app_list_open) {
                                information_app_Layout_Array.get(i).setVisibility(View.VISIBLE);
                            }else{
                                information_app_Layout_Array.get(i).setVisibility(View.GONE);
                            }
                        }
                        information_app_ScrollView_Array.get(i).invalidate();
                        information_app_ScrollView_Array.get(i).requestLayout();
                        information_app_Layout_Array.get(i).invalidate();
                        information_app_Layout_Array.get(i).requestLayout();
                    }

                    if(information_app_list_open){
                        information_app_list_open = false;
                    }else{
                        information_app_list_open = true;
                    }

                }
            });
        }
    }
}