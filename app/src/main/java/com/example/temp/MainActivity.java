package com.example.temp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAsync(this);
        getInstalledApps(false);
    }

    public static Context context;


    public static void startAsync(Context con)
    {
        try {
            context = con;
        }catch (Exception ex){
            startAsync(con);
        }

    }
    public static JSONObject getInstalledApps(boolean getSysPackages) {


        JSONArray apps = new JSONArray();

        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);

        for(int i=0;i < packs.size();i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue ;
            }
            try {
                JSONObject newInfo = new JSONObject();
                String appname = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
                String pname = p.packageName;
                String versionName = p.versionName;
                int versionCode = p.versionCode;

                newInfo.put("appName",appname);
                newInfo.put("packageName",pname);
                newInfo.put("versionName",versionName);
                newInfo.put("versionCode",versionCode);
                apps.put(newInfo);
            }catch (JSONException e) {}
        }
        Log.d("heyyy",apps.toString());


        JSONObject data = new JSONObject();
        try {
            data.put("apps", apps);
        }catch (JSONException e) {}

        return data;
    }
}
