using MySql.Data.MySqlClient;
using MysqlDatabase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.DataVisualization.Charting;
using System.Web.UI.WebControls;
using static MysqlDatabase.DBConnect;

namespace PassGetWebApp
{
    public partial class graphics : System.Web.UI.Page
    {

        DBConnect db;
        protected void Page_Load(object sender, EventArgs e)
        {
            db = new DBConnect();
            if (!IsPostBack)
            {
                getChartData();
                GetChartTypes();
            }
        }

     
        public void getChartData()
        {
            Series series = Chart1.Series["Series1"];
            
            foreach(stat i1 in db.getProductStat())
            {
                series.Points.AddXY(i1.name, i1.amount);
            }

        }
        private void GetChartTypes()
        {
            string[] chartTypeNames = Enum.GetNames(typeof(SeriesChartType));

            foreach (int chartType in Enum.GetValues(typeof(SeriesChartType)))
            {
                ListItem li = new ListItem(Enum.GetName(typeof(SeriesChartType), chartType), chartType.ToString());
                ddlChartType.Items.Add(li);
            }
        }
        protected void ddlChartType_SelectedIndexChanged(object sender, EventArgs e)
        {
            getChartData();
            this.Chart1.Series["Series1"].ChartType = (SeriesChartType)Enum.Parse(typeof(SeriesChartType), ddlChartType.SelectedValue);
        }

    }
}