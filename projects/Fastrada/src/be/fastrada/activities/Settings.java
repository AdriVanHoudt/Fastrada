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

/**
 * @author: Shana Steenssens
 * @version: 1.0 28/02/14 16:10
 */
public class Settings extends Activity {
    private List<Map<String, String>> settingsList = new ArrayList<Map<String, String>>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final Context context = this.getBaseContext();

        initList();

        SimpleAdapter simpleAdpt = new SimpleAdapter(this, settingsList, android.R.layout.simple_list_item_1, new String[]{"setting"}, new int[]{android.R.id.text1});

        ListView lv = (ListView) findViewById(R.id.settingsListView);
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
        settingsList.add(createSetting("setting", "UI Configiguration"));
        settingsList.add(createSetting("setting", "General Configiguration"));
    }

    private Map<String, String> createSetting(String key, String name) {
        HashMap<String, String> setting = new HashMap<String, String>();
        setting.put(key, name);
        return setting;
    }
}