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

namespace PassGetWebApp
{
    class TestJson
    {
        
        public int age { get; set; }
        public string name { get; set; }
        public string[] messages { get; set; }
    }
    public class Class1
    {
       // private HttpClient client;
        public static string mycontent;
        //WebClient client = new WebClient();

        JsonSerializer serializer = new JsonSerializer();
        

        public void createClient()
        {
            getRequest();
           // Thread.Sleep(2000);
         



        }
        public void JSON()
        {
            TestJson temp = JsonConvert.DeserializeObject<TestJson>(mycontent);
            
            Console.Write(temp.name);


        }
        public static string getmy()
        {
            return mycontent;
        }
        public void getRequest()
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