package scnu.nebulus.ezvideochat_wechat;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by Steve_su on 04/05/2018.
 */
public class ServiceInfoBundle extends AccessibilityServiceInfo {
    public ServiceInfoBundle() {
        this.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        this.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        this.notificationTimeout = 100;
        this.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
    }

    public AccessibilityServiceInfo setPackage(String string) {
        if(string != null)
            this.packageNames = new String[]{string};
        else
            this.packageNames = null;
        return this;
    }
}
