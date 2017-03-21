package com.example.alice.unboundedservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button start = (Button) findViewById(R.id.bStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getBaseContext(), UnboundedService.class));
            }
        });

        Button stop = (Button) findViewById(R.id.bStop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), UnboundedService.class));
            }
        });
    }
}
