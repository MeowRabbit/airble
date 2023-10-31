package stm.airble._4_main._4_faq.viewpager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._4_main._4_faq.viewpager2.recyclerview.Main_Faq_RecyclerView_Item;

public class ViewHolderPage_2 extends Fragment {

    ImageButton inner_page_back_ImageButton;
    TextView inner_page_question_TextView;
    NestedScrollView inner_page_answer_NestedScrollView;
    LinearLayout inner_page_answer_LinearLayout;
    TextView inner_page_answer_TextView;

    Main_Faq_RecyclerView_Item item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_faq_inner_page_2, container, false);

        if(((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_question_item_ArrayList.size() > 0){
            item = ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_question_item_ArrayList.get(((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_question_item_num);
        }

        // 아이디 맞춰주기
        inner_page_back_ImageButton = rootView.findViewById(R.id.faq_inner_page_back_ImageButton);
        inner_page_question_TextView = rootView.findViewById(R.id.faq_inner_page_question_TextView);
        inner_page_answer_NestedScrollView = rootView.findViewById(R.id.faq_inner_page_answer_NestedScrollView);
        inner_page_answer_LinearLayout = rootView.findViewById(R.id.faq_inner_page_answer_LinearLayout);
        inner_page_answer_TextView = rootView.findViewById(R.id.faq_inner_page_answer_TextView);


        ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.question_TextView = inner_page_question_TextView;
        ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.answer_TextView = inner_page_answer_TextView;


        // 질문
        {
            inner_page_back_ImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)MainActivity.Main_Context)._41_faqPageFragment.faq_ViewPager2.setCurrentItem(0);
                }
            });

            if(item != null) {
                inner_page_question_TextView.setText(item.getFaq_question());
            }

        }

        // 답변
        {
            if(item != null) {
                inner_page_answer_TextView.setText(item.getFaq_answer());
            }
        }

        return rootView;
    }
}