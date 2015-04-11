package com.zeroone_creative.basicapplication.controller.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shunhosaka on 2015/04/10.
 */
public class AssetUtil {

    /**
     * Get json from assets/json.
     * @param path
     * @param context
     * @return
     */
    public static String jsonAssetReader(String path, Context context) {
        AssetManager assetManager = context.getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String _line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((_line = reader.readLine()) != null) {
                stringBuilder.append(_line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
