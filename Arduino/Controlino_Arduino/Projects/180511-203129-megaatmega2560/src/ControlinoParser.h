#ifndef _ControlinoParser_h_
#define _ControlinoParser_h_

class ControlinoMessage;

class ControlinoParser{
    private:
        const int sizeOfControlinoStart = 3;
        ControlinoMessage* myCMsg;
    public:
        ControlinoParser();
        char* controlSMessage = "CMS";
        char* controlEMessage = "CME";
        ControlinoMessage* parseData(unsigned char* buffer);
};
#endif 