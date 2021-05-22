package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private static final int ARC_SIGN_IN = 2;
    SignInButton signin;
    Button signout;
    GoogleSignInClient client;
    String owner;
    String TAG="Register";
    FirebaseAuth mauth;
    Button admin;
    boolean isnotauth=true;
    ArrayList<String> users=new ArrayList<String>();
    TextView name;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        signin=(SignInButton)findViewById(R.id.sgnin);
        name=(TextView)findViewById(R.id.Name);
        admin=(Button)findViewById(R.id.admin);
        signout=(Button)findViewById(R.id.signout);
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SignStatus", "Out").equals("Out"))
            signout.setVisibility(View.INVISIBLE);
        String ad=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Admin", "N");
        if (ad.equals("Y"))
            admin.setVisibility(View.VISIBLE);
        else
            admin.setVisibility(View.INVISIBLE);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client= GoogleSignIn.getClient(this,gso);
        String stat=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SignStatus", "Out");
        String want=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Want", "N");
        if(stat.equals("In") && want.equals("N")){
            name.setText("Hi,"+GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getDisplayName());
            updateUI(1);
        }
        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signin();

            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                revokeAccess();
                name.setText("");
                signout.setVisibility(View.INVISIBLE);
                admin.setVisibility(View.INVISIBLE);
                PreferenceManager.getDefaultSharedPreferences(Register.this).edit().putString("SignStatus", "Out").apply();
                PreferenceManager.getDefaultSharedPreferences(Register.this).edit().putString("Admin", "N").apply();

            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Admin.class);
                startActivity(intent);
            }
        });

    }


    protected void onResume() {

        super.onResume();
        String stat=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SignStatus", "Out");
        if(GoogleSignIn.getLastSignedInAccount(getApplicationContext())!=null)
            name.setText("Hi,"+GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getDisplayName());
        String want=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Want", "N");
        if(stat.equals("In") && want.equals("N")){
            updateUI(1);
        }
    }

    public void onBackPressed(){
        String stat=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("SignStatus", "Out");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Want", "N").apply();
        if(stat.equals("In")){
            updateUI(1);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(Register.this).edit().putString("Want", "N").apply();
    }

    private void signin(){
        Intent signinIntent=client.getSignInIntent();
        startActivityForResult(signinIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSigninResult(task);
        }
    }



    private void handleSigninResult( Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc= completedTask.getResult(ApiException.class);
            //Toast.makeText(Register.this,"Sign In Successful",Toast.LENGTH_SHORT).show();
            name.setText("Hi,"+acc.getDisplayName());
            FirebasegoogleAuth(acc);
            //FirebasegoogleAuth(acc);
        }
        catch (ApiException e) {
            //Toast.makeText(Register.this,"Sign In Failed",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            FirebasegoogleAuth(null);
            updateUI(0);
        }
    }

    private void FirebasegoogleAuth(final GoogleSignInAccount acc) {
        signout.setVisibility(View.VISIBLE);
        AuthCredential cred= GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        final ArrayList<String> users= new ArrayList<String>();
        mauth.signInWithCredential(cred).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dbRef.child("Devices/QWERTY12345/Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        owner= (String) snapshot.getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dbRef.child("Devices/QWERTY12345/Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        owner= (String) snapshot.getValue();
                        if(owner!=null && owner.equals(acc.getEmail())){
                            admin.setVisibility(View.VISIBLE);
                            PreferenceManager.getDefaultSharedPreferences(Register.this).edit().putString("Admin", "Y").apply();
                            isnotauth=false;
                            if(isnotauth==false)
                                updateUI(1);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                    final String useremail = acc.getEmail();
                    final int[] s = {0};
                    dbRef.child("QWERTY12345/Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                if(child.getValue().equals(useremail)){
                                    isnotauth=false;
                                    break;
                                }
                            }
                            if(isnotauth){
                                Toast.makeText(Register.this,"You are not authenticated!!",Toast.LENGTH_SHORT).show();
                                revokeAccess();

                            }
                            else{
                                updateUI(1);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


        });

    }


    private void updateUI(int n) {

        if(n!=0){
            signout.setVisibility(View.VISIBLE);
            PreferenceManager.getDefaultSharedPreferences(Register.this).edit().putString("SignStatus", "In").apply();
            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void revokeAccess() {
        client.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        //Toast.makeText(Register.this,"Signed out!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void signOut() {
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}
