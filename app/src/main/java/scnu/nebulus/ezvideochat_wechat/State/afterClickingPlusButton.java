package scnu.nebulus.ezvideochat_wechat.State;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 04/05/2018.
 */
public class afterClickingPlusButton implements State {
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.tencent.mm")) {
            Log.e("myservice", this.getClass().getName());
            Log.e("myservice", event.toString());
            if(event.getText() != null && event.getText().toString().contains("提示, 在移动网络环境下会影响视频和音频质量")) {
                AccessibilityNodeInfo root = context.getRootInActiveWindow();
                if(root != null) {
                    List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText("确定");
                    if(list.size() > 0) {
                        list.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        context.setCurState(new missionComplete());
                    }
                }
            }
            else if(event.getText() != null && event.getText().toString().contains("提示, 当前网络不可用")) {
                context.setCurState(new missionComplete());
            }
            else if(event.getClassName().toString().equals("com.tencent.mm.plugin.voip.ui.VideoActivity")) {
                context.setCurState(new missionComplete());
            }
            else {
                AccessibilityNodeInfo root = context.getRootInActiveWindow();
                if(root != null) {
                    List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText("视频通话");
                    if(list.size() > 0) {
                        list.get(0).getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }

    }
}
