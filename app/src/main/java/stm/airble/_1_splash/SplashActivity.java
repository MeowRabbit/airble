package stm.airble._1_splash;


import static stm.airble._0_public.Public_Values.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import stm.airble.R;
import stm.airble._4_main.MainActivity;
import stm.airble._0_public.Airble_Model;
import stm.airble._0_public.GET_RequestHttpURLConnection;
import stm.airble._0_public.Loading_ProgressDialog;
import stm.airble._3_registration.RegistrationActivity;
import stm.airble._1_splash.dialog.Join_Dialog_Activity;


public class SplashActivity extends AppCompatActivity {
    public static Context Splash_Context;
    Loading_ProgressDialog Splash_Loading;
    Handler handler;

    SignInButton splash_login_Button;
    //Button change_login_Button;

    static ActivityResultLauncher<Intent> join_ActivityResultLauncher;

    // 파이어베이스 인증 객체 생성
    FirebaseAuth auth;
    // 구글api클라이언트
    GoogleSignInClient client;
    // 구글 계정
    GoogleSignInAccount account;
    ActivityResultLauncher<Intent> login_resultLauncher;

    private static final String TAG = "Sans";
    private static final int RC_SIGN_IN = 9001;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Splash_Context = this;
        Splash_Loading = new Loading_ProgressDialog(Splash_Context);

        // 아이디 맞춰주기
        splash_login_Button = findViewById(R.id.splash_login_Button);
        //change_login_Button = findViewById(R.id.test_logout_btn);

        Start_Settings(Splash_Context);

        join_ActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        Splash_Loading.Start_Loading();
                        try{
                            String server_url = Server_Domain + "airble_test?num=41&email=" + User_Email;
                            URL url = new URL(server_url);
                            new Airble_HttpConnection().execute(url);
                        } catch (Exception e) {
                            Splash_Loading.Stop_Loading();
                        }
                    }else{
                        splash_login_Button.setVisibility(View.VISIBLE);
                    }
                });

        handler = new Handler(){
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:{
                        account = GoogleSignIn.getLastSignedInAccount(SplashActivity.this);

                        if (account != null) { // 로그인 되있는 경우
                            Splash_Loading.Start_Loading();
                            User_Email = account.getEmail();
                            Login_User();

                        }else {
                            signIn();
                        }
                    }
                    break;

                    default:
                        break;
                }
            }

        };

        login_resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        //handleSignInResult(task);
                        try {
                            GoogleSignInAccount acct = task.getResult(ApiException.class);
                            if (acct != null) {
                                firebaseAuthWithGoogle(acct.getIdToken());

                                // String personName = acct.getDisplayName();
                                // String personGivenName = acct.getGivenName();
                                // String personFamilyName = acct.getFamilyName();
                                User_Email = acct.getEmail();

                                Log.d("Sans", User_Email);
                                // String personId = acct.getId();
                                // Uri personPhoto = acct.getPhotoUrl();
                                Login_User();

                            }
                        } catch (ApiException e) {
                            Splash_Loading.Stop_Loading();
                            // The ApiException status code indicates the detailed failure reason.
                            // Please refer to the GoogleSignInStatusCodes class reference for more information.
                            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
                        }
                    }else{
                        Splash_Loading.Stop_Loading();
                    }
                });

        handler.sendEmptyMessageDelayed(0, 1500);   //1.5초 후 시작

        // 파이어베이스 인증 객체 선언
        auth = FirebaseAuth.getInstance();

        // Google 로그인을 앱에 통합
        // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, googleSignInOptions);
        account = GoogleSignIn.getLastSignedInAccount(SplashActivity.this);

        splash_login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signChange();
                /*
                account = GoogleSignIn.getLastSignedInAccount(SplashActivity.this);

                if(account != null){ // 로그인 되있는 경우
                    Start_Loading(Splash_Context);
                    User_Email = account.getEmail();
                    Login_User();
                }else{
                    signIn();
                }

                 */
            }
        });

        //change_login_Button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        signChange();
        //    }
        //});
    }

    private void signIn(){
        Splash_Loading.Start_Loading();
        Intent signInIntent = client.getSignInIntent();
        login_resultLauncher.launch(signInIntent);
    }


    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        //Toast.makeText(SplashActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(SplashActivity.this, "로그인에 실패하였습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signChange(){
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        auth.signOut();
                        account = null;

                        signIn();
                    }
                });
    }

    public void Login_User(){
        try{
            String server_url = Server_Domain + "airble_test?num=40&email=" + User_Email;
            Log.d("Sans", "server_url = " + server_url);
            URL url = new URL(server_url);
            new Airble_HttpConnection().execute(url);
        }catch (Exception e){
            Splash_Loading.Stop_Loading();
        }
    }

    public void Search_My_Airble(){
        try{
            String server_url = Server_Domain + "airble_test?num=42&email=" + User_Email;
            URL url = new URL(server_url);
            Log.d("Sans", "server_url = " + server_url);
            new Airble_HttpConnection().execute(url);
        } catch (Exception e) {
            Splash_Loading.Stop_Loading();
        }
    }

    //서버에 연결하는 코딩
    private class Airble_HttpConnection extends AsyncTask<URL, Integer, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String data = "";
            if (urls.length == 0) {
                return " URL is empty";
            }
            try {
                GET_RequestHttpURLConnection connection = new GET_RequestHttpURLConnection();
                data = connection.request(urls[0]);
            } catch (Exception e) {
                data = e.getMessage();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            if (data != null) { //연결성공
                Log.d("Sans", "data = " + data);
                String code = data.split("\\[\\]\\[")[1].split("\\]")[0].trim();
                //String code = data;
                switch (code){
                    // 유저 로그인
                    case "S40":
                    {
                        // 로그인 성공 기기 리스트 불러오기
                        Search_My_Airble();
                    }
                    break;

                    case "F40":
                    {
                        // 로그인 정보없음 회원가입 시키기
                        Splash_Loading.Stop_Loading();

                        Intent intent = new Intent(Splash_Context, Join_Dialog_Activity.class);
                        join_ActivityResultLauncher.launch(intent);

                    }
                    break;

                    // 유저 로그인
                    case "S41":
                    {
                        // 회원가입 성공 기기 리스트 불러오기
                        Toast.makeText(SplashActivity.Splash_Context, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        Splash_Loading.Start_Loading();
                        Search_My_Airble();
                    }
                    break;

                    // Airble 현재 데이터 가져오기
                    case "S42":
                    {
                        try{
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            APP_Airble_Model_Array.clear();
                            String values[] = data.split(",,");
                            for(int i = 1; i< values.length; i++){
                                String value[] = values[i].split(",");

                                Airble_Model airble = new Airble_Model();
                                airble.setMAC_Address(value[0]);
                                if(Integer.parseInt(value[1]) == 1){
                                    airble.setOwner(true);
                                }else{
                                    airble.setOwner(false);
                                }
                                airble.setSSID(value[3]);
                                airble.setNick_Name(value[4]);
                                airble.setUpdate_date(LocalDateTime.parse(value[5].split("\\.")[0], dateTimeFormatter));

                                APP_Airble_Model_Array.add(airble);
                            }

                            Setting_Devices_Page();

                        } catch (Exception e) {
                            Splash_Loading.Stop_Loading();
                        }
                        Splash_Loading.Stop_Loading();

                        Intent intent = new Intent(Splash_Context, MainActivity.class);
                        Splash_Context.startActivity(intent);
                        ((SplashActivity)Splash_Context).finish();
                    }
                    break;

                    case "F42": // 등록된 기기가 없음
                    {
                        Splash_Loading.Stop_Loading();
                        Intent intent = new Intent(Splash_Context, RegistrationActivity.class);
                        ((SplashActivity)SplashActivity.Splash_Context).startActivity(intent);
                        ((SplashActivity)SplashActivity.Splash_Context).finish();
                    }
                    break;

                    case "E40":
                    case "F41":
                    case "E41":
                    case "E42":{
                        // 서버 오류
                        Toast.makeText(SplashActivity.Splash_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        ((SplashActivity)SplashActivity.Splash_Context).splash_login_Button.setVisibility(View.VISIBLE);
                        //((SplashActivity)SplashActivity.Splash_Context).change_login_Button.setVisibility(View.VISIBLE);
                        Splash_Loading.Stop_Loading();
                    }
                    break;

                    default:{
                        Toast.makeText(Splash_Context, "에러코드 : " + code, Toast.LENGTH_SHORT).show();
                        ((SplashActivity)SplashActivity.Splash_Context).splash_login_Button.setVisibility(View.VISIBLE);
                        //((SplashActivity)SplashActivity.Splash_Context).change_login_Button.setVisibility(View.VISIBLE);
                        Splash_Loading.Stop_Loading();
                    }

                }

            } else {  //연결실패
                Toast.makeText(SplashActivity.Splash_Context, "서버와 연결상태가 좋지않습니다. 잠시후에 다시 시도해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                ((SplashActivity)SplashActivity.Splash_Context).splash_login_Button.setVisibility(View.VISIBLE);
                //((SplashActivity)SplashActivity.Splash_Context).change_login_Button.setVisibility(View.VISIBLE);
                Splash_Loading.Stop_Loading();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(Close_App_bool){
            System_Handler.removeMessages(CLOSE_APP);
            super.onBackPressed();
        }else{
            Toast.makeText(SplashActivity.this, "뒤로가기를 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
            System_Handler.sendEmptyMessageDelayed(CLOSE_APP, CLOSE_APP_Interval);
            Close_App_bool = true;
        }
    }
}
