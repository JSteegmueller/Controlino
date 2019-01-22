#ifndef _ControlinoApp_h_
#define _ControlinoApp_h_

#include <Arduino.h>
#include "ArduinoApp.h"

class ArduinoKernel;
class ControlinoSteuerung;
class ControlinoPinManager;

class ControlinoApp : public ArduinoApp{
    private: 
        ControlinoSteuerung*  mySteuerung;
        ControlinoPinManager* myPinManager; 
    public:
        ControlinoApp(ArduinoKernel *derKernel);
        void appSetup();
        void appLoop();
        bool appEvent(int idEvent);
};
#endif