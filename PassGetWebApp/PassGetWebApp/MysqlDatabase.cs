using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MysqlDatabase
{
    class DBConnect
    {
        private MySqlConnection connection;
        private string server;
        private string database;
        private string uid;
        private string password;

        //Constructor
        public DBConnect()
        {
            Initialize();
        }

        //Initialize values
        private void Initialize()
        {
            server = "bitirme.cuhyc2prztfl.eu-west-1.rds.amazonaws.com";
            database = "bitirme";
            uid = "bitirme";
            password = "01480148";
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

        //Insert statement
        public void Insert()
        {
            string query = "INSERT INTO Customers (idCustomers, Name, Surname, age, gender, city) VALUES('21300', 'Ozgur', 'Erdogan', '22', 'erkek', 'ankara')";

            //open connection
            if (this.OpenConnection() == true)
            {
                //create command and assign the query and connection from the constructor
                MySqlCommand cmd = new MySqlCommand(query, connection);

                //Execute command
                cmd.ExecuteNonQuery();

                //close connection
                this.CloseConnection();
            }
        }
        public void InsertObject(string type, int id, string brand)
        {
            

            OpenConnection();
            string query = "INSERT INTO Products(idProducts, type, brand) VALUES(@idProducts, @type, @brand)";
            MySqlCommand cmd = new MySqlCommand(query, connection);
            cmd.Parameters.AddWithValue("@type", type);
            cmd.Parameters.AddWithValue("@idProducts", id);
            cmd.Parameters.AddWithValue("@brand", brand);
            cmd.ExecuteNonQuery();
            connection.Close();
        
        }

        //Update statement
        public void Update()
        {
            string query = "UPDATE tableinfo SET name='Joe', age='22' WHERE name='John Smith'";

            //Open connection
            if (this.OpenConnection() == true)
            {
                //create mysql command
                MySqlCommand cmd = new MySqlCommand();
                //Assign the query using CommandText
                cmd.CommandText = query;
                //Assign the connection using Connection
                cmd.Connection = connection;

                //Execute query
                cmd.ExecuteNonQuery();

                //close connection
                this.CloseConnection();
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

        public bool checkAccount(int ID, string Password)
        {
            string query = "SELECT * FROM bitirme.Users WHERE userID = " + ID ;
            string updateQuery = "UPDATE bitirme.Users SET status = 'online' WHERE userID ="+ID; 
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            int IDcheck = 0;
            string passwordCheck = "";
            while (dataReader.Read())
            {
                IDcheck = (int)dataReader["userID"];
                passwordCheck = dataReader["password"].ToString();
            }
            dataReader.Close();
           
            if (ID == IDcheck && Password == passwordCheck)
            {

               cmd = new MySqlCommand(updateQuery, connection);
               cmd.ExecuteNonQuery();
               dataReader.Close();
               this.CloseConnection();
               return true;
            }
            this.CloseConnection();
            return false;
            
        }
        public bool checkAdmin(int ID)
        {
            string query = "SELECT * FROM bitirme.admin WHERE adminID = " + ID;
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            int IDcheck = 0;
            while (dataReader.Read())
            {
                IDcheck = (int)dataReader["adminID"];

            }
            dataReader.Close();
            this.CloseConnection();
            if (ID == IDcheck)
                return true;
            return false;
        }
        public List<string> searchProduct(string word)
        {
            string query = "SELECT * FROM bitirme.Products WHERE type LIKE '%"+word+"%'" ;
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