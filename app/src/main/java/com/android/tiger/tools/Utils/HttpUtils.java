package com.android.tiger.tools.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import static android.content.ContentValues.TAG;

/**
 * Created by shiger on 2017/12/17.
 */

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    public static String send() {
        String target="";
        String result = null;
        target = "https://www.baidu.com/";
        URL url;
        try {
            url = new URL(target);
            HttpURLConnection urlConn = (HttpURLConnection) url
                    .openConnection();	//创建一个HTTP连接
            InputStreamReader in = new InputStreamReader(
                    urlConn.getInputStream()); // 获得读取的内容
            BufferedReader buffer = new BufferedReader(in); // 获取输入流对象
            String inputLine = null;
            //通过循环逐行读取输入流中的内容
            while ((inputLine = buffer.readLine()) != null) {
                result += inputLine + "\n";
            }
            in.close();	//关闭字符输入流对象
            urlConn.disconnect();	//断开连接
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String executeHttpGet(String targeturl) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(targeturl);
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            Log.d(TAG,"result " + result);
        } catch (Exception e) {
            Log.w(TAG,e.toString());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.w(TAG,e.toString());
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static String executeHttpPost(String targeturl) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(targeturl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");
            DataOutputStream dop = new DataOutputStream(
                    connection.getOutputStream());
            dop.writeBytes("token=alexzhou");
            dop.flush();
            dop.close();

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static String executeGet(String url) {
        String result = null;
        BufferedReader reader = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            reader = new BufferedReader(new InputStreamReader(response
                    .getEntity().getContent()));
            StringBuffer strBuffer = new StringBuffer("");
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String sendHttpGet(String target) {
        String result = null;
        DefaultHttpClient httpclient = new DefaultHttpClient();//创建HttpClient对象
        HttpGet httpRequest = new HttpGet(target);	//创建HttpGet连接对象
        HttpResponse httpResponse;
        try {
            httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串
            }else{
                result="请求失败！";
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendHttpPost(String target) {
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();	//创建HttpClient对象
        HttpPost httpRequest = new HttpPost(target);	//创建HttpPost对象
        //将要传递的参数保存到List集合中
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", "post"));	//标记参数
        params.add(new BasicNameValuePair("nickname", "is nickname"));	//昵称
        params.add(new BasicNameValuePair("content", "is content"));	//内容
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //设置编码方式
            HttpResponse httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){	//如果请求成功
                result += EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串

            }else{
                result = "请求失败！";
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();	//输出异常信息
        } catch (ClientProtocolException e) {
            e.printStackTrace();	//输出异常信息
        } catch (IOException e) {
            e.printStackTrace();	//输出异常信息
        }
        return result;
    }

    public static String sendTuLinPost(String target) {
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();	//创建HttpClient对象
        HttpPost httpRequest = new HttpPost("http://www.tuling123.com/openapi/api");	//创建HttpPost对象
        //将要传递的参数保存到List集合中
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", "post"));	//标记参数
        params.add(new BasicNameValuePair("key", "21e76bf9a7f2418098dc514619c8bf00"));	//昵称
        params.add(new BasicNameValuePair("info", "你好"));	//内容
        params.add(new BasicNameValuePair("userid", "1"));
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //设置编码方式
            HttpResponse httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){	//如果请求成功
                result += EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串
            }else{
                result = "请求失败！";
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();	//输出异常信息
        } catch (ClientProtocolException e) {
            e.printStackTrace();	//输出异常信息
        } catch (IOException e) {
            e.printStackTrace();	//输出异常信息
        }
        return result;
    }

    public static String sendTuLinPost(String target, String content, String usrID) {
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();	//创建HttpClient对象
        HttpPost httpRequest = new HttpPost("http://www.tuling123.com/openapi/api");	//创建HttpPost对象
        //将要传递的参数保存到List集合中
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", "post"));	//标记参数
        params.add(new BasicNameValuePair("key", "21e76bf9a7f2418098dc514619c8bf00"));	//昵称
        params.add(new BasicNameValuePair("info", content));	//内容
        params.add(new BasicNameValuePair("userid", usrID));
        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //设置编码方式
            HttpResponse httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){	//如果请求成功
                result += EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串
            }else{
                result = "请求失败！";
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();	//输出异常信息
        } catch (ClientProtocolException e) {
            e.printStackTrace();	//输出异常信息
        } catch (IOException e) {
            e.printStackTrace();	//输出异常信息
        }
        return result;
    }


    public static String sendEvPost(String target, String content) {
        String result = null;
        HttpClient httpclient = new DefaultHttpClient();	//创建HttpClient对象
        HttpPost httpRequest = new HttpPost("http://139.224.37.24:8102/evcard-svm/api/activityList");	//创建HttpPost对象
        //将要传递的参数保存到List集合中
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("param", "post"));	//标记参数
        params.add(new BasicNameValuePair("terminalId", "123456"));	//昵称

        try {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //设置编码方式
            HttpResponse httpResponse = httpclient.execute(httpRequest);	//执行HttpClient请求
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){	//如果请求成功
                result += EntityUtils.toString(httpResponse.getEntity());	//获取返回的字符串
            }else{
                result = "请求失败！";
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();	//输出异常信息
        } catch (ClientProtocolException e) {
            e.printStackTrace();	//输出异常信息
        } catch (IOException e) {
            e.printStackTrace();	//输出异常信息
        }
        return result;
    }

}
