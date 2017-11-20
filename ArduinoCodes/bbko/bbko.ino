#include <SPI.h>
#include <MFRC522.h>
#include <EEPROM.h>

#define wireless_name "babako" // Please enter the local wireless SSID which the WebApp lies
#define wireless_pwn "bun12345" // And the password for it of course
#define RST_PIN 9
#define SS_PIN 10

byte readCard[4];
int successRead;
String tagID;
int itemCount = 0;
const String passgetID = "M-A-K-01"; // PassGet ID specific for the basket

MFRC522 mfrc522(SS_PIN, RST_PIN);
MFRC522::MIFARE_Key key;

void setup() {
  // Open a serial connection to baud rate 115200 which is the standard for ESP8266
  Serial.begin(115200);
  SPI.begin();

  // Initiate MFRC522 RFID reader
  mfrc522.PCD_Init  ();

  // Initiate Wi-Fi Connection
  Serial.println("AT"); 

  // Pin 13 and 3 is the indicator for the RFID reads. 13 being READ, 3 being DELETED. 
  pinMode(13,OUTPUT);
  pinMode(3,OUTPUT);

  delay(3000);

  if(Serial.find("OK")){ // If we established a connection between Wi-Fi and ESP8266
    Serial.println("AT+CWMODE=1"); // Set the mode for the ESP8266
    delay(2000);
    String connectionString=String("AT+CWJAP=\"")+wireless_name+"\",\""+wireless_pwn+"\""; // Connection string. Proceed with caution.
    Serial.println(connectionString);
    delay(5000);
  }
  
  // Setting MUX mode and opening a server at PORT 80. Try not to change this port pls boiz.
  Serial.print("AT+CIPMUX=1\r\n");
  delay(2000);
  Serial.print("AT+CIPSERVER=1,88\r\n");
  delay(1000);

  // After getting server running wait for RFID, maybe one day it'll come
  do {
    successRead = getID();
  } while (!successRead);

}

void loop() {
  // If communication is available
  if(Serial.available()>0){
    // If there is an incoming message
    if(Serial.find("+IPD,")){

      // Create the JSON data
      char itemsChar[tagID.length()+1];
      tagID.toCharArray(itemsChar,tagID.length()+1);
      // Create a new String array to seperate the item UIDs
      String items[itemCount] = strtok(itemsChar, ",");
      String jsonData = "{\"passgetID\":\"" + passgetID + "\",\"items\":[";

      for(int i = 0; i<itemCount-1; i++){
        jsonData += "\""+items[i]+"\",";
      }
      jsonData += "\""+items[itemCount-1]+"\"]}";
      // We split the strings by commas, which we had to first convert our string to char first
      // Then simply concatination no black magic here
      
      String metin = String("<p>") + jsonData + String("</p>");  
      String cipsend = "AT+CIPSEND=";
      cipsend +="0";
      cipsend +=",";
      cipsend += metin.length();
      cipsend += "\r\n";
      Serial.print(cipsend);
      delay(50);
      Serial.println(metin);
      Serial.println("AT+CIPCLOSE=0");
    }
  }
}

int getID() {
  
  // Return 0 if none read
  if ( ! mfrc522.PICC_IsNewCardPresent()) { 
    return 0;
  }
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return 0;
  }
  
  Serial.print("Kart UID'si: ");
  // Read the UID byte by byte (LOL) and print it
  for (int i = 0; i < mfrc522.uid.size; i++) {  //
    readCard[i] = mfrc522.uid.uidByte[i];
    Serial.print(readCard[i], HEX);
    itemCount++;

    /*
     * tagID = id1,id2,id3.....,idn 
     */
    String tempID = String(readCard[i],HEX);
    if(tagID == "")
      tagID += tempID;
    else
      tagID += ","+tempID;
  }
  // Set the read as 1
  digitalWrite(13,HIGH);  
  Serial.println("");
  // Stop reading and end this misery by returning 1
  mfrc522.PICC_HaltA();
  return 1;
}
