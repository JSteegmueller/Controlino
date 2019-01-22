#include "ControlinoSteuerung.h"

#include "ArduinoKernel.h"
#include "ArduinoBoardManager.h"
#include "ControlinoServer.h"
#include "ControlinoEvent.h"
#include "ControlinoParser.h"
#include "ControlinoMessage.h"
#include "ControlinoMessageBuilder.h"
#include "ControlinoPinManager.h"

ControlinoSteuerung::ControlinoSteuerung(ArduinoKernel *derKernel){
    this->derKernel = derKernel;
    myBoardManager = new ArduinoBoardManager();
    myServer       = new ControlinoServer(this);
    myParser       = new ControlinoParser();
    myMsg          = new ControlinoMessage();
    myMsgBuilder   = new ControlinoMessageBuilder();
    myPinManager   = ControlinoPinManager::getInstance();
    myPinManager->cPinMode(13, OUTPUT);
    myPinManager->cDigitalWrite(13, 0);
}

int ControlinoSteuerung::onEvent(int event){
    if(event == myEventRX->eventId ){
        Serial.println("EventRX");
        ControlinoMessage* readMsg = myParser->parseData(myServer->readData()); 
        char msgTyp = readMsg->typ;
        
        switch(msgTyp){
            case 0:                                         //ArduinoTyp 
                myServer->sendBoard();
                break;
            case 2:                                         //ArduinoPin
                myServer->sendPin();
                break;
            case 4:                                         //ArduinoServerAlive
                myServer->sendAlive();
                break;
            case 6:                                         //ArduinoPin An/Aus 
                int pNr = readMsg->pNr;
                int value = readMsg->value;
                
                if(value == 0) myPinManager->cPinMode(pNr, 0);
                else myPinManager->cPinMode(pNr, 1);

                myPinManager->cDigitalWrite(pNr, value);
                break;    
        }
        return 1;
    }
}

int ControlinoSteuerung::createEventRX(char *eventName){
    myEventRX = new ControlinoEvent(derKernel->createNewEvent(), eventName);
    Serial.println("eventRegistered:");
    Serial.print(myEventRX->eventName);
    return myEventRX->eventId;
}

void ControlinoSteuerung::eventOccured(int id){
    //Serial.println("Event");
    derKernel->event(id);
}

void ControlinoSteuerung::loop(){
    myServer->listen();
}
