package scnu.nebulus.ezvideochat_wechat.State;

import android.view.accessibility.AccessibilityEvent;

import scnu.nebulus.ezvideochat_wechat.ServiceInfoBundle;
import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 04/05/2018.
 */
public class missionComplete implements State {
    @Override
    public void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("com.tencent.mm")) {
            context.setCurState(new insideMyApp());
            context.setServiceInfo(new ServiceInfoBundle().setPackage(null));
            context.setChatTarget(null);
        }
    }
}
