using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MySql.Data.MySqlClient;
using MysqlDatabase;
using System.Collections;
using BasketDatabaseMysql;
using System.Timers;
using System.Web.UI.HtmlControls;

namespace PassGetWebApp
{

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
    public partial class ProductPage : System.Web.UI.Page
    {
        

        Dictionary<string, string> productList = new Dictionary<string, string>();
        DBConnect db;
        BasketDatabase basketDB;
        System.Timers.Timer aTimer;
        List<string> product;

        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
            basketDB = new BasketDatabase();


            
            product = db.bringAllProducts();
            //productList = db.bringAllProductsAsList();
            for (int i = 0; i < product.Count; i = i + 6)
            {

                HtmlTableCell cell1 = new HtmlTableCell();
                HtmlTableCell cell2 = new HtmlTableCell();
                HtmlTableCell cell3 = new HtmlTableCell();
                HtmlTableCell cell4 = new HtmlTableCell();
                

                cell1.InnerText = product[i];
                cell2.InnerText = product[i+3];
                cell3.InnerText = "1";
                cell4.InnerText = "1";


                HtmlTableRow row = new HtmlTableRow();
                row.Controls.Add(cell1);
                row.Controls.Add(cell2);
                row.Controls.Add(cell3);
                row.Controls.Add(cell4);
                row.Attributes.Add("class", "product");
                ProductInnerTable.Rows.Add(row);
            }
            ////////for (int i=0; i<product.Count; i = i + 6)
            ////////{
            ////////    Button btn = new Button();
            ////////    btn.ID = product[i];
            ////////    btn.BackColor = System.Drawing.Color.Green;
            ////////    btn.ForeColor = System.Drawing.Color.Yellow;
            ////////    btn.Click += new EventHandler(sub_click);
            ////////    TableCell cell1 = new TableCell();
            ////////    TableCell cell2 = new TableCell();
            ////////    TableCell cell3 = new TableCell();
            ////////    TableCell cell4 = new TableCell();
            ////////    //cell.Text = product.ElementAt(i);
            ////////    btn.Text = "Detaylar";


            ////////    //btn.Style["background-color"] = "Red";
            ////////    //btn.CssClass = "ProductTable";
            ////////    //btn.Style.Add("ProductTable", "Red");
            ////////    cell1.Text = product[i];
            ////////    //cell1.Controls.Add(btn);
            ////////    cell2.Text = product[i+1];
            ////////    cell3.Text = product[i+2];
            ////////    cell4.Controls.Add(btn);
            ////////    TableRow row2 = new TableRow();
            ////////    //row2.Controls.Add(btn);

            ////////    row2.Controls.Add(cell1);

            ////////    row2.Controls.Add(cell2);
            ////////    row2.Controls.Add(cell3);
            ////////    row2.Controls.Add(cell4);
            ////////    ProductTable.Controls.Add(row2);
            ////////}

            //foreach(var data in product)
            //{
            //    product.
            //    TableCell cell = new TableCell();
            //    cell.Text = data;
            //    TableRow row2 = new TableRow();
            //    row2.Controls.Add(cell);
            //    ProductTable.Controls.Add(row2);
            //}
            ////task();
        }

        protected void addProduct(object sender, EventArgs e)
        {
            string id = productIDinput.Value;
            string type = productTypeInput.Value;
            string brand = "Mavi";
            string name = productNameInput.Value;
            string size = productBedenInput.Value;
            double price = double.Parse(productPriceInput.Value, System.Globalization.CultureInfo.InvariantCulture);


            //Page.ClientScript.RegisterStartupScript(GetType(), "msgbox", "alert('Ürün başarıyla eklendi');", true);

            db.addProducttoInvent(id, type, brand, name, size, price);
            Response.Redirect("ProductPage.aspx");
        }
        ////public void task()
        ////{

        ////    System.Timers.Timer aTimer = new System.Timers.Timer();
        ////    aTimer.Elapsed += new ElapsedEventHandler(OnTimedEvent);
        ////    // Set the Interval to 5 seconds.
        ////    aTimer.Interval = 2000;
        ////    aTimer.Enabled = true;
        ////}
        ////private void OnTimedEvent(object source, ElapsedEventArgs e)
        ////{
        ////    List<string> takesList = db.basketProduct();
        ////    List<string> basketIDs = new List<string>();
        ////    List<SpecificBasket> baskets = new List<SpecificBasket>();
        ////    //db.deleteTakes();
        ////    //List<bool> falseFlag = new List<bool>();
        ////    basketIDs = basketDB.getBasketsList();

        ////    foreach (var id in basketIDs)
        ////    {
        ////        baskets.Add(basketDB.GetBasket(id));
        ////    }
        ////    foreach (var bask in baskets)
        ////    {
        ////        for (int i = 0; i < bask.items.Length; i++)
        ////        {
        ////            if (!takesList.Contains(bask.items[i]))
        ////            {
        ////                db.addProductToBasketOneByOne(bask.passgetID, bask.items[i]);
        ////            }

        ////        }
        ////        for (int i = 0; i < takesList.Count; i++)
        ////        {
        ////            if (!bask.items.Contains(takesList[i]))
        ////            {
        ////                db.DeleteTakesItem(takesList[i]);
        ////            }
        ////        }
        ////        //for (int i = 0; bask.items.Length > i; i++)
        ////        //db.addProductsToBasket(bask.passgetID, bask.items);
        ////        takesList = null;
        ////        basketIDs = null;
        ////        baskets = null;
        ////    }
        ////}

        //protected void sub_click(object sender, EventArgs e)
        //{
        //    Button btnSend = (sender as Button);
        //    //db = new DBConnect();
        //    //Dictionary<string, Products> specificProductList = new Dictionary<string, Products>();
        //    //specificProductList = db.bringSpecificProduct()
        //    //btnShow = btnSend;
        //    //mp1.TargetControlID = btnSend.ID;
        //    int temp = -1;
        //    for(int i=0; i < product.Count; i++)
        //    {
        //        if (product[i] == btnSend.ID)
        //            temp = i;
        //    }

        //    mp1.TargetControlID = btnSend.ID;
        //    PanelText.Text = product[temp];
        //    Label2.Text = "Ürün Tipi: " + product[temp+1];
        //    Label3.Text = "Marka: " + product[temp+2];
        //    Label4.Text = "Ürün İsmi: " + product[temp + 3];
        //    Label5.Text = "Beden: " + product[temp + 4];
        //    Label6.Text = "Fiyat: " + product[temp + 5];
        //    //PanelText.Text = "Hi";
        //    mp1.Show();







        //    //ClientScript.RegisterStartupScript(GetType(), "failed", string.Format("alert({0});", AntiXss.JavaScriptEncode(error)), true);
        //}
        //protected void send_ServerClick(object sender, EventArgs e)
        //{
        //    string id = idProduct.Value;
        //    string type = Producttype.Value;
        //    string brand = brandProduct.Value;
        //    string name = namePro.Value;
        //    string size = sizePro.Value;
        //    double price = double.Parse(pricePro.Value, System.Globalization.CultureInfo.InvariantCulture);


        //    Page.ClientScript.RegisterStartupScript(GetType(), "msgbox", "alert('Ürün başarıyla eklendi');", true);

        //    db.addProducttoInvent(id, type, brand, name, size, price);
        //}




    }
}