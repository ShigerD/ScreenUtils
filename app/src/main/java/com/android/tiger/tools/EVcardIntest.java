package com.android.tiger.tools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.tiger.tools.Utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Created by shiger on 2017/12/17.
 */

public class EVcardIntest extends BaseActivity {

    private String TAG = "ChatActivity_";
    private String TARGET_URL = "http://www.baidu.com/";
    private String TARGET_URL_GET = "http://192.168.0.106:8080/AndroidBookJavaWeb/blog/deal_httpclient.jsp?param=get";
    private String TULING_URL = "http://www.tuling123.com/openapi/api?key=21e76bf9a7f2418098dc514619c8bf00&info=你好&userid=1";
    private String HTTP_POST = "http://192.168.0.106:8080/AndroidBookJavaWeb/blog/deal_httpclient.jsp";    //

    private static final String JSON_TEMPLE =
            "{" +
                    "   \"phone\" : [\"12345678\", \"87654321\"]," +
                    "   \"name\" : \"yuanzhifei89\"," +
                    "   \"age\" : 100," +
                    "   \"address\" : { \"country\" : \"china\", \"province\" : \"jiangsu\" }," +
                    "   \"married\" : false" +
                    "}";

    private Handler handler;
    private TextView mResultTextview;
    EditText mContentEt;
    Button mOKbutton;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "-onCreate-");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mResultTextview = findViewById(R.id.chat_text);
        mContentEt = findViewById(R.id.content);

// fail
        result = HttpUtils.sendEvPost(HTTP_POST, mContentEt.getText().toString());
        result = result.replace("null","");
        Log.d(TAG, "result--- " + result);

        mOKbutton = findViewById(R.id.ok_button);
        mOKbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        Log.d(TAG, "run--2   ");
//                        result = HttpUtils.sendTuLinPost(HTTP_POST);
                        result = HttpUtils.sendTuLinPost(HTTP_POST, mContentEt.getText().toString(), "2");
                        result = result.replace("null","");
                        Log.d(TAG, "result--- " + result);
                        Message m = handler.obtainMessage(); // 获取一个Message
                        handler.sendMessage(m); // 发送消息
                    }
                }).start(); // 开启线程
            }
        });



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (result != null) {

                    try {
                        JSONTokener jsonParser = new JSONTokener(result);
//                        JSONTokener jsonParser = new JSONTokener("{\"code\":100000,\"text\":\"我很好，你也要好好的\"}");
                        JSONObject person = (JSONObject) jsonParser.nextValue();
                        int code = person.getInt("code");
                        String answer = person.getString("text");
                        Log.d(TAG, "JSON--"
                                + "code==" + person.getInt("code")
                                + "text==" + person.getString("text")
                        );
                        mResultTextview.setText(answer);

                    } catch (JSONException ex) {
                        // 异常处理代码
                        ex.printStackTrace();
                    }
                    Log.d(TAG, "hand result--2 ");
                    super.handleMessage(msg);
                }
            }
        };


  /*      try {
            Log.d(TAG, "JSON == " + JSON_TEMPLE);
            JSONTokener jsonParser = new JSONTokener("{\"code\":100000,\"text\":\"我很好，你也要好好的\"}");
            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
            // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
            JSONObject person = (JSONObject) jsonParser.nextValue();
            // 接下来的就是JSON对象的操作了
            Log.d(TAG, "JSON--"
                    + "code==" + person.getInt("code")
                    + "text==" + person.getString("text")
            );
        } catch (JSONException ex) {
            // 异常处理代码
            ex.printStackTrace();
        }*/

        Log.d(TAG, "+onCreate+");
    }


}
