package com.example.sanya.dwarvesvsorcs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;


/**
 * Created by sanya on 2017.04.03..
 */

public class Showrules extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        WebView w1 = (WebView) findViewById(R.id.ruletext1);
        String intro = "<p align='justify'>" + getString(R.string.rules1) + "</p>";
        w1.loadData(intro, "text/html", null);

        WebView w2 = (WebView) findViewById(R.id.ruletext2);
        String intro2 = "<p align='justify'>" + getString(R.string.rules2) + "</p>";
        w2.loadData(intro2, "text/html", null);

        WebView w3 = (WebView) findViewById(R.id.ruletext3);
        String intro3 = "<p align='justify'>" + getString(R.string.rules3) + "</p>";
        w3.loadData(intro3, "text/html", null);
    }
}
