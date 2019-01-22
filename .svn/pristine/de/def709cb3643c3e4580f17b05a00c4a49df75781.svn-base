#include "ControlinoParser.h"
#include "ControlinoMessage.h"
#include <Arduino.h>

ControlinoParser::ControlinoParser(){
    myCMsg = new ControlinoMessage();
    myCMsg->typ = 404;                         
}

ControlinoMessage* ControlinoParser::parseData(unsigned char* buffer){
    bool isControlinoSMsg = true;
    //bool isControlinoEMsg = true;
    int aktIndex = 0;
    for(int i = 0; i < sizeOfControlinoStart; i++){
        if(controlSMessage[i] != buffer[i]){
            isControlinoSMsg = false;
            break;
        }
        aktIndex++;
    }
    if(isControlinoSMsg){
        int msgTyp = buffer[aktIndex];  
        aktIndex++;
        switch(msgTyp){
            case 0:                      //ArduinoTyp Anfrage
                myCMsg->typ = 0;
                return myCMsg;
                break;
            case 2:                      //ArduinoPin Anfrage
                myCMsg->typ = 2;
                return myCMsg;
                break;
            case 4:                      //ArduinoServerAlive Anfrage 
                myCMsg->typ = 4;
                return myCMsg;   
                break;    
            case 6:                      //Arduino_Pin_An_Aus setzen - CMS 6 pinNr 0/1 CME
                myCMsg->typ = 6;
                myCMsg->pNr = buffer[aktIndex];
                aktIndex++;
                myCMsg->value = buffer[aktIndex]; 
                aktIndex++;
                return myCMsg;
                break;
            default:
                return myCMsg;    
        }
    aktIndex++;
    }
    else Serial.println("Not CM");
    //else return myCMsg;
}