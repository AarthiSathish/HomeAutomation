char Incoming_value=0;
int pin1=2;
int pin2=5;
void setup() 
{
  Serial.begin(9600);         
  pinMode(pin1, OUTPUT);
  pinMode(pin2,OUTPUT); 
  digitalWrite(pin1,HIGH); 
  digitalWrite(pin2, HIGH);      
}
void loop()
{
  if(Serial.available() > 0)  
  {
    Incoming_value = Serial.read();      //Read the incoming data and store it into variable Incoming_value 
    if(Incoming_value == '1'){           
      digitalWrite(pin1,LOW);
      delay(3000);
      digitalWrite(pin1,HIGH);
      flush_receive();
    }  
    else if(Incoming_value == '2'){           
      digitalWrite(pin2,LOW);
      delay(3000);
      digitalWrite(pin2,HIGH); 
      flush_receive();
    }                           
  }
}

void flush_receive(){
  while(Serial.available())
    Serial.read();
}
