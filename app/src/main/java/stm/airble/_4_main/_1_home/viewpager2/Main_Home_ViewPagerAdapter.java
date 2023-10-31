package stm.airble._4_main._1_home.viewpager2;

import static stm.airble._0_public.Public_Values.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;

public class Main_Home_ViewPagerAdapter extends RecyclerView.Adapter<_1_Home_ViewHolderPage> {

    public Main_Home_ViewPagerAdapter() {

    }

    @Override
    public _1_Home_ViewHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_page, parent, false);
        return new _1_Home_ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(_1_Home_ViewHolderPage holder, int position) {
        if(holder instanceof _1_Home_ViewHolderPage){
            _1_Home_ViewHolderPage viewHolder = (_1_Home_ViewHolderPage) holder;
            viewHolder.onBind(position);
        }
    }

    @Override
    public int getItemCount() {
        return APP_Airble_Model_Array.size();
    }
}
