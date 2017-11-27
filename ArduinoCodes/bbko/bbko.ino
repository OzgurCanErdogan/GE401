#include <SPI.h>
#include <MFRC522.h>
#include <EEPROM.h>

#define wireless_name "mahmut123" // Please enter the local wireless SSID which the WebApp lies
#define wireless_pwn "kerik123" // And the password for it of course
#define RST_PIN 9
#define SS_PIN 10

byte readCard[4];
int successRead;
String tagID[32];
int itemCount = 0;
const String passgetID = "M-A-K-01"; // PassGet ID specific for the basket

MFRC522 mfrc522(SS_PIN, RST_PIN);
MFRC522::MIFARE_Key key;

void setup() {
  // Open a serial connection to baud rate 115200 which is the standard for ESP8266
  Serial.begin(115200);
  SPI.begin();

  // Initiate MFRC522 RFID reader
  mfrc522.PCD_Init();

  // Initiate Wi-Fi Connection
  Serial.println("AT");

  // Pin 2 and 4 is the indicator for the RFID reads. 13 being READ, 3 being DELETED. 
  pinMode(2,OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(13,OUTPUT);  
  
  delay(2000);

  if(Serial.find("OK")){ // If we established a connection between Wi-Fi and ESP8266
    Serial.println("AT+CWMODE=1"); // Set the mode for the ESP8266
    delay(2000);
    String connectionString=String("AT+CWJAP=\"")+wireless_name+"\",\""+wireless_pwn+"\""; // Connection string. Proceed with caution.
    Serial.println(connectionString);
    delay(5000);
  } else{
    Serial.println("Error!");
  }
  
  // Setting MUX mode and opening a server at PORT 80. Try not to change this port pls boiz.
  Serial.print("AT+CIPMUX=1\r\n");
  delay(2000);
  Serial.print("AT+CIPSERVER=1,80\r\n");
  delay(1000);
}

void loop() {
  // If communication is available
  if(Serial.available()>0){
    // If there is an incoming message
    if(Serial.find("+IPD")){
      String metin = String("<p>") + createJsonData() + String("</p>");  
      String cipsend = "AT+CIPSEND=";
      cipsend +="0";
      cipsend +=",";
      cipsend += metin.length();
      cipsend += "\r\n";
      Serial.print(cipsend);
      delay(50);
      Serial.println(metin);
      delay(2000);
      Serial.println("AT+CIPCLOSE=0");
    }
  }
  else{
    // After getting server running wait for RFID, maybe one day it'll come
    //do {
      successRead = getID();
    //} while (!successRead);
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
  String tempID = "";
  
  Serial.print("RFID UID: ");
  // Read the UID byte by byte (LOL) and print it
  for (int i = 0; i < mfrc522.uid.size; i++) {  //
    readCard[i] = mfrc522.uid.uidByte[i];
    Serial.print(readCard[i], HEX);

    tempID += String(readCard[i],HEX);
  }

  changeBasket(tempID);
 
  // Stop reading and end this misery by returning 1
  mfrc522.PICC_HaltA();
  return 1;
}

// Creates the JSON data to be sent to the server
String createJsonData(){
  // Create the JSON data
  String jsonData = "{\"passgetID\":\"" + passgetID + "\",\"items\":[";

  for(int i = 0; i<itemCount-1; i++){
    jsonData += "\""+tagID[i]+"\",";
  }
  jsonData += "\""+tagID[itemCount-1]+"\"]}";
  // We split the strings by commas, which we had to first convert our string to char first
  // Then simply concatination no black magic here
  return jsonData;
}

// Traverses the whole basket to find the desired item. If it is in the basket returns true
boolean is_inBasket(String rfid){
  for(int i = 0; i<itemCount+1; i++){
    if(tagID[i].equals(rfid))
      return true;
  }
  return false;
}

// Gets the index of the item in the tagID array.
int get_tagIndex(String rfid){
  for(int i = 0; i<itemCount+1; i++){
    if(tagID[i].equals(rfid))
      return i;
  }
}

// Adds or deletes the desired item from the basket.
void changeBasket(String tempID){
   // Check whether the RFID UID is in the basket. If not add it, if it is delete it 
   if(!is_inBasket(tempID)){
    tagID[itemCount] = tempID;     
    itemCount++;
  
    // Set the read as 1
    digitalWrite(2,HIGH);
    delay(1000);
    digitalWrite(2,LOW);
    Serial.println("");
    Serial.println(createJsonData());
  } else{
    int index = get_tagIndex(tempID);

    // Traverse the following indexes and shift them by one, deal with first and the last terms.
    if(index == itemCount-1){
      // If it is the last item just delete it.
      tagID[index] = "";
    } else{
      // If it is one of the middle or the first term, shift them all and assign the last as blank
      for(int i = index+1; i<itemCount; i++){
        tagID[i-1] = tagID[i];
      }
      tagID[itemCount-1] = "";
    }

    digitalWrite(4,HIGH);
    delay(1000);
    digitalWrite(4,LOW);
    // Don't forget to decrease the item count.
    itemCount--;
    Serial.println(createJsonData());    
  }
}