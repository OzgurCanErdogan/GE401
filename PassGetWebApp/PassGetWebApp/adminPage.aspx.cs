using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MySql.Data.MySqlClient;
namespace PassGetWebApp
{
    public partial class adminPage : System.Web.UI.Page
    {
        private MySqlConnection connection;
        private string server = "bitirme.cuhyc2prztfl.eu-west-1.rds.amazonaws.com";
        private string database = "bitirme";
        private string uid = "bitirme";
        private string password = "01480148";
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void btnKayit_Click(object sender, EventArgs e)
        {




        }

        protected void TextBox1_TextChanged(object sender, EventArgs e)
        {

        }

        protected void Button3_Click(object sender, EventArgs e)
        {
            string connectionString;
            connectionString = "SERVER=" + server + ";" + "DATABASE=" +
            database + ";" + "UID=" + uid + ";" + "PASSWORD=" + password + ";";

            connection = new MySqlConnection(connectionString);

            string query = "INSERT INTO Products(idProducts, type, brand) VALUES(@idProducts, @type, @brand)";
            MySqlCommand cmd = new MySqlCommand(query, connection);
            connection.Open();
            cmd.Parameters.AddWithValue("@type", TextBox1.Text);
            cmd.Parameters.AddWithValue("@idProducts", TextBox2.Text);
            cmd.Parameters.AddWithValue("@brand", TextBox3.Text);
            cmd.ExecuteNonQuery();
            connection.Close();
        }
    }
}