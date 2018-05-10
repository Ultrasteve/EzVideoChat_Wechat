package scnu.nebulus.ezvideochat_wechat.State;

import android.view.accessibility.AccessibilityEvent;

import scnu.nebulus.ezvideochat_wechat.WechatAutoService;

/**
 * Created by Steve_su on 01/05/2018.
 */
public interface State {
    public void doAction(WechatAutoService context, AccessibilityEvent event);
}
