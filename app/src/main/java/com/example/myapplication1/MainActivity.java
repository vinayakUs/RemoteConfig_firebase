package com.example.myapplication1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anther method
        Map<String,Object> map =new HashMap<>();
        map.put("text_str","Welcome to the app");
        map.put("text_size",14);
        //end
        button = findViewById(R.id.b1);
        textView= findViewById(R.id.t1);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());
        firebaseRemoteConfig.setDefaults(R.xml.default_map);
        firebaseRemoteConfig.setDefaults(map);
        /*
        Setting color, size and string for TextView using parameters returned from
        remote config server
         */

        textView.setTextSize((float) firebaseRemoteConfig.getValue("text_size").asDouble());
        textView.setText(firebaseRemoteConfig.getString("text_str"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 This will initiate fetching of parameters. We have set the expiry time as 0
                 which will ensure we get fresh parameters every time
                 */
                firebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Activated", Toast.LENGTH_SHORT).show();
                            firebaseRemoteConfig.activateFetched();
                        }else {
                            Toast.makeText(MainActivity.this, "Not Activated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    void ft(){

    }
}