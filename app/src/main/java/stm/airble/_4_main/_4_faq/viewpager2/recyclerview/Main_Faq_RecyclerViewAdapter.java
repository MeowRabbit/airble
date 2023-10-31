package stm.airble._4_main._4_faq.viewpager2.recyclerview;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._4_faq._41_FaqPageFragment;

import java.util.ArrayList;

public class Main_Faq_RecyclerViewAdapter extends RecyclerView.Adapter<Main_Faq_RecyclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recycler_item_TextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recycler_item_TextView = (TextView) itemView.findViewById(R.id.faq_recycler_item_TextView);
        }
    }

    private ArrayList<Main_Faq_RecyclerView_Item> mList = null;

    public Main_Faq_RecyclerViewAdapter(ArrayList<Main_Faq_RecyclerView_Item> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_faq_inner_page_recycler_item, parent, false);
        Main_Faq_RecyclerViewAdapter.ViewHolder vh = new Main_Faq_RecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull Main_Faq_RecyclerViewAdapter.ViewHolder holder, int position) {
        Main_Faq_RecyclerView_Item item = mList.get(position);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.recycler_item_TextView.getLayoutParams();
        if(item.getFaq_question_order() == 0){
            params.topMargin = (int)((_41_FaqPageFragment.faq_page_Context).getResources().getDimensionPixelOffset(R.dimen.public_10dp) * 3.3);
        }else{
            params.topMargin = (int)((_41_FaqPageFragment.faq_page_Context).getResources().getDimensionPixelOffset(R.dimen.public_10dp) * 1.6);
        }
        holder.recycler_item_TextView.setLayoutParams(params);

        holder.recycler_item_TextView.setText(item.getFaq_question());
        holder.recycler_item_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.question_TextView.setText(item.getFaq_question());
                ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.answer_TextView.setText(item.getFaq_answer());
                ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_question_item_num = position;
                ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_ViewPager2.setCurrentItem(1);
            }
        });
    }

    public void setData(ArrayList<Main_Faq_RecyclerView_Item> mList) {
        this.mList = mList;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}