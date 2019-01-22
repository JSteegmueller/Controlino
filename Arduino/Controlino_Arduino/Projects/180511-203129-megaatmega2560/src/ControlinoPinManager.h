#ifndef _ControlinoPinManager_h_
#define _ControlinoPinManager_h_

#include <Arduino.h>
#include <Pin.h>

class ControlinoMessage;

//Singelton
class ControlinoPinManager{
    private:        
        static ControlinoPinManager* cpmInst;
        static Pin*                  myPins[70];
        ControlinoPinManager(){};

    public:
        static ControlinoPinManager* getInstance(){
            if (!cpmInst){
                cpmInst = new ControlinoPinManager();
                    for ( int i = 0 ; i < 70 ; i++){
                        myPins[i] = new Pin();
                    }  
                } 
            return cpmInst;
        }
        unsigned char* getAllPins();
        bool getIsOutput(int pNr);
        void cPinMode(uint8_t pNr,uint8_t io);
        void cAnalogWrite(uint8_t pNr, int val);
        void cDigitalWrite(uint8_t pNr, uint8_t val);
};
#endif