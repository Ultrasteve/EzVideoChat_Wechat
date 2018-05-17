package scnu.nebulus.ezvideochat_wechat;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;


import scnu.nebulus.ezvideochat_wechat.State.State;
import scnu.nebulus.ezvideochat_wechat.State.insideMyApp;

@TargetApi(Build.VERSION_CODES.DONUT)
public class WechatAutoService extends AccessibilityService{

    private State curState = null;

    private String chatTarget = null;

    private SharedPreferences msp;

    private String listviewID = null;

    private String plusbuttonID = null;

    protected void onServiceConnected() {
        setCurState(new insideMyApp());
        setServiceInfo(new ServiceInfoBundle().setPackage(null));
        setChatTarget(null);

        loadID();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("myservice", event.toString());
        if(!ifNoServiceCase(event) && !ifWithoutIDCase(event)){
            ifSetChatTargetCase(event);
            this.getCurState().doAction(this, event);
        }

    }

    @Override
    public void onInterrupt() {
        setCurState(new insideMyApp());
        setServiceInfo(new ServiceInfoBundle().setPackage(null));
        setChatTarget(null);
    }

    private boolean ifNoServiceCase(AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.android.settings") && event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            System.out.println("NO SERVICE NOW!");
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            return true;
        }
        return false;
    }

    private boolean ifSetChatTargetCase(AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("scnu.nebulus.ezvideochat_wechat") && event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED && event.getClassName().toString().equals("android.widget.TextView")) {
            System.out.println("SETTING CHAT TARGET NOW!");
            setCurState(new insideMyApp());
            this.chatTarget = event.getText().toString().replace("[","").replace("]","");
            return true;
        }
        return false;
    }

    private boolean ifWithoutIDCase(AccessibilityEvent event) {
        if(listviewID == null || plusbuttonID == null) {
            System.out.println("NO ID NOW!");
            if(event.getPackageName().toString().equals("scnu.nebulus.ezvideochat_wechat") && event.getClassName().toString().equals("android.widget.TextView") && event.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {
                if(listviewID == null)
                    System.out.println("listview");
                if(plusbuttonID == null)
                    System.out.println("plusbutton");
                Handler handler=new Handler(Looper.getMainLooper());
                handler.post(new Runnable(){
                    public void run(){
                        Toast.makeText(WechatAutoService.this, "请录制id", Toast.LENGTH_LONG).show();
                    }
                });
            }
            if((event.getText() != null && event.getText().toString().contains("录制")) || (chatTarget != null && chatTarget.contains("录制")))
                return false;
            return true;
        }
        return false;

    }

    public void setCurState(State state) {
        this.curState = state;
    }


    public State getCurState() {
        return this.curState;
    }

    public void setChatTarget(String target) {
        this.chatTarget = target;
    }

    public SharedPreferences getMsp() {return msp;}

    public String getListviewID() {return listviewID;}

    public String getPlusbuttonID() {return plusbuttonID;}

    public void loadID() {
        if(msp == null)
            msp = getSharedPreferences("ezWechat", Context.MODE_PRIVATE);
        listviewID = msp.getString("listviewid", null);
        plusbuttonID = msp.getString("plusbuttonid", null);
    }

    public String getChatTarget() {
        return this.chatTarget;
    }
}
