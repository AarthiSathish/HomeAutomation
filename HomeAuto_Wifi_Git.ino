#include <WiFiManager.h>
#include <DNSServer.h>
#include <ESP8266wifi.h>
#include <FirebaseESP8266.h>
#include<EEPROM.h>

String ssid;
String password;
const char* apssid="ssid";
const char* appassword="password";
FirebaseData firebaseData;
int pin1=D9;
int pin2=D2;
int pin3=D1;
int pin4=D5;
int r=0;
String cred_avail;
int s1,s2,s3,s4,rset;
int i1,i2,i3,i4;
WiFiManager wifiManager;
char arr_ssid[50];
char arr_password[50];
char arr_cred[50];

void setup() {
  Serial.begin(9600);
  EEPROM.begin(512);
  pinMode(r,INPUT_PULLUP);
  pinMode(pin1,OUTPUT);
  pinMode(pin2,OUTPUT);
  pinMode(pin3,OUTPUT);
  pinMode(pin4,OUTPUT);
  pinMode(r,INPUT_PULLUP);
  digitalWrite(pin1,1);
  digitalWrite(pin2,1);
  digitalWrite(pin3,1);
  digitalWrite(pin4,1);
  EEPROM.get(101,arr_cred);
  cred_avail=String(arr_cred);
  Serial.println(cred_avail); 
  if(cred_avail!="Cred"){
    AP_getCredentials();  
  }
  
  else{
   WiFi.mode(WIFI_STA);
   ssid=(String)EEPROM.get(1,arr_ssid);
   password=(String)EEPROM.get(51,arr_password);
   Serial.println(ssid);
   Serial.println(password);
   WiFi.begin(ssid.c_str(),password.c_str());
   while(WiFi.status() != WL_CONNECTED){
      if(digitalRead(r)==0)
      {
        int count=1;
        int sec=0;
        while(sec<=15000)
        {
          if(digitalRead(r)==0)
          {
            count++;
          }
          sec+=1000;
          delay(1000);
        }
       if(count>=13)
        AP_getCredentials();
     }
     delay(500);
   }
  }
  Firebase.begin("https://filler.firebaseio.com/", "Secret Creds");
  if(WiFi.status() == WL_CONNECTED){
    s1=1;
    s2=1;
    s3=1;
    s4=1;
    Firebase.setInt(firebaseData,"Path",1);
    Firebase.setInt(firebaseData,"Path",1);
    Firebase.setInt(firebaseData,"Path",1);
    Firebase.setInt(firebaseData,"Path",1);
    digitalWrite(LED_BUILTIN,0);
    delay(500);
    digitalWrite(LED_BUILTIN,1);
    delay(500);
    digitalWrite(LED_BUILTIN,0);
    delay(500);
    digitalWrite(LED_BUILTIN,1);
    delay(500);
    digitalWrite(LED_BUILTIN,0);
    delay(500);
    digitalWrite(LED_BUILTIN,1);
  }
}




void loop() {
  if(WiFi.status() != WL_CONNECTED)
  {
   //WiFi.mode(WIFI_STA);
   ssid=(String)EEPROM.get(1,arr_ssid);
   password=(String)EEPROM.get(51,arr_password);
   Serial.println(ssid);
   Serial.println(password);
   WiFi.begin(ssid.c_str(),password.c_str());
   while(WiFi.status() != WL_CONNECTED){
      if(digitalRead(r)==0)
      {
        int count=1;
        int sec=0;
        while(sec<=10000)
        {
          if(digitalRead(r)==0)
          {
            count++;
          }
          sec+=1000;
          delay(1000);
        }
       if(count>=9)
        AP_getCredentials();
     }
     delay(500);
   }
   if(WiFi.status() == WL_CONNECTED){
    Firebase.getInt(firebaseData,"Path");
    s1=firebaseData.intData();
    Firebase.getInt(firebaseData,"Path");
    s2=firebaseData.intData();
    Firebase.getInt(firebaseData,"Path");
    s3=firebaseData.intData();
    Firebase.getInt(firebaseData,"Path");
    s4=firebaseData.intData();
    digitalWrite(LED_BUILTIN,0);
    delay(500);
    digitalWrite(LED_BUILTIN,1);
    delay(500);
    digitalWrite(LED_BUILTIN,0);
    delay(500);
    digitalWrite(LED_BUILTIN,1);
    delay(500);
    digitalWrite(LED_BUILTIN,0);
    delay(500);
    digitalWrite(LED_BUILTIN,1);
    if(s1==1)
      digitalWrite(pin1,1);
    else if(s1==2)
      digitalWrite(pin1,0);
    if(s2==1)
      digitalWrite(pin2,1);
    else if(s2==2)
      digitalWrite(pin2,0);
    if(s3==1)
      digitalWrite(pin3,1);
    else if(s3==2)
      digitalWrite(pin3,0);
    if(s4==1)
      digitalWrite(pin4,1);
    else if(s4==2)
      digitalWrite(pin4,0);
  
   }
  }
  if(WiFi.status() == WL_CONNECTED){
    Firebase.getInt(firebaseData,"Path");
    i1=firebaseData.intData();
    if(i1!=s1 && i1!=0){
      if(i1==1)
        digitalWrite(pin1,1);
      if(i1==2)
        digitalWrite(pin1,0);
      Serial.print("S1:");
      Serial.println(i1);
      s1=i1;
    }
  }
  if(WiFi.status() == WL_CONNECTED){
    Firebase.getInt(firebaseData,"Path");
    i2=firebaseData.intData();
    if(i2!=s2 && i2!=0){
      if(i2==1)
        digitalWrite(pin2,1);
      if(i2==2)
        digitalWrite(pin2,0);
      Serial.print("S2:");
      Serial.println(i2);
      s2=i2;
    }
  }
  if(WiFi.status() == WL_CONNECTED){
    Firebase.getInt(firebaseData,"Path");
    i3=firebaseData.intData();
    if(i3!=s3 && i3!=0){
      if(i3==1)
        digitalWrite(pin3,1);
      if(i3==2)
        digitalWrite(pin3,0);
      Serial.print("S3:");
      Serial.println(i3);
      s3=i3;
    }
  }
  if(WiFi.status() == WL_CONNECTED){
    Firebase.getInt(firebaseData,"Path");
    i4=firebaseData.intData();
    if(i4!=s4 && i4!=0){
      if(i4==1)
        digitalWrite(pin4,1);
      if(i4==2)
        digitalWrite(pin4,0);
      Serial.print("S4:");
      Serial.println(i4);
      s4=i4;
    }
  }
}


void AP_getCredentials(){
  wifiManager.startConfigPortal(apssid,appassword);
  ssid=WiFi.SSID();
  password=WiFi.psk();
  cred_avail="Cred";
  ssid.toCharArray(arr_ssid,ssid.length()+1);
  password.toCharArray(arr_password,password.length()+1);
  cred_avail.toCharArray(arr_cred,cred_avail.length()+1);
  EEPROM.put(1,arr_ssid);
  EEPROM.put(51,arr_password);
  EEPROM.put(101,arr_cred);
  EEPROM.commit();
}


