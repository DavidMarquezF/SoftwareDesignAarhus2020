/******************************************************/
//       THIS IS A GENERATED FILE - DO NOT EDIT       //
/******************************************************/

#include "Particle.h"
#line 1 "/home/david/Documents/IOT/Webhooks/Webhooks/src/Webhooks.ino"
void setup();
void loop();
#line 1 "/home/david/Documents/IOT/Webhooks/Webhooks/src/Webhooks.ino"
int led = D7;  // The on-board LED

void setup() {
  pinMode(led, OUTPUT);
}

void loop() {
  digitalWrite(led, HIGH);   // Turn ON the LED

  String temp = String(random(60, 80));
  Particle.publish("temp", temp, PRIVATE);
  delay(30000);               // Wait for 30 seconds

  digitalWrite(led, LOW);    // Turn OFF the LED
  delay(30000);               // Wait for 30 seconds
}