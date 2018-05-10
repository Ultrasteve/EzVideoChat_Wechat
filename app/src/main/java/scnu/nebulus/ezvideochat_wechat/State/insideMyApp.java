package scnu.nebulus.ezvideochat_wechat.State;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 01/05/2018.
 */
public class insideMyApp implements State {
    /* the Action in this state
        1. change the Accessibility receiving target(to android.api)
        2. set session and name
        3. set state

        or

        receiving notification
        if phonecall page
            press on button
    * */
    @Override
    public synchronized void doAction(WechatAutoService context, AccessibilityEvent event) {
        if(event.getPackageName().toString().equals("scnu.nebulus.ezvideochat_wechat") && context.getChatTarget() != null) {
            Log.e("myservice", this.getClass().getName());

            //接收通知状态
            //正常状态

            context.setCurState(new inMainPage());
            context.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
        }

    }
}
