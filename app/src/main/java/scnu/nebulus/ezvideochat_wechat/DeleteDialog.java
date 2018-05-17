package scnu.nebulus.ezvideochat_wechat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Steve_su on 15/05/2018.
 */
public class DeleteDialog extends Dialog {
    private MainActivity context;
    private String ondeleteString;

    public DeleteDialog(MainActivity context, String ondeleteString) {
        super(context);
        this.context = context;
        this.ondeleteString = ondeleteString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_delete);

        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 为按钮绑定点击事件监听器
        Button btn = (Button)findViewById(R.id.btn_yes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_yes:
                        Set<String> set = context.getSharedPreferences("ezWechat", Context.MODE_PRIVATE).getStringSet("contacts", new HashSet<String>());
                        set = new HashSet<String>(set);
                        set.remove(ondeleteString);
                        SharedPreferences.Editor editor = context.getSharedPreferences("ezWechat", 0).edit();
                        editor.putStringSet("contacts", set);
                        editor.apply();
                        context.loadListView(set.toArray(new String[set.size()]));
                        dismiss();



                        break;
                }
            }
        });

        btn = (Button)findViewById(R.id.btn_no);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.setCancelable(true);
    }
}
