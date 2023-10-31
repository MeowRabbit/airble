package stm.airble._4_main._4_faq.viewpager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._4_faq.viewpager2.recyclerview.Main_Faq_RecyclerViewAdapter;
import stm.airble._4_main._4_faq.viewpager2.recyclerview.Main_Faq_RecyclerView_Item;

public class ViewHolderPage_1 extends Fragment {

    TextView faq_page_title_TextView, faq_page_sub_TextView;
    ImageButton faq_page_back_ImageButton;
    ImageView faq_page_ImageView;
    RecyclerView faq_page_RecyclerView;
    Main_Faq_RecyclerViewAdapter faq_RecyclerViewAdapter;

    ImageButton faq_inner_page_search_back_ImageButton;
    EditText faq_inner_page_search_EditText;
    ImageButton faq_inner_page_search_ImageButton;

    public static LinearLayout faq_choice_keyword_LinearLayout, faq_search_keyword_LinearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_faq_inner_page_1, container, false);

        // 아이디 맞춰주기
        faq_page_title_TextView = rootView.findViewById(R.id.faq_page_title_TextView);
        faq_page_sub_TextView = rootView.findViewById(R.id.faq_page_sub_TextView);
        faq_page_back_ImageButton = rootView.findViewById(R.id.faq_page_back_ImageButton);
        faq_page_ImageView = rootView.findViewById(R.id.faq_page_ImageView);
        faq_page_RecyclerView = rootView.findViewById(R.id.faq_page_RecyclerView);

        faq_inner_page_search_back_ImageButton = rootView.findViewById(R.id.faq_inner_page_search_back_ImageButton);
        faq_inner_page_search_EditText = rootView.findViewById(R.id.faq_inner_page_search_EditText);
        faq_inner_page_search_ImageButton = rootView.findViewById(R.id.faq_inner_page_search_ImageButton);

        faq_choice_keyword_LinearLayout = rootView.findViewById(R.id.faq_choice_keyword_LinearLayout);
        faq_search_keyword_LinearLayout = rootView.findViewById(R.id.faq_search_keyword_LinearLayout);

        Start_Setting();

        return rootView;
    }

    public void Start_Setting(){
        // 상단부 셋팅 관련
        {
            switch (((MainActivity)MainActivity.Main_Context).faqFragment_keyword){
                case "APP":
                    faq_page_title_TextView.setText("앱 관련");
                    faq_page_sub_TextView.setText("APP 사용 중 발생한 문제 및 궁금증을 해결해 드립니다.");
                    faq_page_ImageView.setImageResource(R.drawable.faq_app);
                    faq_choice_keyword_LinearLayout.setVisibility(View.VISIBLE);
                    faq_search_keyword_LinearLayout.setVisibility(View.GONE);
                    break;

                case "Device":
                    faq_page_title_TextView.setText("기기 관련");
                    faq_page_sub_TextView.setText("기기 사용 중 발생한 문제 및 궁금증을 해결해 드립니다.");
                    faq_page_ImageView.setImageResource(R.drawable.faq_device);
                    faq_choice_keyword_LinearLayout.setVisibility(View.VISIBLE);
                    faq_search_keyword_LinearLayout.setVisibility(View.GONE);
                    break;

                case "Purchase":
                    faq_page_title_TextView.setText("구매 관련");
                    faq_page_sub_TextView.setText("구매 중 발생한 문제 및 궁금증을 해결해 드립니다.");
                    faq_choice_keyword_LinearLayout.setVisibility(View.VISIBLE);
                    faq_search_keyword_LinearLayout.setVisibility(View.GONE);
                    break;

                case "Information":
                    faq_page_title_TextView.setText("정보 관련");
                    faq_page_sub_TextView.setText("정보와 관련된 궁금증을 해결해 드립니다.");
                    faq_choice_keyword_LinearLayout.setVisibility(View.VISIBLE);
                    faq_search_keyword_LinearLayout.setVisibility(View.GONE);
                    break;

                default:
                    faq_inner_page_search_EditText.setText(((MainActivity)MainActivity.Main_Context).faqFragment_keyword);
                    faq_choice_keyword_LinearLayout.setVisibility(View.GONE);
                    faq_search_keyword_LinearLayout.setVisibility(View.VISIBLE);
                    break;
            }

            faq_page_back_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)MainActivity.Main_Context).Change_Fragment_4_Faq();
                }
            });

            faq_inner_page_search_back_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)MainActivity.Main_Context).Change_Fragment_4_Faq();
                }
            });

            faq_inner_page_search_ImageButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    Log.d("Sans", "검색 누름");
                    if(faq_inner_page_search_EditText.getText().toString().trim().length() == 0){
                        Toast.makeText(MainActivity.Main_Context, "검색할 단어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }else if(faq_inner_page_search_EditText.getText().toString().trim().length() < 2){
                        Toast.makeText(MainActivity.Main_Context, "2글자 이상의 검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        faq_RecyclerViewAdapter.setData(Loading_Questions_Item_Array(faq_inner_page_search_EditText.getText().toString().trim()));
                        faq_RecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });

        }


        // 질문 리스트 관련
        {

            ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_question_item_ArrayList = Loading_Questions_Item_Array(((MainActivity)MainActivity.Main_Context).faqFragment_keyword);
            faq_RecyclerViewAdapter = new Main_Faq_RecyclerViewAdapter(((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_question_item_ArrayList);
            faq_page_RecyclerView.setAdapter(faq_RecyclerViewAdapter);
            faq_page_RecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.Main_Context));

        }
    }

    public ArrayList<Main_Faq_RecyclerView_Item> Loading_Questions_Item_Array(String keyword){
        ArrayList<Main_Faq_RecyclerView_Item> list = new ArrayList<>();
        String questions[] = getResources().getStringArray(R.array.questions);
        String answers[] = getResources().getStringArray(R.array.answers);

        for(int i = 0; i < questions.length; i++){
            Main_Faq_RecyclerView_Item item = new Main_Faq_RecyclerView_Item();

            switch (keyword){
                case "APP":
                case "Device":
                case "Purchase":
                case "Information":
                    if(keyword.equals(questions[i].split("_")[0])){
                        item.setFaq_question_order(list.size());
                        item.setFaq_question(questions[i].substring(questions[i].indexOf("_") + 1));
                        item.setFaq_answer(answers[i].substring(answers[i].indexOf("_") + 1));
                        list.add(item);
                    }

                    break;

                default:
                    if(questions[i].substring(questions[i].indexOf("_") + 1).contains(keyword.trim())){
                        item.setFaq_question_order(list.size());
                        item.setFaq_question(questions[i].substring(questions[i].indexOf("_") + 1));
                        item.setFaq_answer(answers[i].substring(answers[i].indexOf("_") + 1));
                        list.add(item);
                    }
                    break;
            }

        }
        if(list.size() == 0){
            Toast.makeText(MainActivity.Main_Context, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
        }
        return list;
    }

}