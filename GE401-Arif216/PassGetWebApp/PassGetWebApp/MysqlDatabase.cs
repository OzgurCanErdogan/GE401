using MySql.Data.MySqlClient;
using PassGetWebApp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace MysqlDatabase
{
    //DENEME 123456
    class DBConnect
    {
        private MySqlConnection connection;
        private string server;
        private string database;
        private string uid;
        private string password;
        static string ozgur;


        public struct ProductSpecfic
        {
            public string id { get; set; }
            public string type { get; set; }
            public string brand { get; set; }
        }
        public class MyDictionary : Dictionary<string, ProductSpecfic>
        {
            public void Add(string type, string id, string brand)
            {
                ProductSpecfic productSpec = new ProductSpecfic();
                productSpec.id = id;
                productSpec.type = type;
                productSpec.brand = brand;
                this.Add(type, productSpec);
            }
        }
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
            //Benim adım Özgürsas
            string query = "INSERT INTO Customers (idCustomers, Name, Surname, age, gender, city) VALUES('21300', 'Ozgur', 'Erdogan', '22', 'erkek', 'ankara')";

            //open connection
            if (this.OpenConnection() == true)
            {
                //ben yaptım oldu zaaşş
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






        public bool checkAccount(int ID, string Password)
        {
            string query = "SELECT * FROM bitirme.Users WHERE userID = " + ID;
            string updateQuery = "UPDATE bitirme.Users SET status = 'online' WHERE userID =" + ID;
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
        public struct stat
        {
            public string name;
            public int amount;

        }

        public List<stat> getProductStat()
        {
            string query = "SELECT * FROM bitirme.amountperproductName;";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<stat> result = new List<stat>();

            while (dataReader.Read())
            {
                stat i1 = new stat();
                i1.name = (string)dataReader["name"];
                i1.amount = Int32.Parse(dataReader["amount"].ToString());
                result.Add(i1);
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }
        public List<string> bringAllProducts()
        {
            string query = "SELECT * FROM bitirme.Products";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();
            //string result;

            while (dataReader.Read())
            {
                result.Add((string)dataReader["idProducts"]);
                result.Add((string)dataReader["type"]);
                result.Add((string)dataReader["brand"]);
                result.Add((string)dataReader["name"]);
                result.Add((string)dataReader["size"]);
                result.Add((string)dataReader["price"].ToString());
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }
        public List<string> bringOneProduct(string id) // LABA YAPILDI
        {
            string query = "SELECT * FROM bitirme.Products WHERE idProducts LIKE '%" + id + "%'";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add((string)dataReader["idProducts"]);
                result.Add((string)dataReader["type"]);
                result.Add((string)dataReader["name"]);
                result.Add((string)dataReader["size"]);
                result.Add((string)dataReader["price"].ToString());
                result.Add((string)dataReader["StockAmount"].ToString());
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }
        //Stock dan ürün ismini çeker.
        public List<string> bringProductFromStock(string id) // LABA YAPILDI
        {
            string query = "SELECT * FROM bitirme.stock WHERE RFID LIKE '%" + id + "%'";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add((string)dataReader["idProducts"]);
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }


        public Dictionary<string, string> bringAllProductsAsList()
        {
            string query = "SELECT * FROM bitirme.Products";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            Dictionary<string, string> result = new Dictionary<string, string>();
            //string result;

            while (dataReader.Read())
            {
                result.Add((string)dataReader["brand"], (string)dataReader["idProducts"].ToString());
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }
        public Dictionary<string, ProductSpecfic> bringSpecificProduct(int productID)
        {
            string query = "SELECT * FROM bitirme.Products WHERE idProducts = " + productID;
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            Dictionary<string, ProductSpecfic> result = new Dictionary<string, ProductSpecfic>();
            //string result;
            ProductSpecfic productSpec = new ProductSpecfic();

            while (dataReader.Read())
            {
                productSpec.id = (string)dataReader["idProducts"].ToString();
                productSpec.type = (string)dataReader["type"].ToString();
                productSpec.brand = (string)dataReader["brand"].ToString();
                result.Add((string)dataReader["type"], productSpec);
            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }
        public List<string> getOnlineUser()
        {
            string query = "SELECT name FROM bitirme.Users WHERE status = 'online'";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add((string)dataReader["name"]);
            }
            return result;

        }
        public void addUserProduct(String basketID, int userID, String[] products)
        {
            String product = "";
            OpenConnection();

            for (int i = 0; i < products.Length; i++)
            {
                product = products[i];
                string query = "INSERT INTO `bitirme`.`takes` (`userID`, `idBasket`, `idProducts`) VALUES(" + userID + ",'" + basketID + "','" + product + "')";

                MySqlCommand cmd = new MySqlCommand(query, connection);
                cmd.ExecuteNonQuery();

            }


            this.CloseConnection();


        }
        public void addProductsToBasket(String basketID, String[] products)
        {
            String product = "";
            OpenConnection();

            for (int i = 0; i < products.Length; i++)
            {
                product = products[i];
                //string query = "INSERT INTO takes(basketID, productID) VALUES(@basketID, @idProducts)";
                string query = "INSERT INTO bitirme.takes(basketID, productID) SELECT * FROM (SELECT @basketID, @idProducts) AS tmp WHERE NOT EXISTS(SELECT productID FROM takes WHERE productID = @idProducts) LIMIT 1";
                MySqlCommand cmd = new MySqlCommand(query, connection);
                cmd.Parameters.AddWithValue("@basketID", basketID);
                cmd.Parameters.AddWithValue("@idProducts", product);
                cmd.ExecuteNonQuery();
            }


            this.CloseConnection();


        }
        public void addProductToBasketOneByOne(string basketID, string products)
        {
            String product = products;
            OpenConnection();


            //string query = "INSERT INTO takes(basketID, productID) VALUES(@basketID, @idProducts)";
            string query = "INSERT INTO bitirme.takes(basketID, productID) SELECT * FROM (SELECT @basketID, @idProducts) AS tmp WHERE NOT EXISTS(SELECT productID FROM takes WHERE productID = @idProducts) LIMIT 1";
            MySqlCommand cmd = new MySqlCommand(query, connection);
            cmd.Parameters.AddWithValue("@basketID", basketID);
            cmd.Parameters.AddWithValue("@idProducts", product);
            cmd.ExecuteNonQuery();



            this.CloseConnection();


        }
        public void deleteTakes()
        {
            OpenConnection();

            //string query = "INSERT INTO takes(basketID, productID) VALUES(@basketID, @idProducts)";
            string query = "truncate bitirme.takes;";
            MySqlCommand cmd = new MySqlCommand(query, connection);
            cmd.ExecuteNonQuery();
            this.CloseConnection();



        }
        public List<string> basketNames() // BU SADECE LAB İÇİN EKLENDİ DÜZENLENECEK
        {
            string query = "SELECT * FROM bitirme.takes";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();
            //string result;

            while (dataReader.Read())
            {
                string temp = (string)dataReader["basketID"].ToString();
                if (!result.Contains(temp))
                    result.Add(temp);
            }
            dataReader.Close();
            this.CloseConnection();
            return result;

        }
        public List<string> basketProductSpec(string id) // BU SADECE LAB İÇİN EKLENDİ DÜZENLENECEK
        {
            string query = "SELECT * FROM bitirme.takes WHERE basketID LIKE '%" + id + "%'";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();
            //string result;

            while (dataReader.Read())
            {
                string temp = (string)dataReader["RFID"].ToString();
                if (!result.Contains(temp))
                    result.Add(temp);
            }
            dataReader.Close();
            this.CloseConnection();
            return result;

        }
        public List<string> basketProduct()
        {
            string query = "SELECT * FROM bitirme.takes";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();
            //string result;

            while (dataReader.Read())
            {
                result.Add((string)dataReader["productID"].ToString());
            }
            dataReader.Close();
            this.CloseConnection();
            return result;

        }

        public void DeleteTakesItem(string product)
        {
            string query = "DELETE FROM bitirme.takes WHERE productID = @product";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            cmd.Parameters.AddWithValue("@product", product);
            cmd.ExecuteNonQuery();
            this.CloseConnection();
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
        public List<string> getBasketProducts()
        {

            string query = "SELECT productID FROM bitirme.takes where basketID = 'M-A-K-01'";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add(dataReader["productID"].ToString());

            }
            dataReader.Close();
            this.CloseConnection();
            return result;

        }
        public void addProducttoInvent(string id, string type, string brand, string name, string size, double price)
        {
            string query = "INSERT INTO `bitirme`.`Products` (`idProducts`, `type`, `brand`, `name`, `size`, `price`) VALUES('" + id + "','" + type + "', '" + brand + "', '" + name + "', '" + size + "', '" + price + "');";

            try
            {
                if (this.OpenConnection() == true)
                {
                    MySqlCommand cmd = new MySqlCommand(query, connection);
                    cmd.ExecuteNonQuery();
                    this.CloseConnection();
                }
            }
            catch (Exception e)
            {

            }


        }
        public List<string> mostSellsProducts()
        {

            string query = "SELECT * FROM bitirme.amountWithID;";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add(dataReader["ID"].ToString());
                result.Add(dataReader["name"].ToString());
                result.Add(dataReader["amount"].ToString());

            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }

        public List<string> genderDistribution()
        {

            string query = "SELECT * FROM bitirme.genderDistribution;";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add(dataReader["gender"].ToString());
                result.Add(dataReader["genderCount"].ToString());
                result.Add(dataReader["percentage"].ToString());

            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }

        public List<string> ageDistribution()
        {
            string query = "select age, count, ((count * 100)/ (select sum(count) from (select age, count(age) as count from (SELECT FLOOR(DATEDIFF(NOW(), `bitirme`.`Users`.`year-of-birth`) / 365) as age FROM bitirme.Sold join bitirme.Users on `Users`.`userID` = `Sold`.`UserID` ) as P) as I)) as percentage from ( select age, count(age) as count from ( SELECT FLOOR(DATEDIFF(NOW(), `bitirme`.`Users`.`year-of-birth`) / 365) as age FROM bitirme.Sold join bitirme.Users on `Users`.`userID` = `Sold`.`UserID` ) as T group by age) as Z";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add(dataReader["age"].ToString());
                result.Add(dataReader["count"].ToString());
                result.Add(dataReader["percentage"].ToString());

            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }

        public List<string> getStock()
        {

            string query = "SELECT* FROM bitirme.stockView;";
            OpenConnection();
            MySqlCommand cmd = new MySqlCommand(query, connection);
            MySqlDataReader dataReader = cmd.ExecuteReader();
            List<string> result = new List<string>();

            while (dataReader.Read())
            {
                result.Add(dataReader["idProducts"].ToString());
                result.Add(dataReader["name"].ToString());
                result.Add(dataReader["amount"].ToString());

            }
            dataReader.Close();
            this.CloseConnection();
            return result;
        }


        
    }
}