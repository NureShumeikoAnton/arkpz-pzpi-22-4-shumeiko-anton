#include <WiFi.h>
#include <HTTPClient.h>
#include <algorithm>
#include "DHTesp.h"
using namespace std;

String ssid = "Wokwi-GUEST";
String password = "";
DHTesp sensor;

String serverURL = "http://f142-2a09-bac5-5983-2dc-00-49-e3.ngrok-free.app/measurements/add";

String deviceType = "temperature";
// Binding device serial number
String serialNumber = "12345ABC";

const int sensorPin = 15;

const int dataSize = 15;
float temperatures[dataSize];
int currentIndex = 0;

void setupWiFi() {
  Serial.print("Connecting to WiFi");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\nConnected to WiFi!");
}

float calculateMedian(float arr[], int size) {
  float temp[size];
  memcpy(temp, arr, size * sizeof(float));
  std::sort(temp, temp + size);
  if (size % 2 == 0) {
    return (temp[size / 2 - 1] + temp[size / 2]) / 2.0;
  } else {
    return temp[size / 2];
  }
}

void sendDataToServer(float medianValue) {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;

    // Construct the full URL
    String fullURL = serverURL + "?serial=" + serialNumber + "&value=" + String(medianValue, 2);
    Serial.println("Request URL: " + fullURL);

    if (http.begin(fullURL)) {
      int httpResponseCode = http.GET();

      if (httpResponseCode > 0) {
        Serial.print("Server response code: ");
        Serial.println(httpResponseCode);

        String response = http.getString();
        Serial.print("Server response body: ");
        Serial.println(response);
      } else {
        Serial.print("Error sending data, code: ");
        Serial.println(httpResponseCode);
        Serial.println(HTTP_CODE_OK);
        Serial.println(http.errorToString(httpResponseCode));
      }

      http.end();
    } else {
      Serial.println("Failed to connect to server.");
    }
  } else {
    Serial.println("WiFi not connected!");
  }
}



void setup() {
  Serial.begin(115200);
  setupWiFi();
  sensor.setup(sensorPin, DHTesp::DHT22);
}

void loop() {
  float temperature = sensor.getTempAndHumidity().temperature;

  temperatures[currentIndex] = temperature;
  currentIndex = (currentIndex + 1) % dataSize;
  Serial.print("\nCurrent measurement: ");
  Serial.println(temperature);

  if (currentIndex == 0) {
    float median = calculateMedian(temperatures, dataSize);
    Serial.print("Median value: ");
    Serial.println(median);
    sendDataToServer(median);
  }

  delay(1000);
}
