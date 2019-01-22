#include "ArduinoTimer.h"
#include <Arduino.h>
#include "ArduinoKernel.h"

ArduinoTimer::ArduinoTimer()
{
  myEventID = ArduinoKernel::createNewEvent();
  intervalTimeMicroSec      = ARDUINOTIMER_MINIMUM_TIMER_INTERVALL_MICRO_SEC;
  nextTime                  = 0;
  eventsRemaining           = 0;
  deltaToNextEventTime      = 0;
  ueberschritteneIntervalle = 0;
}

ArduinoTimer::~ArduinoTimer()
{
  ArduinoKernel::deleteEvent(myEventID);
}

void ArduinoTimer::setIntervalTimeMicroSec(unsigned long int intervalTimeMicroSec, unsigned int nTimes, unsigned char waitMicrosToEvent)
{
  this->waitMicrosToEvent= waitMicrosToEvent;
  if (intervalTimeMicroSec < ARDUINOTIMER_MINIMUM_TIMER_INTERVALL_MICRO_SEC)
  {
    this->intervalTimeMicroSec = ARDUINOTIMER_MINIMUM_TIMER_INTERVALL_MICRO_SEC;
  }
  else
  {
    this->intervalTimeMicroSec = intervalTimeMicroSec;
  }

  eventsRemaining = nTimes;

  unsigned long int microSeconds = micros();
  nextTime = microSeconds + this->intervalTimeMicroSec;
}

unsigned long int  ArduinoTimer::pollTimerEvent()
{
  if (eventsRemaining == 0) return 0;
  
  deltaToNextEventTime = ArduinoKernel::remainingMicrosecondsTo(nextTime);

  if ((deltaToNextEventTime < waitMicrosToEvent) && (deltaToNextEventTime > 5)) // Nahe dran
  {
    delayMicroseconds(deltaToNextEventTime - 4); // 4 us brauchen die Befehle (ca.)
    deltaToNextEventTime = 0;
  }

  if (deltaToNextEventTime <= 0) // Drüber?
  {
    ueberschritteneIntervalle = -deltaToNextEventTime / intervalTimeMicroSec + 1;
    nextTime += intervalTimeMicroSec * ueberschritteneIntervalle;

    if (eventsRemaining != ARDUINOTIMER_ENDLESS)
    {
      if (eventsRemaining > 0)
      {
        eventsRemaining--;
      }
    }

/*
    if(ueberschritteneIntervalle>0)
    {
      Serial.println(getEventID());
    }
*/
    return ueberschritteneIntervalle;
  }
  else
  {
    return 0;
  }
}

