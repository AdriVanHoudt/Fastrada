package be.fastrada.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import be.fastrada.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings extends Activity {
    private List<Map<String, String>> settingsList = new ArrayList<Map<String, String>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final Context context = this.getBaseContext();

        initList();

        final SimpleAdapter simpleAdpt = new SimpleAdapter(this, settingsList, android.R.layout.simple_list_item_1, new String[]{"setting"}, new int[]{android.R.id.text1});

        final ListView lv = (ListView) findViewById(R.id.settingsListView);
        lv.setAdapter(simpleAdpt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
                Intent intent = null;

                if (id == 0) {
                    intent = new Intent(context, UiConfig.class);
                } else if (id == 1) {
                    intent = new Intent(context, GeneralConfig.class);
                }

                startActivity(intent);
            }
        });
    }

    private void initList() {
        settingsList.add(createSetting("setting", getString(R.string.uiConfiguration)));
        settingsList.add(createSetting("setting", getString(R.string.genConfiguration)));
    }

    private Map<String, String> createSetting(String key, String name) {
        final HashMap<String, String> setting = new HashMap<String, String>();
        setting.put(key, name);

        return setting;
    }
}