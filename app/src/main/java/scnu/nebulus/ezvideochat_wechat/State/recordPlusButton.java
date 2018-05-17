package scnu.nebulus.ezvideochat_wechat.State;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 02/05/2018.
 */
public class recordPlusButton implements State {
    /* 先自动进入微信，之后提供指示，让用户点击imagebutton
    * */
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getSource() != null && event.getSource().getPackageName().equals("com.tencent.mm")) {
            Log.e("myservice", this.getClass().getName());
            final Context innerContext = context;
            Handler handler;
            if(event.getContentDescription() != null && event.getContentDescription().equals("更多功能按钮，已折叠")) {
                System.out.println("进来啦");
                AccessibilityNodeInfo ani = event.getSource();
                ani.refresh();
                SharedPreferences.Editor editor = context.getMsp().edit();
                editor.putString("plusbuttonid", ani.getViewIdResourceName());
                final String s = ani.getViewIdResourceName();
                if(editor.commit()) {
                    handler=new Handler(Looper.getMainLooper());
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(innerContext, "录制成功"+s, Toast.LENGTH_LONG).show();
                        }
                    });
                    context.loadID();
                    context.setCurState(new missionComplete());
                }
            }
            else {
                Log.e("myservice", "what");
                Log.e("myservice", this.getClass().getName());
                Log.e("myservice", event.toString());
                if(event.getContentDescription() != null && event.getContentDescription().toString().contains("当前所在页面,与")) {
                    handler=new Handler(Looper.getMainLooper());
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(innerContext, "请点击进入任意联系人界面或点击'+'按钮,之后点击一次回退按钮", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    }
}
