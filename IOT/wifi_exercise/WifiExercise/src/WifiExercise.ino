/*
 * Project WifiExercise
 * Description:
 * Author:
 * Date:
 */

SerialLogHandler logHandler;
byte mac[6];
// setup() runs once, when the device is first turned on.
void setup()
{
  WiFi.on();
  waitFor(Serial.isConnected, 1000);
  Log.info("SSID: %s", WiFi.SSID());
  Log.info("ip address: %s", WiFi.localIP().toString().c_str());
  WiFi.macAddress(mac);
  Log.info("mac: %02x:%02x:%02x:%02x:%02x:%02x", mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
}

// loop() runs over and over again, as quickly as it can execute.
void loop()
{
  // The core of your code will likely live here.
}