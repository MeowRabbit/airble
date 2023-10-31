package stm.airble._4_main._4_faq.viewpager2.recyclerview;

import java.io.Serializable;

public class Main_Faq_RecyclerView_Item implements Serializable {
    private int Faq_question_order;
    private String Faq_question;
    private String Faq_answer;


    public int getFaq_question_order() {
        return Faq_question_order;
    }

    public void setFaq_question_order(int Faq_question_order) {
        this.Faq_question_order = Faq_question_order;
    }

    public String getFaq_question() {
        return Faq_question;
    }

    public void setFaq_question(String Faq_question) {
        this.Faq_question = Faq_question;
    }

    public String getFaq_answer() {
        return Faq_answer;
    }

    public void setFaq_answer(String faq_answer) {
        Faq_answer = faq_answer;
    }
}