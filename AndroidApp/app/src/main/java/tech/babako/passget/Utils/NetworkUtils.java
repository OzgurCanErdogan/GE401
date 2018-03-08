package tech.babako.passget.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by bunya on 17-Dec-17.
 */

public class NetworkUtils {

    public static String postToDatabase(String passgetID, String sync_url){
        try
        {
            String basketID = passgetID;
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
        return null;
    }

    public static String syncWithBasket (String basket_id, String user_id) {
        try
        {
            String basketID = basket_id;
            URL url = new URL("http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/basket.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"iso-8859-1"));
            String post_data = URLEncoder.encode("basket-id","iso-8859-1")+"="+URLEncoder.encode(basketID,"iso-8859-1")
                    +"&"+URLEncoder.encode("user-id","iso-8859-1") + "=" + URLEncoder.encode(user_id,"iso-8859-1");
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
        return null;
    }

    public static String postUserToDatabase(String uid, String name, String surname, String gender, String bday, String sync_url) {
        try
        {
            URL url = new URL(sync_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"iso-8859-9"));
            String post_data = URLEncoder.encode("user-id","UTF-8")+"="+URLEncoder.encode(uid,"iso-8859-9") +
                    "&"+URLEncoder.encode("user-name","UTF-8")+"="+URLEncoder.encode(name,"iso-8859-9") +
                    "&"+URLEncoder.encode("user-sname","UTF-8")+"="+URLEncoder.encode(surname,"iso-8859-9") +
                    "&"+URLEncoder.encode("user-gender","UTF-8")+"="+URLEncoder.encode(gender,"iso-8859-9") +
                    "&"+URLEncoder.encode("user-bday","UTF-8")+"="+URLEncoder.encode(bday,"iso-8859-9");
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
        return null;
    }

    public static String endShopping (String basket_id, String shopping_id) {
        try
        {
            String basketID = basket_id;
            URL url = new URL("http://dijkstra.ug.bcc.bilkent.edu.tr/~arif.terzioglu/GE401/endShopping.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"iso-8859-1"));
            String post_data = URLEncoder.encode("basket-id","iso-8859-1")+"="+URLEncoder.encode(basket_id,"iso-8859-1")
                    +"&"+URLEncoder.encode("sold-id","iso-8859-1") + "=" + URLEncoder.encode(shopping_id,"iso-8859-1");
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
        return null;
    }
}
