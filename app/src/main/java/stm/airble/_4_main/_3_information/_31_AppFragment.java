package stm.airble._4_main._3_information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class _31_AppFragment extends Fragment {

    ImageButton back_Button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_app, container, false);

        back_Button = view.findViewById(R.id.back_Button);

        back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_3_Information();
            }
        });

        return view;
    }
}