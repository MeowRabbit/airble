package stm.airble._4_main._5_setting.viewpager2;

import static stm.airble._0_public.Public_Values.APP_Airble_Model_Array;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;
import stm.airble._0_public.Airble_Model;

import java.util.ArrayList;

public class Main_Setting_ViewPagerAdapter extends RecyclerView.Adapter<ViewHolderPage> {

    private ArrayList<Airble_Model> listData;

    public Main_Setting_ViewPagerAdapter(ArrayList<Airble_Model> listData) {
        this.listData = listData;

    }

    @Override
    public ViewHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_settings_pages, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPage holder, int position)
    {
        int index = position % listData.size();

        if(holder instanceof ViewHolderPage){
            ViewHolderPage viewHolder = (ViewHolderPage) holder;
            viewHolder.onBind(index);
        }
    }

    @Override
    public int getItemCount() {
        if(APP_Airble_Model_Array.size() == 1){
            return 1;
        }else{
            return Integer.MAX_VALUE;
        }
    }
}