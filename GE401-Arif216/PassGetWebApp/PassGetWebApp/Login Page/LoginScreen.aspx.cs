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

        protected void login_Click(object sender, EventArgs e)
        {
           int id = int.Parse(nameID.Value);
           string pass = passID.Value;

           if (db.checkAccount(id, pass))
           {
               if (db.checkAdmin(id))
                   Response.Redirect("~/Admin Page/adminPage.aspx");
               else
                   Response.Redirect("~/loginScreen.aspx");
           }
        }



    }
}