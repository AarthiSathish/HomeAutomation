package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Math.random;

public class Admin extends AppCompatActivity {
    EditText useremail;
    Button done;
    Editable email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        useremail=(EditText)findViewById(R.id.email);
        done=(Button)findViewById(R.id.submit);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=useremail.getText();
                if(email.toString().trim().matches(emailPattern)){
                    AddtoDB(email.toString());
                    updateUI();
                }
                else{
                    Toast.makeText(Admin.this,"Not a valid email id", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void AddtoDB(String email) {
        String path="QWERTY12345/Users/";
        dbRef=dbRef.child(path);
        String k=dbRef.push().getKey();
        dbRef.child(k).setValue(email);

    }

    private void updateUI() {

            Intent intent = new Intent(Admin.this,Register.class);
            startActivity(intent);

    }

}
