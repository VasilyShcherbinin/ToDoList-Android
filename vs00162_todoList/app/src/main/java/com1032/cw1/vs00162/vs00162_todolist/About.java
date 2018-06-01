package com1032.cw1.vs00162.vs00162_todolist;

import android.app.Activity;
import android.os.Bundle;

/**
 * Responsible for launching the About window on ButtonClick.
 */
public class About extends Activity {

    /**
     * Called when About is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }
}
