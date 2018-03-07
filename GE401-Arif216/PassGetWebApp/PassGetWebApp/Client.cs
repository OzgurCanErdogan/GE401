using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Net.Http;
using System.Web.Script.Serialization;
using Newtonsoft.Json;
using System.Threading;
using System.Net;
using System.IO;
using MysqlDatabase;

namespace PassGetWebApp
{
   
    public class Client
    {
        //private HttpClient client;
        public static String mycontent;
        //WebClient client = new WebClient();

        JsonSerializer serializer = new JsonSerializer();
        DBConnect db;


        private JsonPassget JSON()
        {
            JsonPassget temp = JsonConvert.DeserializeObject<JsonPassget>(mycontent);

            if (temp != null)
            {
                db = new DBConnect();
                db.addUserProduct(temp.passgetID, 213, temp.items);
                return temp;
            }
                
            else
                return null;


        }
        public static string getmy()
        {
            return mycontent;
        }
        private void getRequest()
        {
            WebRequest request = WebRequest.Create("http://localhost/test.json");
            WebResponse response = request.GetResponse();
            Stream data = response.GetResponseStream();
            mycontent = String.Empty;
            using (StreamReader sr = new StreamReader(data))
            {
                mycontent = sr.ReadToEnd();
            }

        }

        public JsonPassget getProducts()
        {

            getRequest();

            return JSON();



        }
        
      /*  async void GetRequest()
        {

            using (client = new HttpClient())
            {
                using (HttpResponseMessage response = await client.GetAsync("http://localhost/test.json") )
                {
                    using (HttpContent content = response.Content)
                    {
                        mycontent = await content.ReadAsStringAsync();
                        
                
                    }
                        

                }
                    

            }


            


        }*/

    }
}