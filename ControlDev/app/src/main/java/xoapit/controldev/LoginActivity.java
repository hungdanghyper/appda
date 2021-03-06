package xoapit.controldev;

import android.content.Intent;
import android.inputmethodservice.ExtractEditText;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    EditText txtuser, txtpass;


    /*
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        txtuser = (EditText) findViewById(R.id.txtUser);
        txtpass = (EditText) findViewById(R.id.txtPass);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void btn_login_OnClick(View view) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String user = txtuser.getText().toString().trim();
                String password = txtpass.getText().toString().trim();

                // Check for empty data in the form
                if (!user.isEmpty() && !password.isEmpty()) {
                    // login user
                    new goiWebService().execute("https://davdk.herokuapp.com/androidLogin.php");
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter Username or Password!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    class goiWebService extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        //xu li voi du lieu phan hoi ve tu server
        @Override
        protected void onPostExecute(String s) {
            //Log.d(TAG,(s.trim() == "OK") ? "true": "false");
            /*if (!s.equals("OK")) {
                Log.d(TAG,s);*/
                IntentReset();
            /*} else {
                if (s.equals("0 result")){
                    Toast.makeText(getApplicationContext(), "Account does not exist.", Toast.LENGTH_SHORT).show();}
                *//*else {
                    Toast.makeText(getApplicationContext(), "No Connect", Toast.LENGTH_SHORT).show();
                }*//*
            }*/
        }

        public String makePostRequest(String url) {
            HttpClient httpClient = new DefaultHttpClient();

            // URL của trang web nhận request
            HttpPost httpPost = new HttpPost(url);

            // Các tham số truyền
            List nameValuePair = new ArrayList(2);
            nameValuePair.add(new BasicNameValuePair("user", txtuser.getText().toString()));
            nameValuePair.add(new BasicNameValuePair("pass", txtpass.getText().toString()));
            Log.d(TAG, nameValuePair.toString());
            //Encoding POST data
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                //Log.d(TAG, "httpPost: " + httpPost.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String kq = "";
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                kq = EntityUtils.toString(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "kq: " + kq);
            return kq;
        }

        public void IntentReset() {
            try {
                Intent i2 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i2);
            } catch (Exception e) {
            }
            txtpass.setText("");
        }


    }
}
