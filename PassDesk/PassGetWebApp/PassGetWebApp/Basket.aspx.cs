using MysqlDatabase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using BasketDatabaseMysql;
using System.Timers;

namespace PassGetWebApp
{
    public partial class Basket1 : System.Web.UI.Page
    {
        DBConnect db;
        System.Timers.Timer aTimer;
        BasketDatabase basketDB;
        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
            basketDB = new BasketDatabase();
            task();
        }

        protected void addRowsBasketTable2(object sender, EventArgs e)
        {

            
            TableRow row = new TableRow();
            TableCell cell1 = new TableCell();
            List<string> list = db.getBasketProducts();
            
            cell1.Text = "M-A-K-01";
            row.Cells.Add(cell1);
            for (int i = 0; i < list.Count(); i++)
            {
                TableCell cell = new TableCell();
                cell.Text = list[i];
                row.Cells.Add(cell);
            }

            Table1.Rows.Add(row);


        }
        protected void addRowsBasketTable(object sender, EventArgs e)
        {
            TableRow row = new TableRow();
            TableCell cell1 = new TableCell();

            cell1.Text = "<a href ='#basketModel'>" + "M-A-K-01" + "</a >";



            row.Cells.Add(cell1);

            basketTable.Rows.Add(row);


        }
        public void task()
        {

            System.Timers.Timer aTimer = new System.Timers.Timer();
            aTimer.Elapsed += new ElapsedEventHandler(OnTimedEvent);
            // Set the Interval to 5 seconds.
            aTimer.Interval = 4000;
            aTimer.Enabled = true;
        }
        private void OnTimedEvent(object source, ElapsedEventArgs e)
        {
            List<string> takesList = db.basketProduct();
            List<string> basketIDs = new List<string>();
            List<SpecificBasket> baskets = new List<SpecificBasket>();
            //db.deleteTakes();
            //List<bool> falseFlag = new List<bool>();
            basketIDs = basketDB.getBasketsList();

            foreach (var id in basketIDs)
            {
                baskets.Add(basketDB.GetBasket(id));
            }
            foreach (var bask in baskets)
            {
                for (int i = 0; bask != null && i < bask.items.Length; i++)
                {
                    if (!takesList.Contains(bask.items[i]))
                    {
                        db.addProductToBasketOneByOne(bask.passgetID, bask.items[i]);
                    }

                }
                for (int i = 0; takesList != null  && i < takesList.Count; i++)
                {
                    if (!bask.items.Contains(takesList[i]))
                    {
                        db.DeleteTakesItem(takesList[i]);
                    }
                }
                    //for (int i = 0; bask.items.Length > i; i++)
                    //db.addProductsToBasket(bask.passgetID, bask.items);
                }
            
            takesList = null;
            basketIDs = null;
            baskets = null;
            
        }
    }
}