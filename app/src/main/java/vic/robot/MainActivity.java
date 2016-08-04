package vic.robot;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import vic.utils.HttpUtils;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            System.out.println(data.get("msg"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread() {
            @Override
            public void run() {
                super.run();
                String result = HttpUtils.doGet("给我讲个笑话");
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("msg", result);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }.start();

    }
}
