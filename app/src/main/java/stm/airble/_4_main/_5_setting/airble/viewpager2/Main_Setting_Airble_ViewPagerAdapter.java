package stm.airble._4_main._5_setting.airble.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Main_Setting_Airble_ViewPagerAdapter extends FragmentStateAdapter {

    public Main_Setting_Airble_ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    public ViewHolderPage_1 information_page = new ViewHolderPage_1();
    public ViewHolderPage_2 shared_page = new ViewHolderPage_2();

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = position;

        if (index == 0) return information_page;
        else if (index == 1) return shared_page;
        else return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}