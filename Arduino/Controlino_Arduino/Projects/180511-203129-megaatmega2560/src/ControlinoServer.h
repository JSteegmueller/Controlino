#ifndef _ControlinoServer_h_
#define _ControlinoServer_h_
#include <EthernetClient.h>

class EthernetServer;
class ControlinoParser;
class ArduinoBoardManager;
class ControlinoSteuerung;
class ControlinoMessageBuilder;
class ControlinoPinManager;

class ControlinoServer{
    private:
        EthernetServer*             myServer;
        EthernetServer*             myDebugServer;
        EthernetClient              mySocket;
        ControlinoParser*           myParser;
        ArduinoBoardManager*        myBoardManager;
        ControlinoSteuerung*        mySteuerung;
        ControlinoMessageBuilder*   myMsgBuilder;
        ControlinoPinManager*       myPinManager;
        
        unsigned char* buffer;
        int eventDataRx;
        char ip;

    public: 
        ControlinoServer(ControlinoSteuerung *mySteuerung);
        void ServerStart();
        void ConnectionClose();
        void sendBoard();
        void sendPin();
        void sendAlive();
        void listen();
        unsigned char* readData();
};
#endif