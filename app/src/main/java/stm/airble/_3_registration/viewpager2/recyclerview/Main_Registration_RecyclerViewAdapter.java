package stm.airble._3_registration.viewpager2.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import stm.airble.R;
import stm.airble._3_registration.RegistrationActivity;

public class Main_Registration_RecyclerViewAdapter extends RecyclerView.Adapter<Main_Registration_RecyclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_item_LinearLayout;
        TextView recycler_item_TextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recycler_item_LinearLayout = itemView.findViewById(R.id.registration_page_2_recycler_item_LinearLayout);
            recycler_item_TextView = (TextView) itemView.findViewById(R.id.registration_page_2_recycler_item_TextView);
        }
    }

    private ArrayList<String> mList = null;

    public Main_Registration_RecyclerViewAdapter(ArrayList<String> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_registration_page_recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mList.get(position);

        holder.recycler_item_TextView.setText(item);
        holder.recycler_item_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Sans",item);
                ((RegistrationActivity)RegistrationActivity.registration_context).Page3_Select_Router_SSID = item;
                ((RegistrationActivity)RegistrationActivity.registration_context).Page3_SSID_TextView.setText(item);
                ((RegistrationActivity)RegistrationActivity.registration_context).viewPager2.setCurrentItem(2,true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}