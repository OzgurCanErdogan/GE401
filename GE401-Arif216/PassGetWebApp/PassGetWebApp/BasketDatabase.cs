using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;
using Newtonsoft.Json;

namespace BasketDatabaseMysql
{
    public class SpecificBasket
    {
        public string passgetID { get; set; }
        public string[] items { get; set; }
    }
    class BasketDatabase
    {
        private MySqlConnection connection;
        private string server;
        private string database;
        private string uid;
        private string password;
        static string ozgur;


        
        //Constructor
        public BasketDatabase()
        {
            Initialize();
        }

        //Initialize values
        private void Initialize()
        {
            server = "160.153.153.152";
            database = "babako";
            uid = "csboiz";
            password = "bartubartu";
            string connectionString;
            connectionString = "SERVER=" + server + ";" + "DATABASE=" +
            database + ";" + "UID=" + uid + ";" + "PASSWORD=" + password + ";";

            connection = new MySqlConnection(connectionString);
        }

        //open connection to database
        private bool OpenConnection()
        {
            try
            {
                connection.Open();
                return true;
            }
            catch (MySqlException ex)
            {
                //When handling errors, you can your application's response based 
                //on the error number.
                //The two most common error numbers when connecting are as follows:
                //0: Cannot connect to server.
                //1045: Invalid user name and/or password.
                switch (ex.Number)
                {
                    case 0:
                        Console.WriteLine("Cannot connect to server.  Contact administrator");
                        break;

                    case 1045:
                        Console.WriteLine("Invalid username/password, please try again");
                        break;
                }
                return false;
            }
        }

        //Close connection
        private bool CloseConnection()
        {
            try
            {
                connection.Close();
                return true;
            }
            catch (MySqlException ex)
            {
                Console.WriteLine(ex.Message);
                return false;
            }
        }
        
        //Delete statement
        public void Delete()
        {
            string query = "DELETE FROM tableinfo WHERE name='John Smith'";

            if (this.OpenConnection() == true)
            {
                MySqlCommand cmd = new MySqlCommand(query, connection);
                cmd.ExecuteNonQuery();
                this.CloseConnection();
            }
        }
        public List<string> getBasketsList()
        {
            List<string> basketIDs = new List<string>();
            string query = "SELECT * FROM passget";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            while (dataReader.Read())
            {
                basketIDs.Add((string)dataReader["passget_id"]);
            }
            dataReader.Close();
            this.CloseConnection();

            return basketIDs;
        }
        public string getJson(string basket)
        {
            string query = "SELECT * FROM passget WHERE passget_id = '"+ basket+" '";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            string result = null;
            while (dataReader.Read())
            {
                result = (string)dataReader["passget_json"];
            }
            dataReader.Close();
            this.CloseConnection();

            return result;
        }

        public SpecificBasket GetBasket(string basket) {
            SpecificBasket userBasket = new SpecificBasket();
            userBasket = JsonConvert.DeserializeObject<SpecificBasket>(getJson(basket));
            return userBasket;
        }


        public List<string> searchProduct(string word)
        {
            string query = "SELECT * FROM bitirme.Products WHERE type LIKE '%" + word + "%'";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();
            //string result;

            while (dataReader.Read())
            {
                result.Add((string)dataReader["type"]);
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }
        


        //Select statement
        //public List<string>[] Select()
        //{
        //}

        ////Count statement
        //public int Count()
        //{
        //}

        //Backup
        public void Backup()
        {
        }

        //Restore
        public void Restore()
        {
        }
    }
}