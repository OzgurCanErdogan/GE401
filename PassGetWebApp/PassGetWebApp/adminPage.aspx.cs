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
            
            if(db.searchProduct(txtArama.Text) != null)
                ClientScript.RegisterStartupScript(this.GetType(), "myalert", "alert('" + db.searchProduct(txtArama.Text) + "');", true);
        }
    }
}