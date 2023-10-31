package stm.airble._4_main._4_faq;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._4_faq.viewpager2.Main_Faq_ViewPagerAdapter;
import stm.airble._4_main._4_faq.viewpager2.recyclerview.Main_Faq_RecyclerViewAdapter;
import stm.airble._4_main._4_faq.viewpager2.recyclerview.Main_Faq_RecyclerView_Item;

import java.util.ArrayList;

public class _41_FaqPageFragment extends Fragment {

    Handler handler;

    public static Fragment faq_page_Context;
    public static ArrayList<Main_Faq_RecyclerView_Item> faq_question_item_ArrayList;
    public static int faq_question_item_num = 0;
    public TextView question_TextView;
    public TextView answer_TextView;
    public static ViewPager2 faq_ViewPager2;
    public Main_Faq_ViewPagerAdapter faq_ViewPager2_adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq_page, container, false);
        faq_page_Context = this;
        handler = setting_handler();

        // 아이디 맞춰주기
        faq_ViewPager2 = view.findViewById(R.id.main_faq_ViewPager2);

        faq_ViewPager2_adapter = new Main_Faq_ViewPagerAdapter((FragmentActivity) MainActivity.Main_Context);
        faq_ViewPager2.setAdapter(faq_ViewPager2_adapter);
        faq_ViewPager2.setOffscreenPageLimit(2);
        faq_ViewPager2.setUserInputEnabled(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Sans", "faq_page Resume");


        faq_ViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //handler.sendEmptyMessageDelayed(1, 10);
            }
        });

    }

    public Handler setting_handler(){
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1:
                        //faq_ViewPager2_adapter.getViewHolderPage_1().
                        //faq_ViewPager2_adapter.getViewHolderPage_1().Start_Setting();
                        //faq_ViewPager2_adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        return handler;
    }

}