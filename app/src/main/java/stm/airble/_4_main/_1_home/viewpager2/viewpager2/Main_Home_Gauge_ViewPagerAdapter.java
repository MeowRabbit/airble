package stm.airble._4_main._1_home.viewpager2.viewpager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;

public class Main_Home_Gauge_ViewPagerAdapter extends RecyclerView.Adapter<_1_Home_Gauge_ViewHolderPage> {

    int device_position;

    public Main_Home_Gauge_ViewPagerAdapter(int device_position) {
        this.device_position = device_position;
    }

    @Override
    public _1_Home_Gauge_ViewHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_gauge_page, parent, false);
        return new _1_Home_Gauge_ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(_1_Home_Gauge_ViewHolderPage holder, int position) {
        if(holder instanceof _1_Home_Gauge_ViewHolderPage){
            _1_Home_Gauge_ViewHolderPage viewHolder = (_1_Home_Gauge_ViewHolderPage) holder;
            viewHolder.onBind(device_position, position);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
