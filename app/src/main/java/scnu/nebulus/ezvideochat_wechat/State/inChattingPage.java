package scnu.nebulus.ezvideochat_wechat.State;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 01/05/2018.
 */
public class inChattingPage implements State {
    /* the Action in this state
        1. press "+" button
        2. press chat button
        3. init Accessibility parameter and session
    * */
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.tencent.mm")) {
            AccessibilityNodeInfo root = context.getRootInActiveWindow();
            List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(context.getPlusbuttonID());
            if(list.size() > 0) {
                list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);

                context.setCurState(new afterClickingPlusButton());

            }

        }
    }
}
