package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Admin_login extends AppCompatActivity {
    Button done;
    EditText pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        done=(Button)findViewById(R.id.done);
        pswd=(EditText)findViewById(R.id.pswd);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pswd.getText().toString().equals("indian01")){
                    Intent intent = new Intent(Admin_login.this,Admin.class);
                    startActivity(intent);
                }
            }
        });
    }
}
