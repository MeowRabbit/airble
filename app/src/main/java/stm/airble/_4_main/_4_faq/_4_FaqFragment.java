package stm.airble._4_main._4_faq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._4_main.MainActivity;

public class _4_FaqFragment extends Fragment {

    ImageButton search_ImageButton;
    EditText search_EditText;

    RelativeLayout app_RelativeLayout, device_RelativeLayout, purchase_RelativeLayout, information_RelativeLayout;
    TextView app_main_TextView, app_sub_TextView, device_main_TextView, device_sub_TextView, purchase_main_TextView, purchase_sub_TextView, information_main_TextView, information_sub_TextView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("sans", "faq onattach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("sans", "faq create");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("sans", "faq onActivityCreated ");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("sans", "faq onStart ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("sans", "faq onResume ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("sans", "faq onPause ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("sans", "faq onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("sans", "faq onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("sans", "faq onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("sans", "faq onDetach ");
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        Log.d("sans", "faq createview");

        // 아이디 맞춰주기
        search_ImageButton = view.findViewById(R.id.main_faq_search_ImageButton);
        search_EditText = view.findViewById(R.id.main_faq_search_EditText);

        app_RelativeLayout = view.findViewById(R.id.faq_app_RelativeLayout);
        device_RelativeLayout = view.findViewById(R.id.faq_device_RelativeLayout);
        purchase_RelativeLayout = view.findViewById(R.id.faq_purchase_RelativeLayout);
        information_RelativeLayout = view.findViewById(R.id.faq_information_RelativeLayout);
        app_main_TextView = view.findViewById(R.id.faq_app_main_TextView);
        app_sub_TextView = view.findViewById(R.id.faq_app_sub_TextView);
        device_main_TextView = view.findViewById(R.id.faq_device_main_TextView);
        device_sub_TextView = view.findViewById(R.id.faq_device_sub_TextView);
        purchase_main_TextView = view.findViewById(R.id.faq_purchase_main_TextView);
        purchase_sub_TextView = view.findViewById(R.id.faq_purchase_sub_TextView);
        information_main_TextView = view.findViewById(R.id.faq_information_main_TextView);
        information_sub_TextView = view.findViewById(R.id.faq_information_sub_TextView);

        search_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_EditText.getText().toString().trim().length() == 0){
                    Toast.makeText(MainActivity.Main_Context, "검색할 단어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else if(search_EditText.getText().toString().trim().length() < 2){
                    Toast.makeText(MainActivity.Main_Context, "2글자 이상의 검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    ((MainActivity)MainActivity.Main_Context).faqFragment_keyword = search_EditText.getText().toString().trim();
                    ((MainActivity)MainActivity.Main_Context).Change_Fragment_41_Faq_Page();
                }
            }
        });

        app_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).faqFragment_keyword = "APP";
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_41_Faq_Page();
            }
        });

        device_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).faqFragment_keyword = "Device";
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_41_Faq_Page();
            }
        });

        purchase_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).faqFragment_keyword = "Purchase";
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_41_Faq_Page();
            }
        });

        information_RelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.Main_Context).faqFragment_keyword = "Information";
                ((MainActivity)MainActivity.Main_Context).Change_Fragment_41_Faq_Page();
            }
        });

        return view;
    }
}
