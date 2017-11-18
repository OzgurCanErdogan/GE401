#include <SPI.h>
#include <MFRC522.h>
#include <EEPROM.h>

#define wireless_name "babako"
#define wireless_pwn "bun12345"
#define RST_PIN 9
#define SS_PIN 10

byte readCard[4];
int successRead;
String tagID;
int itemCount = 0;
const String passgetID = "M-A-K-01";

MFRC522 mfrc522(SS_PIN, RST_PIN);

MFRC522::MIFARE_Key key;

void setup() {
  Serial.begin(115200);
  SPI.begin();
  mfrc522.PCD_Init();
  Serial.println("AT"); 
  pinMode(13,OUTPUT);
  delay(3000);

  if(Serial.find("OK")){         //esp modülü ile bağlantıyı kurabilmişsek modül "AT" komutuna "OK" komutu ile geri dönüş yapıyor.
     Serial.println("AT+CWMODE=1"); //esp modülümüzün WiFi modunu STA şekline getiriyoruz. Bu mod ile modülümüz başka ağlara bağlanabilecek.
     delay(2000);
     String baglantiKomutu=String("AT+CWJAP=\"")+wireless_name+"\",\""+wireless_pwn+"\"";
    Serial.println(baglantiKomutu);
     delay(5000);
  }
  
  Serial.print("AT+CIPMUX=1\r\n");
  delay(2000);
  Serial.print("AT+CIPSERVER=1,80\r\n");
  delay(1000);

}

void loop() {

  do {
    successRead = getID();
  } while (!successRead);
  
  if(Serial.available()>0){
    if(Serial.find("+IPD,")){

      char itemsChar[tagID.length()+1];
      tagID.toCharArray(itemsChar,tagID.length()+1);
      // Create a new String array to seperate the item UIDs
      String items[itemCount] = strtok(itemsChar, ",");
      String jsonData = "{\"passgetID\":\"" + passgetID + "\",\"items\":[";

      for(int i = 0; i<itemCount-1; i++){
        jsonData += "\""+items[i]+"\",";
      }
      jsonData += "\""+items[itemCount-1]+"\"]}";
      
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
  
  //yeni bir kart okunmadıysa 0 döndür
  if ( ! mfrc522.PICC_IsNewCardPresent()) { 
    return 0;
  }
  if ( ! mfrc522.PICC_ReadCardSerial()) {
    return 0;
  }
  
  Serial.print("Kart UID'si: ");
  //kartın UID'sini byte byte oku ve seri monitöre yaz
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
  Serial.println("");
  //kart okumayı durdur ve 1 döndür (okuma başarılı)
  mfrc522.PICC_HaltA();
  return 1;
}
