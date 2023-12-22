package stm.airble._4_main._5_setting.airble.viewpager2.recyclerview;

import android.content.Context;
import android.content.Intent;
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
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._5_setting.airble._51_SettingsAirbleFragment;
import stm.airble._4_main._5_setting.dialog.Shared_Remove_Dialog_Activity;

public class Main_Setting_Shared_RecyclerViewAdapter extends RecyclerView.Adapter<Main_Setting_Shared_RecyclerViewAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout shared_LinearLayout;
        TextView shared_email_TextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shared_LinearLayout = itemView.findViewById(R.id.settings_airble_shared_item_LinearLayout);
            shared_email_TextView = (TextView) itemView.findViewById(R.id.settings_airble_shared_item_email_TextView);

        }
    }

    private ArrayList<String> mList = null;

    public Main_Setting_Shared_RecyclerViewAdapter(ArrayList<String> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_settings_airble_setting_page_2_shared_item, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = mList.get(position);
        int select_num = position;
        holder.shared_email_TextView.setText(item);
        holder.shared_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.Main_Context, Shared_Remove_Dialog_Activity.class);
                intent.putExtra("shared_email", item);
                ((_51_SettingsAirbleFragment)_51_SettingsAirbleFragment.Setting_Airble_Context).start_shared_popup_bool = true;
                ((_51_SettingsAirbleFragment)_51_SettingsAirbleFragment.Setting_Airble_Context).running_popup_bool = true;
                ((MainActivity)MainActivity.Main_Context)._51_Settings_Airble_Fragment.drawer_adapter.shared_page.shared_remove_ActivityResultLauncher.launch(intent);
                Log.d("Sans", item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}