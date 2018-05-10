package scnu.nebulus.ezvideochat_wechat.State;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 04/05/2018.
 */
public class intoChattingPage implements State {
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.tencent.mm")) {
            Log.e("myservice", this.getClass().getName());
            if(context.getServiceInfo().packageNames != null)
                Log.e("myservice", context.getServiceInfo().packageNames[0]);
            else
                Log.e("myservice", "null");

            AccessibilityNodeInfo root = context.getRootInActiveWindow();
            //到时候需要动态获取id，可能涉及到异步操作, miui -- com.tencent.mm:id/j8 emui -- com.tencent.mm:id/iq
            List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/j8");
            if(list.size() > 0) {
                List<AccessibilityNodeInfo> list2 = root.findAccessibilityNodeInfosByText(context.getChatTarget());
                System.out.println(list2.size());
                if(list2.size() > 0) {
                    if(list2.get(0).getParent() != null) {
                        list2.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        context.setCurState(new inDetailPage());
                    }
                    if(list2.get(0).getParent() == null && list2.size() > 1) {
                        list2.get(1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        context.setCurState(new inDetailPage());
                    }
                }
                else
                    list.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
        }

    }
}
