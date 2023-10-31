package stm.airble._5_add_airble.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AddAirble_ViewPagerAdapter extends FragmentStateAdapter {

    public AddAirble_ViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = position;

        if (index == 0) return new ViewHolderPage_1();
        else if (index == 1) return new ViewHolderPage_2();
        else if (index == 2) return new ViewHolderPage_3();
        else if (index == 3) return new ViewHolderPage_4();
        else return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
