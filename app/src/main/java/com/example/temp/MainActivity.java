package com.example.temp;

import android.os.Bundle;
import org.json.JSONArray;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.Activity;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        walk("/storage/emulated/0/Download");
    }


    public JSONArray walk(String path){

        // Read all files sorted into the values-array
        JSONArray values = new JSONArray();
        File dir = new File(path);
        if (!dir.canRead()) {
            Log.d("cannot","inaccessible");
            try {
                JSONObject errorJson = new JSONObject();
                errorJson.put("type", "error");
                errorJson.put("error", "Denied");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        File[] list = dir.listFiles();
        try {
            if (list != null) {
                JSONObject parenttObj = new JSONObject();
                parenttObj.put("name", "../");
                parenttObj.put("isDir", true);

                parenttObj.put("path", dir.getParent());
                values.put(parenttObj);
                for (File file : list) {
                    if (!file.getName().startsWith(".")) {
                        JSONObject fileObj = new JSONObject();
                        fileObj.put("name", file.getName());
                        fileObj.put("isDir", file.isDirectory());
                        fileObj.put("path", file.getAbsolutePath());
                        values.put(fileObj);
                        Log.d("Address",file.getAbsolutePath());
                        Log.d("Address",file.getName());
                        Log.d("Address",file.getParent());
//                        Log.d("Address",file.getParent());
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return values;
    }

    public static void downloadFile(String path){
        if (path == null)
            return;

        File file = new File(path);

        if (file.exists()){

            int size = (int) file.length();
            byte[] data = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(data, 0, data.length);
                JSONObject object = new JSONObject();
                object.put("type","download");
                object.put("name",file.getName());
                object.put("buffer" , data);
                buf.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}