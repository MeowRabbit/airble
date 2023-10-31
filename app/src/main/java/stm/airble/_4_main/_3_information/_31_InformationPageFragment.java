package stm.airble._4_main._3_information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._3_information.viewpager2.Main_Information_ViewPagerAdapter;

public class _31_InformationPageFragment extends Fragment {

    ViewPager2 information_ViewPager2;
    Main_Information_ViewPagerAdapter information_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_page, container, false);

        //아이디 맞춰주기
        information_ViewPager2 = view.findViewById(R.id.information_ViewPager2);

        information_adapter = new Main_Information_ViewPagerAdapter((FragmentActivity) MainActivity.Main_Context);
        information_ViewPager2.setAdapter(information_adapter);
        information_ViewPager2.setUserInputEnabled(false);

        return view;
    }
}