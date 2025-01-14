#include <WiFi.h>
#include <HTTPClient.h>
#include <ESP32Servo.h>

const char* ssid = "Wokwi-GUEST";
const char* password = "";

const char* serverUrl = "http://f142-2a09-bac5-5983-2dc-00-49-e3.ngrok-free.app/commands/last/";

// Binding device serial number
const String serialNumber = "TEMPO111";

const unsigned long requestInterval = 60000; // 1 minute

const int buttonPin = 13;
const int servoPin = 15;

bool deviceOn = false;
unsigned long lastRequestTime = 0;
Servo servo;

void setup() {
  pinMode(buttonPin, INPUT_PULLUP);
  servo.attach(servoPin);
  servo.write(0);
  
  Serial.begin(115200);
  connectToWiFi();
}

void loop() {
  if (digitalRead(buttonPin) == LOW) {
    delay(50); // to avoid button debouncing
    if (digitalRead(buttonPin) == LOW) {
      deviceOn = !deviceOn;
      Serial.println(deviceOn ? "Device ON" : "Device OFF");
      delay(300);
    }
  }

  if (deviceOn && millis() - lastRequestTime > requestInterval) {
    sendRequestToServer();
    lastRequestTime = millis();
  }
}

void connectToWiFi() {
  Serial.print("Connecting to WiFi");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWiFi connected");
}

void sendRequestToServer() {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    String url = String(serverUrl) + serialNumber;
    http.begin(url);
    int httpResponseCode = http.GET();

    if (httpResponseCode == 200) {
      String payload = http.getString();
      int commandValue = payload.toInt();
      Serial.println("Command received: " + String(commandValue));
      servo.write(map(commandValue, 0, 100, 0, 180));
    } else {
      Serial.println("Failed to get command. HTTP response code: " + String(httpResponseCode));
    }
    http.end();
  } else {
    Serial.println("WiFi not connected");
  }
}
