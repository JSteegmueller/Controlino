#include "ControlinoServer.h"

#include <Ethernet.h>
#include <EthernetServer.h>
#include "ControlinoParser.h"
#include "ArduinoBoardManager.h"
#include "ControlinoSteuerung.h"
#include "ControlinoMessageBuilder.h"
#include "ControlinoPinManager.h"

ControlinoServer::ControlinoServer(ControlinoSteuerung *mySteuerung){
    myServer         = new EthernetServer(404);
    myDebugServer    = new EthernetServer(405);
    myBoardManager   = new ArduinoBoardManager();
    myMsgBuilder = new ControlinoMessageBuilder();
    myPinManager     = ControlinoPinManager::getInstance();

    this->mySteuerung = mySteuerung;
    byte mac[6] = {0xAA,0xBB,0xCC,0xDD,0xEE,0xFF};
    Ethernet.begin(mac);
    Serial.println(Ethernet.localIP());
    eventDataRx = mySteuerung->createEventRX("eventDataRx");
    ip = Ethernet.localIP();
}
/*
void ControlinoServer::ServerStart(){
    Ethernet.begin(mac);
}*/

void ControlinoServer::ConnectionClose(){
    mySocket.stop();
}

//Datenlesen & Parsen 
unsigned char* ControlinoServer::readData(){
    buffer = new unsigned char[mySocket.available()];
    int dataRead = mySocket.readBytes(buffer, mySocket.available());
    for(int i = 0; i < dataRead; i++){
        Serial.print((char)buffer[i]);
    }
    Serial.println();
    return buffer;
}

void ControlinoServer::sendBoard(){
    myServer->write(myMsgBuilder->arduinoTypAnswer(),8);
    myServer->flush();
}

void ControlinoServer::sendPin(){
    myServer->write(myMsgBuilder->arduinoPinAnswer(), 148);
    myServer->flush();
}

void ControlinoServer::sendAlive(){
    myDebugServer->write(myMsgBuilder->arduinoServerAliveAnswer(), 7);
    myDebugServer->flush();
}

void ControlinoServer::listen(){
    mySocket = myServer->available();
    if (mySocket){
        mySteuerung->eventOccured(eventDataRx);
        //mySocket.stop();
    }
    
    mySocket = myDebugServer->available();
    if (mySocket){
        mySteuerung->eventOccured(eventDataRx);
        //myDebuigSocket.stop();
    }
}


