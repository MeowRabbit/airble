package stm.airble._4_main._2_graph.viewpager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;

public class Main_Graph_ViewPagerAdapter extends RecyclerView.Adapter<ViewHolderPage> {

    public Main_Graph_ViewPagerAdapter() {

    }

    @Override
    public ViewHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_graph_pages, parent, false);
        return new ViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPage holder, int position) {
        if(holder instanceof ViewHolderPage){
            ViewHolderPage viewHolder = (ViewHolderPage) holder;
            viewHolder.onBind(position);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
