package com.martin.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @author majingze
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.to_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputPayPasswordDialog.newInstance().show(getFragmentManager(), "22");
            }
        });
    }
}
