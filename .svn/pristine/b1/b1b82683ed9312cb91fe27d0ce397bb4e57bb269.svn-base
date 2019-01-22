#ifndef _BitList_h_
#define _BitList_h_

class BitList
{
   unsigned char* dieListe;
   unsigned int byteCount;
public:
   BitList(unsigned int byteCount, unsigned char startValue_01);
   ~BitList();
   bool setBit(unsigned int index, unsigned char value_0_1);
   unsigned char getBit(unsigned int index, bool* success = 0);
   unsigned int getFirstBitindexThatIs_StartAtBit_getSuccess(unsigned char zeroOne, unsigned int startBit, bool &success);
};
#endif
