#ifndef _ArduinoMain_h_
#define _ArduinoMain_h_
#include "ArduinoKernel.h"

class ControlinoApp;
class ArduinoMain : public ArduinoKernel
{
  public:
    ArduinoMain();
    void arduinoLoop();
    void arduinoSetup();
    void arduinoEvent(int idEvent);

    private:
      ControlinoApp* myControlino;
};

#endif




