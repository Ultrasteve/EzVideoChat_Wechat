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
 * Created by Steve_su on 08/05/2018.
 */
public class recordListView implements State {

    /* 在进入这个state之前自动跳到联系人界面
        监控listview source的事件，记录id
        reset state

        需要添加toast提示 --- 请开始拖动，内部检测是否已经储存成功，储存成功后 toast显示成功 reset state
    * */
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getSource() != null && event.getSource().getPackageName().equals("com.tencent.mm")) {
            final Context innerContext = context;
            String fileName = null;
            Handler handler;

            if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED && event.getSource().getClassName().equals("android.widget.ListView")){
                AccessibilityNodeInfo ani = event.getSource();
                ani.refresh();
                SharedPreferences.Editor editor = context.getMsp().edit();
                editor.putString("listviewid", ani.getViewIdResourceName());
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

                if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                    handler=new Handler(Looper.getMainLooper());
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(innerContext, "请滚动联系人栏", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

        }
    }
}
