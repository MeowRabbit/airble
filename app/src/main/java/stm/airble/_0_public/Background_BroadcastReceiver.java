package stm.airble._0_public;

import static stm.airble._0_public.Background_Alarm_Service.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Background_BroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Sans", "intent.getAction() = " + intent.getAction());
        switch (intent.getAction()){
            case "alarm_off":
                Log.d("Sans", "꺼져라 알람");
                context.stopService(new Intent(context, Background_Alarm_Service.class));
                break;
        }
    }
}
