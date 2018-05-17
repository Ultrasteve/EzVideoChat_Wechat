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


            AccessibilityNodeInfo root = context.getRootInActiveWindow();
            System.out.println(context.getListviewID());
            List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByViewId(context.getListviewID());
            if(list.size() > 0) {
                List<AccessibilityNodeInfo> list2 = root.findAccessibilityNodeInfosByText(context.getChatTarget());
                System.out.println(list2.size());
                if(list2.size() > 0) {
                    if(list2.get(0).getParent() != null) {
                        list2.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        context.setCurState(new inDetailPage());
                    }
                    else if(list2.get(0).getParent() == null && list2.size() > 1) {
                        list2.get(1).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        context.setCurState(new inDetailPage());
                    }
                    else
                        list.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);

                }
                else
                    list.get(0).performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
        }

    }
}
