package scnu.nebulus.ezvideochat_wechat;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity {

    private CreateUserDialog createUserDialog;
    private String ondeleteString;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences("ezWechat", Context.MODE_PRIVATE);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });
        findViewById(R.id.record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecordActivity.class));
            }
        });
        if(!isStartAccessibilityService(this, getPackageName()+"/."+"WechatAutoService")) {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }

        loadListView(getDataSource());
    }

    public void loadListView(String[] s) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, s);
        ListView listView = (ListView) findViewById(R.id.list_view_contact);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onclick");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ondeleteString = ((TextView)view).getText().toString();
                System.out.println(12);
                showDeleteDialog();
                return true;
            }
        });
    }

    private View.OnClickListener monclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_save_pop:

                    String name = createUserDialog.text_name.getText().toString().trim();

                    loadListView(insertData(name));
                    createUserDialog.dismiss();
                    break;
            }
        }
    };


    private void showEditDialog() {
        createUserDialog = new CreateUserDialog(this, monclicklistener);
        createUserDialog.show();

    }

    private void showDeleteDialog() {
        DeleteDialog deleteDialog = new DeleteDialog(this, ondeleteString);
        deleteDialog.show();
    }

    private String[] getDataSource() {
        Set<String> set = mSharedPreferences.getStringSet("contacts", new HashSet<String>());
        return set.toArray(new String[set.size()]);
    }

    private String[] insertData(String s) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Set<String> set = new HashSet<String>(Arrays.asList(getDataSource()));
        set.add(s);
        editor.putStringSet("contacts", set);
        editor.apply();
        System.out.println(set.size());
        return set.toArray(new String[set.size()]);
    }

    private static boolean isStartAccessibilityService(Context context, String name){
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_SPOKEN);
        for (AccessibilityServiceInfo info : serviceInfos) {
            Log.e("myservice", name);
            String id = info.getId();
            if (id.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
