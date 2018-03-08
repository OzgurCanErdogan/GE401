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
using System.Web.UI.DataVisualization.Charting;
using static MysqlDatabase.DBConnect;
using System.Web.UI.HtmlControls;

namespace PassGetWebApp
{

    
    public partial class adminPage : System.Web.UI.Page
    {
        System.Timers.Timer aTimer;
        Client client;
        DBConnect db;
        BasketDatabase basketDB; 
        static Basket i1 = new Basket();
        private List<string> i2;
        private List<string> i3;
        private List<string> i4;
        private List<string> i5;



        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
            //AHA BURALAR HEP İSTATİSTİK
            ////////////i2 = db.genderDistribution();
            ////////////i3 = db.ageDistribution();
            ////////////i4 = db.getStock();
            ////////////i5 = db.mostSellsProducts();
            ////////////basketDB = new BasketDatabase();
            ////////////client = new Client();
            ////////////db.ageDistribution();

            

            //AHA BURALAR HEP İSTATİSTİK
            //////////////////if (!IsPostBack)
            //////////////////{
            //////////////////    getChartData();
            //////////////////    GetChartTypes();

            //////////////////}
            //task(); DO NOT OPEN
            
            List<string> basketIDs = db.basketNames();
            List<SpecificBasket> baskets = new List<SpecificBasket>();
            //db.deleteTakes();
            //List<bool> falseFlag = new List<bool>();

            foreach (var basketname in basketIDs)
            {

                string newbasket2 = "<div class='active-basket text-center' id=" + basketname + "runat='server' >" +
                            "<img src = 'https://www.erdoganlarbisiklet.com/media/catalog/product/cache/1/thumbnail/100x100/9df78eab33525d08d6e5fb8d27136e95/2/5/250401.jpg' class='active-basket-img' alt='Basket' />" +
                            "<p class='active-basket-no'>Sepet: <span id = 'firstbasket' runat='server'>" + basketname + "</span></p>" +
                            "<p class='text-muted'>Ürünler</p>" +
                            "<p class='active-basket-item'>Ürün: T-shirt</p>" +
                            "<p class='active-basket-item'>Ürün: Kot Pantolon</p>" +
                            "<p class='active-basket-item'>...</p>" +
                        "</div>";
                TableOfBaskets.Controls.Add(new LiteralControl(newbasket2));
                List<string> takesList = db.basketProductSpec(basketname);
                List<string> basketsProducts = new List<string>();
                foreach (var product in takesList)
                {
                    basketsProducts.Add(db.bringProductFromStock(product)[0]);

                }
                foreach(var pro in basketsProducts)
                {
                    List<string> product = db.bringOneProduct(pro);
                    HtmlTableCell cell1 = new HtmlTableCell();
                    HtmlTableCell cell2 = new HtmlTableCell();
                    HtmlTableCell cell3 = new HtmlTableCell();
                    HtmlTableCell cell4 = new HtmlTableCell();
                    HtmlTableCell cell5 = new HtmlTableCell();

                    cell1.InnerText = product[0];
                    cell2.InnerText = product[1];
                    cell3.InnerText = product[2];
                    cell4.InnerText = product[3];
                    cell5.InnerText = product[4];
                    cell5.Attributes.Add("class", "active-basket-item-price");

                    HtmlTableRow row = new HtmlTableRow();
                    row.Controls.Add(cell1);
                    row.Controls.Add(cell2);
                    row.Controls.Add(cell3);
                    row.Controls.Add(cell4);
                    row.Controls.Add(cell5);
                    //row.Attributes.Add("class","active-basket-item-price");
                    innerTable.Rows.Add(row);
                }
                basketsProducts = null;

            }


        }

        protected void smt1(object sender, EventArgs e)
        {
            
            Response.Redirect("ProductPage.aspx");
        }

        //public void task()
        //{

        //    System.Timers.Timer aTimer = new System.Timers.Timer();
        //    aTimer.Elapsed += new ElapsedEventHandler(OnTimedEvent);
        //    // Set the Interval to 5 seconds.
        //    aTimer.Interval = 10000;
        //    aTimer.Enabled = true;
        //}
        //private void OnTimedEvent(object source, ElapsedEventArgs e)
        //{
        //    List<string> takesList = db.basketProduct();
        //    List<string> basketIDs = db.basketNames();
        //    List<SpecificBasket> baskets = new List<SpecificBasket>();
        //    //db.deleteTakes();
        //    //List<bool> falseFlag = new List<bool>();

        //    foreach(var basketname in basketIDs)
        //    {

        //        string newbasket = "<div class='active-basket text-center' id="+ "M-A-K-01" + "runat='server' >" +
        //                    "<img src = 'https://www.erdoganlarbisiklet.com/media/catalog/product/cache/1/thumbnail/100x100/9df78eab33525d08d6e5fb8d27136e95/2/5/250401.jpg' class='active-basket-img' alt='Basket' />" +
        //                    "<p class='active-basket-no'>Sepet: <span id = 'firstbasket' runat='server'>"+ "M-A-K-01" + "</span></p>" +
        //                    "<p class='text-muted'>Ürünler</p>" +
        //                    "<p class='active-basket-item'>Ürün: T-shirt</p>" +
        //                    "<p class='active-basket-item'>Ürün: Kot Pantolon</p>" +
        //                    "<p class='active-basket-item'>...</p>" +
        //                "</div>";
        //        TableOfBaskets.Controls.Add(new LiteralControl(newbasket));

        //        HtmlTableCell cell1 = new HtmlTableCell();
        //        HtmlTableCell cell2 = new HtmlTableCell();
        //        HtmlTableCell cell3 = new HtmlTableCell();
        //        HtmlTableCell cell4 = new HtmlTableCell();
        //        HtmlTableCell cell5 = new HtmlTableCell();
        //        cell1.InnerText = "1111";
        //        cell2.InnerText = "2222";
        //        cell3.InnerText = "Tester";
        //        cell4.InnerText = "XL";
        //        cell5.InnerText = "125.00";
        //        cell5.Attributes.Add("class", "active-basket-item-price");

        //        HtmlTableRow row = new HtmlTableRow();
        //        row.Controls.Add(cell1);
        //        row.Controls.Add(cell2);
        //        row.Controls.Add(cell3);
        //        row.Controls.Add(cell4);
        //        row.Controls.Add(cell5);
        //        //row.Attributes.Add("class","active-basket-item-price");
        //        innerTable.Rows.Add(row);

        //    }

        //    takesList = null;
        //    basketIDs = null;
        //    baskets = null;

        //}

        //AHA BURALAR İSTATİSTİK
        //public void getChartData()
        //{
        //    Series series = Chart1.Series["Series1"];
        //    Series series2 = Chart2.Series["Series2"];
        //    Series series3 = Chart3.Series["Series3"];
        //    Series series4 = Chart4.Series["Series4"];

        //    foreach (stat i1 in db.getProductStat())
        //    {
        //        series.Points.AddXY(i1.name, i1.amount);
        //    }


        //    for (int i = 0; i < i2.Count; i = i + 3)
        //    {
        //        series2.Points.AddXY(i2[i], i2[i+1]);
        //    }



        //    for (int i = 0; i < i3.Count; i = i + 3)
        //    {
        //        series3.Points.AddXY(i3[i], i3[i+1]);
        //    }



        //    for (int i = 0; i < i4.Count; i = i + 3)
        //    {
        //        series4.Points.AddXY(i4[i], i4[i+2]);
        //    }



        //}
        //private void GetChartTypes()
        //{
        //    string[] chartTypeNames = Enum.GetNames(typeof(SeriesChartType));

        //    foreach (int chartType in Enum.GetValues(typeof(SeriesChartType)))
        //    {
        //        ListItem li = new ListItem(Enum.GetName(typeof(SeriesChartType), chartType), chartType.ToString());
        //        ddlChartType.Items.Add(li);
        //        ddlChartType2.Items.Add(li);
        //        ddlChartType3.Items.Add(li);
        //        ddlChartType4.Items.Add(li);
        //    }
        //}
        //protected void ddlChartType_SelectedIndexChanged(object sender, EventArgs e)
        //{
        //    getChartData();
        //    this.Chart1.Series["Series1"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType.SelectedValue);
        //    this.Chart2.Series["Series2"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType2.SelectedValue);
        //    this.Chart3.Series["Series3"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType3.SelectedValue);
        //    this.Chart4.Series["Series4"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType4.SelectedValue);
        //}
        //protected void ddlChartType_SelectedIndexChanged2(object sender, EventArgs e)
        //{
        //    getChartData();
        //    this.Chart1.Series["Series1"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType.SelectedValue);
        //    this.Chart2.Series["Series2"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType2.SelectedValue);
        //    this.Chart3.Series["Series3"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType3.SelectedValue);
        //    this.Chart4.Series["Series4"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType4.SelectedValue);
        //}
        //protected void ddlChartType_SelectedIndexChanged3(object sender, EventArgs e)
        //{
        //    getChartData();
        //    this.Chart1.Series["Series1"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType.SelectedValue);
        //    this.Chart2.Series["Series2"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType2.SelectedValue);
        //    this.Chart3.Series["Series3"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType3.SelectedValue);
        //    this.Chart4.Series["Series4"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType4.SelectedValue);
        //}
        //protected void ddlChartType_SelectedIndexChanged4(object sender, EventArgs e)
        //{
        //    getChartData();
        //    this.Chart1.Series["Series1"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType.SelectedValue);
        //    this.Chart2.Series["Series2"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType2.SelectedValue);
        //    this.Chart3.Series["Series3"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType3.SelectedValue);
        //    this.Chart4.Series["Series4"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType4.SelectedValue);
        //}


        //protected void bestSellers_Load(object sender, EventArgs e)
        //{


        //    for(int i = 0; i<i5.Count;i=i+3)
        //    {
        //        TableRow row = new TableRow();
        //        TableCell cell1 = new TableCell();
        //        TableCell cell2 = new TableCell();
        //        TableCell cell3 = new TableCell();
        //        cell1.Text = i5[i];
        //        cell2.Text = i5[i+1];
        //        cell3.Text = i5[i+2];
        //        row.Cells.Add(cell1);
        //        row.Cells.Add(cell2);
        //        row.Cells.Add(cell3);
        //        best.Rows.Add(row);
        //    }
        //}

        //protected void Table2_Load(object sender, EventArgs e)
        //{


        //    for (int i = 0; i < i2.Count; i = i + 3)
        //    {
        //        TableRow row = new TableRow();
        //        TableCell cell1 = new TableCell();
        //        TableCell cell2 = new TableCell();
        //        TableCell cell3 = new TableCell();
        //        cell1.Text = i2[i];
        //        cell2.Text = i2[i + 1];
        //        cell3.Text = i2[i + 2]+ "%";
        //        row.Cells.Add(cell1);
        //        row.Cells.Add(cell2);
        //        row.Cells.Add(cell3);
        //        Table2.Rows.Add(row);
        //    }
        //}
        //protected void Table3_Load(object sender, EventArgs e)
        //{


        //    for (int i = 0; i < i3.Count; i = i + 3)
        //    {
        //        TableRow row = new TableRow();
        //        TableCell cell1 = new TableCell();
        //        TableCell cell2 = new TableCell();
        //        TableCell cell3 = new TableCell();
        //        cell1.Text = i3[i];
        //        cell3.Text = i3[i + 1];
        //        cell2.Text = i3[i + 2] + "%";
        //        row.Cells.Add(cell1);
        //        row.Cells.Add(cell2);
        //        row.Cells.Add(cell3);
        //        Table3.Rows.Add(row);
        //    }
        //}

        //protected void Table4_Load(object sender, EventArgs e)
        //{

        //    for (int i = 0; i < i4.Count; i = i + 3)
        //    {
        //        TableRow row = new TableRow();
        //        TableCell cell1 = new TableCell();
        //        TableCell cell2 = new TableCell();
        //        TableCell cell3 = new TableCell();
        //        cell1.Text = i4[i];
        //        cell2.Text = i4[i + 1];
        //        cell3.Text = i4[i + 2];
        //        if(Int32.Parse(i4[i + 2]) < 5)
        //        {
        //            cell3.CssClass = "bg-danger";
        //        }
        //        row.Cells.Add(cell1);
        //        row.Cells.Add(cell2);
        //        row.Cells.Add(cell3);
        //        Table4.Rows.Add(row);
        //    }
        //}

    }
}