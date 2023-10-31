package stm.airble._4_main._3_information.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import stm.airble._4_main._4_faq.viewpager2.ViewHolderPage_1;
import stm.airble._4_main._4_faq.viewpager2.ViewHolderPage_2;

public class Main_Information_ViewPagerAdapter extends FragmentStateAdapter {

    APP_ViewHolderPage app_ViewHolderPage;

    public Main_Information_ViewPagerAdapter(FragmentActivity fa) {

        super(fa);
        app_ViewHolderPage = new APP_ViewHolderPage();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = position;

        if (index == 0) return app_ViewHolderPage;
        else if (index == 1) return app_ViewHolderPage;
        else return null;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public APP_ViewHolderPage getAPP_ViewHolderPage() {
        return app_ViewHolderPage;
    }

}
