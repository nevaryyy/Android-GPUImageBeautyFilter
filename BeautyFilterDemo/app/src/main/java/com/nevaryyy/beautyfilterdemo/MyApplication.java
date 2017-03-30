package com.nevaryyy.beautyfilterdemo;

import android.app.Application;

import com.nevaryyy.beautyfilterdemo.permission.PermissionEnZhXMLParserHandler;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @author yuejinyang
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        loadPermissionEnZh();
    }

    private void loadPermissionEnZh() {
        try {
            InputStream inputStream = getAssets().open("permission_en_zh.xml");
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            PermissionEnZhXMLParserHandler parserHandler = new PermissionEnZhXMLParserHandler();

            parser.parse(inputStream, parserHandler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
