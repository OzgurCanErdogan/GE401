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
        DBConnect db;
        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
        }

        protected void btnKayit_Click(object sender, EventArgs e)
        {




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
            string result = null;
            List < string > list= db.searchProduct(txtArama.Text);   
            if(list != null)
            {

                foreach (string product in list)
                    //result = product + Environment.NewLine + result;
                    result = product + "\\n" + result;
                

                //ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + product + "');", true);
            }

            ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + result + "');", true);
        }
    }
}