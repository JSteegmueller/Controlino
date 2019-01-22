#include "ControlinoApp.h"
#include "ArduinoKernel.h"
#include "ControlinoSteuerung.h"
#include "ControlinoPinManager.h"


int timerLED;
int lEDVal;
bool lEDOn = false;

ControlinoApp::ControlinoApp(ArduinoKernel* derKernel)
:ArduinoApp::ArduinoApp(derKernel){
}

void ControlinoApp::appSetup(){
    Serial.println("Controlino -- Setup");
    mySteuerung  = new ControlinoSteuerung(meinArduinoKernel);
    myPinManager = ControlinoPinManager::getInstance();

    timerLED = meinArduinoKernel->setTimerInterrupt(50000);
    lEDVal = 0;
    for(int i = 2; i < 5; i++){
        myPinManager->cPinMode(i, OUTPUT);
    }
}

void ControlinoApp::appLoop(){
    mySteuerung->loop();
}

bool ControlinoApp::appEvent(int idEvent){
    if(idEvent == timerLED){
        if(lEDOn == false){
            lEDVal += 10;
            if(lEDVal == 250) lEDOn = true;
        }
        else{
            lEDVal -= 10;
            if(lEDVal == 0) lEDOn = false;
        }
        for (int i = 2; i < 5; i++){
            myPinManager->cAnalogWrite(i, lEDVal);
        }
    }
    return mySteuerung->onEvent(idEvent);
}



