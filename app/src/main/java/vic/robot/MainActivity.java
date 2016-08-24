package vic.robot;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vic.bean.ChatMessage;
import vic.utils.HttpUtils;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatMessage fromMessage = (ChatMessage) msg.obj;
            mDatas.add(fromMessage);
            mAdapter.notifyDataSetChanged();
        }
    };

    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mDatas;


    private EditText mInputMsg;
    private Button mSengMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initDatas();

        initListener();
    }

    private void initListener() {
        mSengMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg.trim())) {
                } else {
                    Log.d("tag","111");
                    ChatMessage toMessage = new ChatMessage();
                    toMessage.setDate(new Date());
                    toMessage.setMsg(toMsg);
                    toMessage.setType(ChatMessage.Type.OUTCOMING);
                    mDatas.add(toMessage);
                    mAdapter.notifyDataSetChanged();
                    mInputMsg.setText("");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Log.d("tag","222");
                            ChatMessage fromMessage = HttpUtils.sendMessage(toMsg.trim());
                            Message message = Message.obtain();
                            message.obj = fromMessage;
                            handler.sendMessage(message);
                        }
                    }.start();
                }
            }
        });
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        mDatas.add(new ChatMessage("Hello, this is xiaoxiao", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(this, mDatas);
        mMsgs.setAdapter(mAdapter);
    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.id_input_msg);
        mSengMsg = (Button) findViewById(R.id.id_send_msg);
    }
}
