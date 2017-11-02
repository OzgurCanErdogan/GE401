using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MysqlDatabase;

namespace PassGetWebApp
{
    public partial class LoginScreen : System.Web.UI.Page
    {
        DBConnect db;
        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
        }

        protected void Login_Click(object sender, EventArgs e)
        {
            int id = int.Parse(UserID.Text);
            string pass = Password.Text;
            
            if (db.checkAccount(id, pass))
            {
                if (db.checkAdmin(id))
                    Response.Redirect("~/adminPage.aspx");
                else
                    Response.Redirect("~/MainPage.aspx");
            }
           
            
        }
    }
}