#include "ControlinoPinManager.h"
#include "ControlinoMessage.h"
#include "Arduino.h"
#include "Pin.h"

Pin* ControlinoPinManager::myPins[70];
ControlinoPinManager* ControlinoPinManager::cpmInst;

void ControlinoPinManager::cPinMode(uint8_t pNr, uint8_t io){
    pinMode(pNr, io);
    if (io == OUTPUT) myPins[pNr]->isOutput = true;
    else myPins[pNr]->isOutput = false;
}

void ControlinoPinManager::cAnalogWrite(uint8_t pNr, int val){
    analogWrite(pNr, val);
    myPins[pNr]->value = val;
}

void ControlinoPinManager::cDigitalWrite(uint8_t pNr, uint8_t val){
    digitalWrite(pNr, val);
    myPins[pNr]->isDigital = true;

    if (val == HIGH) myPins[pNr]->value = 254;
    else myPins[pNr]->value = 0;
}

unsigned char* ControlinoPinManager::getAllPins(){
    unsigned char pinData[70];
    for (int i = 0 ; i < 70 ; i++){
        pinData[i] = myPins[i]->value;
        Serial.println(pinData[i]);
    }
    return pinData;
}

bool ControlinoPinManager::getIsOutput(int pNr){
    return myPins[pNr]->isOutput;
}


