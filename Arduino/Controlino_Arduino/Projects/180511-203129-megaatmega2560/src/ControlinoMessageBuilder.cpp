#include "ControlinoMessageBuilder.h"
#include "ControlinoPinManager.h"
#include "Arduino.h"
#include "ArduinoBoardManager.h"
#include "ControlinoMessage.h"

ControlinoMessageBuilder::ControlinoMessageBuilder(){ 
    myBoardManager = new ArduinoBoardManager();
    myPinManager   = ControlinoPinManager::getInstance();
    myMsg          = new ControlinoMessage();
    dasBoard = myBoardManager->BOARD_NAME;
}

unsigned char* ControlinoMessageBuilder::arduinoTypAnswer(){
    buffer = new unsigned char[8];
    msgTyp = 1;
    int aktIndex = 0;
    for(aktIndex; aktIndex < sizeOfStart; aktIndex++){
        buffer[aktIndex] = start[aktIndex];
    }       
    buffer[aktIndex] = msgTyp;
    aktIndex++;
    
    if (strcmp(dasBoard, "Mega") == 0){
        Serial.println("Mega");
        buffer[aktIndex] = 1;
        aktIndex++;
    }
    else {
        buffer[aktIndex] = 404;
        aktIndex++;
    }            
    for(int i = 0; i < sizeOfEnd; i++){
        buffer[aktIndex] = end[i];
        aktIndex++;
    }
    return buffer;
}

unsigned char* ControlinoMessageBuilder::arduinoPinAnswer(){
    Serial.println("Building answer pin type");
    buffer = new unsigned char[148];
    msgTyp = 3;
    int aktIndex = 0;

    for(aktIndex; aktIndex < sizeOfStart; aktIndex++){
        buffer[aktIndex] = start[aktIndex];
    }
    buffer[aktIndex] = msgTyp;
    aktIndex++;
    
    buffer[aktIndex] = 70;
    aktIndex++;

    unsigned char* derWert = myPinManager->getAllPins();
    

    for(int i = 0; i < sizeOfMega; i++){ 
        Serial.print("Pin Nummer:");
        Serial.println(i);
        buffer[aktIndex] = myPinManager->getIsOutput(i);
        Serial.print("IO:");
        Serial.println(buffer[aktIndex]);
        aktIndex++;

        buffer[aktIndex] = derWert[i];
        Serial.print("Wert:");
        Serial.println(buffer[aktIndex]);

        aktIndex++;
    }
    Serial.println(aktIndex);
    for(int i = 0; i < sizeOfEnd; i++){
        buffer[aktIndex] = end[i];
        aktIndex++;
    }
    return buffer;
}
 
unsigned char* ControlinoMessageBuilder::arduinoServerAliveAnswer(){
    buffer = new unsigned char[7];
    msgTyp = 5;
    int aktIndex = 0;
    for(aktIndex; aktIndex < sizeOfStart; aktIndex++){
        buffer[aktIndex] = start[aktIndex];
    }       
    buffer[aktIndex] = msgTyp;
    aktIndex++;

    for(int j = 0 ; j < sizeOfEnd; j++){
        buffer[aktIndex] = end[j];
        aktIndex++;
    }
    return buffer;    
}
 