/*
 * Project MockProject
 * Description:
 * Author:
 * Date:
 */

int analogValue = 0;
double tempC = 0;


// setup() runs once, when the device is first turned on.
void setup() {
  // Put initialization like pinMode and begin functions here.
  pinMode(A0, INPUT);
  pinMode(D6, OUTPUT);
  pinMode(D5, OUTPUT);
}
enum State{
  Reading,
  Operation,
  Wifi,
  Sleep
};

void setOutStat(State state){
  switch (state) {
  case Reading:
    digitalWrite(D6, LOW);
    digitalWrite(D5, LOW);
    break;
  case Operation:
  digitalWrite(D6, LOW);
    digitalWrite(D5, HIGH);
  break;
  case Sleep:
  digitalWrite(D6, HIGH);
    digitalWrite(D5, HIGH);
  break;
  case Wifi:
  digitalWrite(D6, HIGH);
    digitalWrite(D5, LOW);
  break;
  
  default:
    break;
  }
}

// loop() runs over and over again, as quickly as it can execute.


void loop() {
  // The core of your code will likely live here.
  setOutStat(Reading);
  for(int i = 0; i < 100000; i++){
    analogValue = analogRead(A0);
  }
  tempC = 0;
  setOutStat(Operation);
  for(int i = 0; i < 100000; i++){
        tempC += (((analogValue * i * 3.3) / 4095) - 0.5) * 100;
  }
  setOutStat(Wifi);
  Particle.publish("test_message", "hello this is a long test to keep the wifi running for a little bit more time", PRIVATE);
  delay(5000);
  setOutStat(Sleep);
  SystemSleepConfiguration config;
  config.mode(SystemSleepMode::ULTRA_LOW_POWER)
      .duration(10s);
      System.sleep(config);
}


