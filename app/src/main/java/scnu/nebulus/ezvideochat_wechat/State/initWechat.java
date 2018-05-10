package scnu.nebulus.ezvideochat_wechat.State;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 01/05/2018.
 */
public class initWechat implements State {
    /* the Action in this state -- jump to Wechat MainPage
        1. see which page we are
        2. if main page --- jump to chatting page and set state
            {
                click into every bar until it find the right name
            }
        3. else operate
    * */
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.tencent.mm")){
            Log.e("myservice", this.getClass().getName());
            if(context.getServiceInfo().packageNames != null)
                Log.e("myservice", context.getServiceInfo().packageNames[0]);
            else
                Log.e("myservice", "null");

            AccessibilityNodeInfo root = context.getRootInActiveWindow();

            if(root != null) {
                List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText("通讯录");
                if(list.size() > 0 && list.get(0).getParent() != null && list.get(0).getParent().isClickable()) {
                    System.out.println("通讯录?"+list.get(0).getClassName().toString());
                    list.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    if(context.getChatTarget().equals("录制1"))
                        context.setCurState(new recordListView());
                    else
                        context.setCurState(new intoChattingPage());
                }
                else
                    context.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            }
        }
    }
}
