using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MySql.Data.MySqlClient;
using MysqlDatabase;

namespace PassGetWebApp
{

    
    public partial class adminPage : System.Web.UI.Page
    {
        Class1 client;
        DBConnect db;
        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
            client = new Class1();
            client.createClient();
        }


     

        protected void btnKayit_Click(object sender, EventArgs e)
        {

            //ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + Class1.mycontent+"aşkımmm" + "');", true);
            client.JSON();

        }

        protected void TextBox1_TextChanged(object sender, EventArgs e)
        {

        }

        protected void Button3_Click(object sender, EventArgs e)
        {

            db.InsertObject(TextBox1.Text, int.Parse(TextBox2.Text), TextBox3.Text);

          

            
        }
        protected void Searchbtn(object sender, EventArgs e)
        {
            string result = "No Product with that name =>"+ txtArama.Text;
            List < string > list= db.searchProduct(txtArama.Text);   
            if(list.Count == 0)
            {
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + result + "');", true);
            }
            else
            {
                result = null;
                foreach (string product in list)
                    //result = product + Environment.NewLine + result;
                    result = product + "\\n" + result;


                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + result + "');", true);

            }


            //ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + result + "');", true);
        }
    }
}