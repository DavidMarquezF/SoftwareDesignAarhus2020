#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>


struct {
  uint8_t packetTypeFlags;
  uint8_t remainingLength;
} mqtt_fixed_header_format_t;

static void encode(uint32_t x, uint8_t* out){
  uint8_t encodedByte; 
  do{
    encodedByte = x % 128;
    x /= 128;
    if(x  > 0)
      encodedByte = encodedByte | 128;
    *out++ = encodedByte;
  }
  while(x > 0);
}

//http://www.steves-internet-guide.com/mosquitto_pub-sub-clients/

static uint32_t decode(uint8_t length[]){
  uint8_t i = 0;
  uint32_t multiplier = 1;
  uint8_t encodedByte; 
  
  uint32_t value = 0;
  do{
    encodedByte = length[i++];
    value += (encodedByte & 127) * multiplier;
   
    if(multiplier > 128*128*128){
      printf("Error decoding");
      exit(EXIT_FAILURE);
    }
    multiplier *= 128;
  }
  while((encodedByte & 128) != 0);
  return value;
}


//According to docs should be able to go from 0 to 268 435 455 (0xFF, 0xFF, 0xFF, 0x7F)
int main(int argc, char *argv[]){
  int num = atoi(argv[1]);
  uint8_t encodedBytes[4];
  encode(num, encodedBytes);
  for(uint8_t i = 0; i < 4; i++){
    printf("%X\t", encodedBytes[i]);
  }
  putchar('\n');
  printf("%d\n", decode(encodedBytes));
}
