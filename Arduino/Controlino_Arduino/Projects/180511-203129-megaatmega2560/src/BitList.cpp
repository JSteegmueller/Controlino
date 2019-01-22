#include "BitList.h"

BitList::BitList(unsigned int byteCount, unsigned char startValue_01)
{
  this->byteCount = byteCount;
  dieListe = new unsigned char[byteCount];
  for (unsigned int i = 0; i < byteCount; i++)
  {
    dieListe[i] = startValue_01;
  }
}

BitList::~BitList()
{
  delete[] dieListe;
  dieListe = 0;
}

bool BitList::setBit(unsigned int index, unsigned char value_0_1)
{
  unsigned int bitIndex  = index % 8;
  unsigned int byteIndex = (index - bitIndex)/8;

  if (byteIndex >= byteCount) return false;
  
  if (value_0_1 == 1)
  {
    dieListe[byteIndex] |= (1 << bitIndex);
  }
  else
  {
    dieListe[byteIndex] &= (0xFF - (1 << bitIndex));
  }
  return true;
}

unsigned char BitList::getBit(unsigned int index, bool* success)
{
  unsigned int bitIndex  = index % 8;
  unsigned int byteIndex = (index - bitIndex)/8;

  if (byteIndex < byteCount)
  {
    if (success != 0)
    {
      *success = true;
    }
    return (dieListe[byteIndex] & (1 << bitIndex)) >> bitIndex;
  }
  else
  {
    if (success != 0)
    {
      *success = false;
    }
    return 0;
  }
}

unsigned int BitList::getFirstBitindexThatIs_StartAtBit_getSuccess(unsigned char zeroOne, unsigned int startBit, bool &success)
{
  success = true;
  unsigned int bitIndex;
  for (bitIndex = startBit; bitIndex < byteCount * 8; bitIndex++)
  {
    if (getBit(bitIndex, &success) == zeroOne )
    {
      break;
    }

    if (success == false)
    {
       return 0;
    }
  }

  return bitIndex;
}
