package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket btsocket=null;
    Set<BluetoothDevice> pairedDevices;
    static final UUID myuid=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //TinyDB tinydb = new TinyDB(MainActivity.this);
    String address,name;
    Button d1,d3o,d3f,d4o,d4f,d5o,d5f,btcnt,d2,d6o,d6f,btallo,btallf;
    private static final int REQUEST_ENABLE_BT=0;
    private static final int REQUEST_DISCOVER_BT=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String d1name=PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("d1", "BT1");
        d1=(Button)findViewById(R.id.d1);
        d1.setText(d1name);
        String d2name = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("d2", "BT2");
        d2=(Button)findViewById(R.id.bt2);
        d2.setText(d2name);
        String d3name=PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("d3", "S1");
        d3o=(Button)findViewById(R.id.d3o);
        String n=d3name+"-ON";
        d3o.setText(n);
        d3f=(Button)findViewById(R.id.d3f);
        n=d3name+"-OFF";
        d3f.setText(n);
        String d4name =PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("d4", "S2");
        d4o=(Button)findViewById(R.id.d4o);
        n=d4name+"-ON";
        d4o.setText(n);
        d4f=(Button)findViewById(R.id.d4f);
        n=d4name+"-OFF";
        d4f.setText(n);
        String d5name = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("d5", "S3");
        d5o=(Button)findViewById(R.id.d5o);
        n=d5name+"-ON";
        d5o.setText(n);
        d5f=(Button)findViewById(R.id.d5f);
        n=d5name+"-OFF";
        d5f.setText(n);
        d6o=(Button)findViewById(R.id.d6o);
        String d6name = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("d6", "S4");
        n=d6name+"-ON";
        d6o.setText(n);
        d6f=(Button)findViewById(R.id.d6f);
        n=d6name+"-OFF";
        d6f.setText(n);
        btallo=(Button)findViewById(R.id.btallo);
        String allname = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("all", "ALL");
        n=allname+"-ON";
        btallo.setText(n);
        btallf=(Button)findViewById(R.id.btallf);
        n=allname+"-OFF";
        btallf.setText(n);
        btcnt=(Button)findViewById(R.id.btcnt);
        d1.setVisibility(View.INVISIBLE);
        d2.setVisibility(View.INVISIBLE);
        d3o.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S1");
                dbRef.setValue(2);
            }
        });
        d3f.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S1");
                dbRef.setValue(1);
            }
        });
        d4o.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S2");
                dbRef.setValue(2);
            }
        });
        d4f.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S2");
                dbRef.setValue(1);
            }
        });
        d5o.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S3");
                dbRef.setValue(2);
            }
        });
        d5f.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S3");
                dbRef.setValue(1);
            }
        });
        d6o.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S4");
                dbRef.setValue(2);
            }
        });
        d6f.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S4");
                dbRef.setValue(1);
            }
        });
        btallo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S1");
                dbRef.setValue(2);
                dbRef = fdb.getReference().child("QWERTY12345/Data/S2");
                dbRef.setValue(2);
                dbRef = fdb.getReference().child("QWERTY12345/Data/S3");
                dbRef.setValue(2);
                dbRef = fdb.getReference().child("QWERTY12345/Data/S4");
                dbRef.setValue(2);
            }
        });
        btallf.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DatabaseReference dbRef = fdb.getReference().child("QWERTY12345/Data/S1");
                dbRef.setValue(1);
                dbRef = fdb.getReference().child("QWERTY12345/Data/S2");
                dbRef.setValue(1);
                dbRef = fdb.getReference().child("QWERTY12345/Data/S3");
                dbRef.setValue(1);
                dbRef = fdb.getReference().child("QWERTY12345/Data/S4");
                dbRef.setValue(1);
            }
        });
        btcnt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
                while(!bluetoothAdapter.isEnabled()){}
                bt_view_connect();
            }
        });
        d1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(!btsocket.isConnected() || !bluetoothAdapter.isEnabled() || btsocket==null){
                        Toast.makeText(getApplicationContext(),"Disconnected",Toast.LENGTH_LONG).show();
                        btcnt.setVisibility(View.VISIBLE);
                        d1.setVisibility(View.INVISIBLE);
                        d2.setVisibility(View.INVISIBLE);
                    }
                    else if (btsocket != null) {
                        btsocket.getOutputStream().write(Integer.toString(1).getBytes());
                    }
                }
                catch(Exception e){}
            }
        });
        d2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    if(!btsocket.isConnected() || !bluetoothAdapter.isEnabled() || btsocket==null){
                        Toast.makeText(getApplicationContext(),"Disconnected",Toast.LENGTH_LONG).show();
                        btcnt.setVisibility(View.VISIBLE);
                        d2.setVisibility(View.INVISIBLE);
                        d1.setVisibility(View.INVISIBLE);
                    }
                    else if (btsocket != null) {
                        btsocket.getOutputStream().write(Integer.toString(2).getBytes());
                    }
                }
                catch(Exception e){}
            }
        });
        d1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("BT1");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d1",bnname).apply();
                                d1.setText(bnname);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("BT2");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d2",bnname).apply();
                                d2.setText(bnname);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d3o.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S1");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d3",bnname).apply();
                                String n=bnname+"-ON";
                                d3o.setText(n);
                                n=bnname+"-OFF";
                                d3f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d3f.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S1");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d3",bnname).apply();
                                String n=bnname+"-ON";
                                d3o.setText(n);
                                n=bnname+"-OFF";
                                d3f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d4o.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S2");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d4",bnname).apply();
                                String n=bnname+"-ON";
                                d4o.setText(n);
                                n=bnname+"-OFF";
                                d4f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d4f.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S2");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d4",bnname).apply();
                                String n=bnname+"-ON";
                                d4o.setText(n);
                                n=bnname+"-OFF";
                                d4f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d5o.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S3");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d5",bnname).apply();
                                String n=bnname+"-ON";
                                d5o.setText(n);
                                n=bnname+"-OFF";
                                d5f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d5f.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S3");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d5",bnname).apply();
                                String n=bnname+"-ON";
                                d5o.setText(n);
                                n=bnname+"-OFF";
                                d5f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d6o.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S4");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d6",bnname).apply();
                                String n=bnname+"-ON";
                                d6o.setText(n);
                                n=bnname+"-OFF";
                                d6f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        d6f.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final EditText txtUrl = new EditText(MainActivity.this);
                txtUrl.setHint("S4");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Name of Button")
                        .setView(txtUrl)
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String bnname = txtUrl.getText().toString();
                                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("d6",bnname).apply();
                                String n=bnname+"-ON";
                                d6o.setText(n);
                                n=bnname+"-OFF";
                                d6f.setText(n);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return false;
            }
        });
        switch_bt_on();

    }

    private void switch_bt_on(){
        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        while(!bluetoothAdapter.isEnabled()){}
        bt_view_connect();
    }

    public void onBackPressed(){
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Want", "Y").apply();
        address=bluetoothAdapter.getAddress();
        pairedDevices=bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice bd : pairedDevices){
                address=bd.getAddress().toString();
                name=bd.getName().toString();
                if(name.equals("VrikshamBT")) {
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        BluetoothDevice dvio = bluetoothAdapter.getRemoteDevice(address);
        if(bluetoothAdapter.isEnabled() && btsocket!=null && btsocket.isConnected()) {
            try {
                btsocket = dvio.createInsecureRfcommSocketToServiceRecord(myuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                btsocket.getOutputStream().close();
                btsocket.getOutputStream().close();
                btsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        d1.setVisibility(View.INVISIBLE);
        d2.setVisibility(View.INVISIBLE);
        btcnt.setVisibility(View.VISIBLE);
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
    }

    protected void onDestroy() {

        super.onDestroy();
        address=bluetoothAdapter.getAddress();
        pairedDevices=bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice bd : pairedDevices){
                address=bd.getAddress().toString();
                name=bd.getName().toString();
                if(name.equals("VrikshamBT")) {
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        BluetoothDevice dvio = bluetoothAdapter.getRemoteDevice(address);
        try {
            btsocket = dvio.createInsecureRfcommSocketToServiceRecord(myuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bluetoothAdapter.isEnabled() && btsocket!=null && btsocket.isConnected()) {
            try {
                btsocket.getOutputStream().close();
                btsocket.getOutputStream().close();
                btsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        d1.setVisibility(View.INVISIBLE);
        d2.setVisibility(View.INVISIBLE);
        btcnt.setVisibility(View.VISIBLE);
    }

    protected void onStop() {

        super.onStop();
        address=bluetoothAdapter.getAddress();
        pairedDevices=bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice bd : pairedDevices){
                address=bd.getAddress().toString();
                name=bd.getName().toString();
                if(name.equals("VrikshamBT")) {
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        BluetoothDevice dvio = bluetoothAdapter.getRemoteDevice(address);
        try {
            btsocket = dvio.createInsecureRfcommSocketToServiceRecord(myuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(bluetoothAdapter.isEnabled() && btsocket!=null && btsocket.isConnected()) {
            try {
                btsocket.getOutputStream().close();
                btsocket.getOutputStream().close();
                btsocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        d1.setVisibility(View.INVISIBLE);
        d2.setVisibility(View.INVISIBLE);
        btcnt.setVisibility(View.VISIBLE);
    }


    private void bt_view_connect(){
        Context context = MainActivity.this;
        SharedPreferences sharedPref;
        sharedPref = MainActivity.this.getSharedPreferences("name",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String defaultValue = "VrikshamBT";
        String btname = sharedPref.getString("name",defaultValue);
        address=bluetoothAdapter.getAddress();
        pairedDevices=bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice bd : pairedDevices){
                address=bd.getAddress().toString();
                name=bd.getName().toString();
                if(name.equals(btname)) {
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        try {
            BluetoothDevice dvio = bluetoothAdapter.getRemoteDevice(address);
            btsocket = dvio.createInsecureRfcommSocketToServiceRecord(myuid);
            btsocket.connect();
            if(btsocket.isConnected()){
                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
                btcnt.setVisibility(View.INVISIBLE);
                d1.setVisibility(View.VISIBLE);
                d2.setVisibility(View.VISIBLE);
            }

        }
        catch(Exception e){}

    }
}
