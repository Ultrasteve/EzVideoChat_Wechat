package scnu.nebulus.ezvideochat_wechat.State;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 04/05/2018.
 */
public class inDetailPage implements State {
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.tencent.mm")) {
            AccessibilityNodeInfo root = context.getRootInActiveWindow();

            List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText("发消息");
            if (list.size() > 0) {
                list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                context.setCurState(new inChattingPage());
            }

        }
    }
}
