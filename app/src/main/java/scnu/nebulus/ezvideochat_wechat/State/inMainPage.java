package scnu.nebulus.ezvideochat_wechat.State;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;


import scnu.nebulus.ezvideochat_wechat.ServiceInfoBundle;
import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 01/05/2018.
 */
public class inMainPage implements State {
    /* the Action in this state
        1. change the Accessibility receiving target(to wechat)
        2. set state
        3. jump to wechat
    * */
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        Log.e("myservice", this.getClass().getName());
        if(context.getServiceInfo().packageNames != null)
            Log.e("myservice", context.getServiceInfo().packageNames[0]);
        else
            Log.e("myservice", "null");

        AccessibilityNodeInfo node = context.getRootInActiveWindow();
        System.out.println("NODE:"+node);
        if(node != null){
            List<AccessibilityNodeInfo> list = node.findAccessibilityNodeInfosByText("微信");
            if(list.size() > 0 ) {
                if(!context.getChatTarget().equals("录制2"))
                    context.setCurState(new initWechat());
                else
                    context.setCurState(new recordPlusButton());
                list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }
}
