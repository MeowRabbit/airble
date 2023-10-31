package stm.airble._4_main._4_faq.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Main_Faq_ViewPagerAdapter extends FragmentStateAdapter {

    ViewHolderPage_1 viewHolderPage_1;
    ViewHolderPage_2 viewHolderPage_2;

    public Main_Faq_ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
        viewHolderPage_1 = new ViewHolderPage_1();
        viewHolderPage_2 = new ViewHolderPage_2();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = position;

        if (index == 0) return viewHolderPage_1;
        else if (index == 1) return viewHolderPage_2;
        else return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public ViewHolderPage_1 getViewHolderPage_1() {
        return viewHolderPage_1;
    }

    public ViewHolderPage_2 getViewHolderPage_2() {
        return viewHolderPage_2;
    }
}