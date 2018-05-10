package scnu.nebulus.ezvideochat_wechat;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import scnu.nebulus.ezvideochat_wechat.State.State;
import scnu.nebulus.ezvideochat_wechat.State.insideMyApp;

@TargetApi(Build.VERSION_CODES.DONUT)
public class WechatAutoService extends AccessibilityService{

    private State curState = null;

    private String chatTarget = "录制1";

    protected void onServiceConnected() {
        setCurState(new insideMyApp());
        setServiceInfo(new ServiceInfoBundle().setPackage(null));

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e("wechatservice", event.toString());
        this.getCurState().doAction(this, event);
    }

    @Override
    public void onInterrupt() {

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


    public String getChatTarget() {
        return this.chatTarget;
    }
}
