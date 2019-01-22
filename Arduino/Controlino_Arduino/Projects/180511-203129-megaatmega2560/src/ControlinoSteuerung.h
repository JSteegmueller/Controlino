#ifndef _ControlinoSteuerung_h_
#define _ControlinoSteuerung_h_

class ControlinoServer;
class ArduinoBoardManager;
class ArduinoKernel;
class ControlinoEvent;
class ControlinoParser;
class ControlinoMessage;
class ControlinoMessageBuilder;
class ControlinoPinManager;

class ControlinoSteuerung{
    private:
        ArduinoKernel*            derKernel;
        ArduinoBoardManager*      myBoardManager;
        ControlinoEvent*          myEventRX;
        ControlinoServer*         myServer;
        ControlinoParser*         myParser;
        ControlinoMessage*        myMsg;
        ControlinoMessageBuilder* myMsgBuilder;
        ControlinoPinManager*     myPinManager;
        int eventIndex = 0;
    public: 
        ControlinoSteuerung(ArduinoKernel *derKernel); 
        int onEvent(int event);
        int createEventRX(char *eventName);
        void eventOccured(int id);
        void loop();
};
#endif