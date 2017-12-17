package tech.babako.passget;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by Arif on 12 Ara 2017.
 */

public class BackgroundList extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    Context context;
    private String response = null;
    private WeakReference<Activity> activity;
    BackgroundList(Activity ctx)
    {
        context = ctx;
        this.activity = new WeakReference<Activity>(ctx);
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String sync_url = "http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/list.php";
        if(type.equals("sync"))
        {
            try
            {
                String basketID = params[1];
                URL url = new URL(sync_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("basketID","UTF-8")+"="+URLEncoder.encode(basketID,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line;
                while((line = bufferedReader.readLine()) !=null)
                {
                    result +=line+"\n";
                }
                bufferedReader.close();
                outputStream.close();
                httpURLConnection.disconnect();
                return result;

            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onPreExecute() {



    }

    @Override
    protected void onPostExecute(String result) {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
