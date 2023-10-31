package stm.airble._0_public;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;

import stm.airble.R;

public class Loading_ProgressDialog extends Dialog
{
    public boolean show_bool = false;

    public Loading_ProgressDialog(Context context)
    {
        super(context);
        // 다이얼 로그 제목을 안보이게...
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_progress);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(false);
    }

    public void Start_Loading(){
        if(show_bool){
            Stop_Loading();
        }
        this.show();
        show_bool = true;
    }

    public void Stop_Loading(){
        if(show_bool){
            show_bool = false;
            this.dismiss();
        }
    }
}