#include <SoftwareSerial.h>
SoftwareSerial wifiConnection(5,6);
#include <SPI.h>
#include <MFRC522.h>
#include <EEPROM.h>
#include <stdlib.h>

const String wireless_name = "babako"; // Please enter the local wireless SSID which the WebApp lies
const String wireless_pwn = "bun12345"; // And the password for it of course
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
  Serial.begin(9600);
  SPI.begin();
  wifiConnection.begin(9600);
  delay(2000);

  // Initiate MFRC522 RFID reader
  mfrc522.PCD_Init();

  // Pin 2 and 4 is the indicator for the RFID reads. 2 being READ, 4 being DELETED. 
  pinMode(2,OUTPUT);
  pinMode(4,OUTPUT);
  pinMode(3,OUTPUT);

  delay(500);
  delay(1000);

  if(!connectInternet()){
    Serial.println("internet gg");
  } else  {
    digitalWrite(3,HIGH);
    Serial.println("Internet OK!");
  }
}

void loop() {
  successRead = getID();
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
  sendToServer(tempID);  
}

String sendATCommand(String ATcommand, int timeout, boolean debug){
  wifiConnection.println(ATcommand);
  long int myTime = millis();
  String cevap = "";

  while((myTime + timeout) > millis()){
    while(wifiConnection.available()){
      char k = wifiConnection.read();
      cevap += k;
    }
    if(cevap.indexOf("OK") != -1)
      break;
  }
  if(debug){
    Serial.println("Cevap: " + cevap);
  }
  return cevap;
}

String sendSTARTCommand(String ATcommand, int timeout, boolean debug){
  wifiConnection.println(ATcommand);
  long int myTime = millis();
  String cevap = "";

  while((myTime + timeout) > millis()){
    while(wifiConnection.available()){
      char k = wifiConnection.read();
      cevap += k;
    }
    if(cevap.indexOf("Link") != -1 || cevap.indexOf("ALREADY LINKED") != -1)
      break;
  }
  if(debug){
    Serial.println("Cevap: " + cevap);
  }
  return cevap;
}

void sendToServer(String rfid){
  String header;
  
  Serial.print("HELLO");
  sendSTARTCommand("AT+CIPSTART=\"TCP\",\"139.179.55.92\",80",5000,true);
    
  header = "GET /passget.php?";
  String metin = createJsonData();  
  
  header += "passid="+ passgetID + "&passjson=" + metin;
  int hlength = header.length()+4;
  Serial.print(hlength);
  sendATCommand("AT+CIPSEND=" + String(hlength),2000,true);
  delay(2000);
  sendATCommand(header+"\r\n",5000,true);
  delay(1000);
  sendATCommand("AT+CIPCLOSE",2000,true);
}

boolean connectInternet(){
  sendATCommand("AT+CWMODE=1", 1000, true);
  sendATCommand("AT+CWJAP=\""+wireless_name+"\",\""+wireless_pwn+"\"",10000,true);
  delay(2000);
  String durum = "";
  durum = sendATCommand("AT+CIFSR", 30000, true);
  if(durum.indexOf("FAIL") != -1)
    return false;
  return true;
}
