#include <Arduino.h>
#include <Ethernet.h>

EthernetServer myServer(29);
byte mac[6] = {0xAA,0xBB,0xCC,0xDD,0xEE,0xFF};
unsigned char buffer[25];
int anzByte = 0;

void setup(){
  Serial.begin(9600);
  Ethernet.begin(mac);
  Serial.println(Ethernet.localIP());
  myServer.begin();
  for(int i = 0; i < 20; i++){
    buffer[i] = 0;
  }
}
void loop(){
  EthernetClient myClient = myServer.available();
  if(myClient.available()){
    Serial.println("Leben!");
    int dataRead = myClient.readBytes(buffer, myClient.available());   
    Serial.println(dataRead);
    for(int i = 0; i < dataRead; i++){
      Serial.print(buffer[i],HEX);
    }
  }
  if(!myClient.connected()){
    myClient.stop();
  }
}

