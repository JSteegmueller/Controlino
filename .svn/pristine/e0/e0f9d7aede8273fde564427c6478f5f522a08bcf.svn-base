#ifndef _ControlinoMessageBuilder_h_
#define _ControlinoMessageBuilder_h_

class ArduinoBoardManager;
class ControlinoPinManager;
class ControlinoMessage;

class ControlinoMessageBuilder{
    private:
        const int sizeOfStart = 3;
        const int sizeOfEnd   = 3;
        const int sizeOfMega  = 70;
        const char* start     = "CMS";
        const char* end       = "CME";
        int msgTyp;
        unsigned char* buffer;
        unsigned char* dasBoard;

        ArduinoBoardManager*  myBoardManager;
        ControlinoPinManager* myPinManager;
        ControlinoMessage*    myMsg;
    public:
        ControlinoMessageBuilder();
         unsigned char* arduinoTypAnswer();
         unsigned char* arduinoPinAnswer();
         unsigned char* arduinoServerAliveAnswer();

};
#endif 