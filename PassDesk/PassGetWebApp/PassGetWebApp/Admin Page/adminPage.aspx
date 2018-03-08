<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="adminPage.aspx.cs" Inherits="PassGetWebApp.adminPage" Async="true" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>PassGet | Admin</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
        crossorigin="anonymous">
    <link href="./style.css" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
</head>
<body>
     <!-- Modal to show the inside of the basket -->
  <div class="modal fade" id="showBasketModal" tabindex="-1" role="dialog" aria-labelledby="showBasketModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="showBasketModalLabel">Basket ID:
            <span id="basket-id">
          </h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <table class="table text-center" id="innerTable" runat="server">
            <h5>Ürünler</h5>
            <thead class="table-primary">
              <tr>
                <th>ID</th>
                <th>RFID</th>
                <th>Ürün Adı</th>
                <th>Beden</th>
                <th>Fiyat</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1232</td>
                <td class="text-uppercase">c612f</td>
                <td>V Yaka T-asfdasfafsasdfaShirt</td>
                <td>M</td>
                <td class="active-basket-item-price">23.99</td>
              </tr>
              <tr>
                <td>1232</td>
                <td class="text-uppercase">c612f</td>
                <td>V Yaka T-Shirt</td>
                <td>M</td>
                <td class="active-basket-item-price">23.99</td>
              </tr>
              <tr>
                <td>1232</td>
                <td class="text-uppercase">c612f</td>
                <td>V Yaka T-Shirt</td>
                <td>M</td>
                <td class="active-basket-item-price">23.99</td>
              </tr>
            </tbody>
          </table>
          <h6 class="text-right">Toplam: <span id="active-basket-total" class="text-danger">1234.1234</span> </h6>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Kapat</button> 
        </div>
      </div>
    </div>
  </div>
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark mr-3 ml-3">
            <a class="navbar-brand" href="#">PassGet</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown"
                aria-expanded="false" aria-label="Toggle navigation">
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="adminPage.aspx">Anasayfa</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../Product Page/ProductPage.aspx">Ürünler</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../Basket.aspx">Sepetler</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="deafult.aspx">Arama</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="deafult.aspx">Ayarlar</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <main>
        <form id="form1" runat="server">
            <div class="container-passget">
                <div class="col-active-basket">
                    <h3 class="text-center">
                        <i class="fas fa-shopping-basket"></i>Aktif Sepetler</h3>

                    <div class="active-basket-container" id="TableOfBaskets" runat="server">
<%--                        <div class="active-basket text-center" id="basket1" runat="server" >
                            <img src="https://www.erdoganlarbisiklet.com/media/catalog/product/cache/1/thumbnail/100x100/9df78eab33525d08d6e5fb8d27136e95/2/5/250401.jpg" class="active-basket-img" alt="Basket" />
                            <p class="active-basket-no">Sepet: <span id="firstbasket" runat="server">M-A-K-01</span></p>
                            <p class="text-muted">Ürünler</p>
                            <p class="active-basket-item">Ürün: T-shirt</p>
                            <p class="active-basket-item">Ürün: Kot Pantolon</p>
                            <p class="active-basket-item">...</p>
                        </div>
                        <div class="active-basket text-center">
                            <img src="https://www.erdoganlarbisiklet.com/media/catalog/product/cache/1/thumbnail/100x100/9df78eab33525d08d6e5fb8d27136e95/2/5/250401.jpg" class="active-basket-img" alt="Basket" />
                            <p class="active-basket-no">Sepet: M-A-K-01</p>
                            <p class="text-muted">Ürünler</p>
                            <p class="active-basket-item">Ürün: T-shirt</p>
                            <p class="active-basket-item">Ürün: Kot Pantolon</p>
                            <p class="active-basket-item">...</p>
                        </div>
                        <div class="active-basket text-center">
                            <img src="https://www.erdoganlarbisiklet.com/media/catalog/product/cache/1/thumbnail/100x100/9df78eab33525d08d6e5fb8d27136e95/2/5/250401.jpg" class="active-basket-img" alt="Basket" />
                            <p class="active-basket-no">Sepet: M-A-K-01</p>
                            <p class="text-muted">Ürünler</p>
                            <p class="active-basket-item">Ürün: T-shirt</p>
                            <p class="active-basket-item">Ürün: Kot Pantolon</p>
                            <p class="active-basket-item">...</p>
                        </div>--%>
                        <div class="active-basket text-center" onclick="DivClicked()">
                            <asp:Button runat="server" id="btnHidden" style="display:none" onclick="smt1" />
                            <img src="https://www.erdoganlarbisiklet.com/media/catalog/product/cache/1/thumbnail/100x100/9df78eab33525d08d6e5fb8d27136e95/2/5/250401.jpg" class="active-basket-img" alt="Basket" />
                            <p class="active-basket-no">Sepet: M-A-K-03</p>
                            <p class="text-muted">Ürünler</p>
                            <p class="active-basket-item">Ürün: T-shirt</p>
                            <p class="active-basket-item">Ürün: Kot Pantolon</p>
                            <p class="active-basket-item">...</p>
                            <%--<asp:Button ID="Button1" class="btn btn-success" runat="server" Text="Ekle" OnClick="smt1" />--%>
                            <%--<input type ="button" name="smtt" value="s" onclick="smt1" runat="server" />--%>
                        </div>
                    </div>

                    <a href="#" class="button text-center">Bütün Sepetler</a>
                </div>

                <script>

                    function DivClicked() {
                        var btnHidden = $('#<%= btnHidden.ClientID %>');
                        if (btnHidden != null) {
                            btnHidden.click();
                        }
                    }

                </script>
                <%--AHA BURALAR HEP İSTATİSTİK--%>
                <%--<div class="col-stats">
                    <h3 class="text-center">Mağaza İstatistikleri</h3>


                    <div class="stats-graphs">


                        <div class="containerStat">

                            <table style="border: 1px solid black" runat="server">
                                <tr>
                                    <td>
                                        <b>Select Chart Type :</b>
                                    </td>
                                    <td>
                                        <asp:DropDownList ID="ddlChartType" runat="server" AutoPostBack="True"
                                            OnSelectedIndexChanged="ddlChartType_SelectedIndexChanged">
                                        </asp:DropDownList>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <asp:Chart ID="Chart1" runat="server" Width="350px" CssClass="style">
                                            <Titles>
                                                <asp:Title Text="En Çok Satan Ürünler">
                                                </asp:Title>
                                            </Titles>
                                            <Series>
                                                <asp:Series Name="Series1" ChartArea="ChartArea1" ChartType="Pie" runat="server">
                                                    <Points>
                                                    </Points>
                                                </asp:Series>
                                            </Series>
                                            <ChartAreas>
                                                <asp:ChartArea Name="ChartArea1">
                                                    <AxisX Title="Ürün ">
                                                    </AxisX>
                                                    <AxisY Title="Miktar">
                                                    </AxisY>
                                                </asp:ChartArea>
                                            </ChartAreas>
                                        </asp:Chart>
                                    </td>
                                </tr>
                            </table>
                        </div>--%>
                        <%--<div class="containerStat">

                            <table style="border: 1px solid black" runat="server">
                                <tr>
                                    <td>
                                        <b>Select Chart Type :</b>
                                    </td>
                                    <td>
                                        <asp:DropDownList ID="ddlChartType2" runat="server" AutoPostBack="True"
                                            OnSelectedIndexChanged="ddlChartType_SelectedIndexChanged2">
                                        </asp:DropDownList>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <asp:Chart ID="Chart2" runat="server" Width="350px">
                                            <Titles>
                                                <asp:Title Text="Aylık Müşteri Oranları">
                                                </asp:Title>
                                            </Titles>
                                            <Series>
                                                <asp:Series Name="Series2" ChartArea="ChartArea2" ChartType="Pie" runat="server">
                                                    <Points>
                                                    </Points>
                                                </asp:Series>
                                            </Series>
                                            <ChartAreas>
                                                <asp:ChartArea Name="ChartArea2">
                                                    <AxisX Title="Cinsiyet ">
                                                    </AxisX>
                                                    <AxisY Title="Sayı">
                                                    </AxisY>
                                                </asp:ChartArea>
                                            </ChartAreas>
                                        </asp:Chart>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="containerStat" >

                            <table style="border: 1px solid black" runat="server">
                                <tr>
                                    <td>
                                        <b>Select Chart Type :</b>
                                    </td>
                                    <td>
                                        <asp:DropDownList ID="ddlChartType3" runat="server" AutoPostBack="True"
                                            OnSelectedIndexChanged="ddlChartType_SelectedIndexChanged3">
                                        </asp:DropDownList>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <asp:Chart ID="Chart3" runat="server" Width="350px">
                                            <Titles>
                                                <asp:Title Text="Müşteri Yaş Oranları">
                                                </asp:Title>
                                            </Titles>
                                            <Series>
                                                <asp:Series Name="Series3" ChartArea="ChartArea3" ChartType="Pie" runat="server">
                                                    <Points>
                                                    </Points>
                                                </asp:Series>
                                            </Series>
                                            <ChartAreas>
                                                <asp:ChartArea Name="ChartArea3">
                                                    <AxisX Title="Yaş ">
                                                    </AxisX>
                                                    <AxisY Title="Sayı">
                                                    </AxisY>
                                                </asp:ChartArea>
                                            </ChartAreas>
                                        </asp:Chart>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="containerStat">

                            <table style="border: 1px solid black" runat="server">
                                <tr>
                                    <td>
                                        <b>Select Chart Type :</b>
                                    </td>
                                    <td>
                                        <asp:DropDownList ID="ddlChartType4" runat="server" AutoPostBack="True"
                                            OnSelectedIndexChanged="ddlChartType_SelectedIndexChanged4">
                                        </asp:DropDownList>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <asp:Chart ID="Chart4" runat="server" Width="350px">
                                            <Titles>
                                                <asp:Title Text="Stok Durunu">
                                                </asp:Title>
                                            </Titles>
                                            <Series>
                                                <asp:Series Name="Series4" ChartArea="ChartArea4" ChartType="Pie" runat="server">
                                                    <Points>
                                                    </Points>
                                                </asp:Series>
                                            </Series>
                                            <ChartAreas>
                                                <asp:ChartArea Name="ChartArea4">
                                                    <AxisX Title="Ürün ID ">
                                                    </AxisX>
                                                    <AxisY Title="Adet">
                                                    </AxisY>
                                                </asp:ChartArea>
                                            </ChartAreas>
                                        </asp:Chart>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <div class="stats-text">
                        <div class="row">
                            <div class="col-md-4">
                                <h6 class="text-center">En Çok Satan Ürünler</h6>
                                <asp:Table ID="best" runat="server" Width="100%" OnLoad="bestSellers_Load" CssClass="table text-center">
                                    <asp:TableRow CssClass="table-success">
                                        <asp:TableCell ForeColor="Black">Ürün ID</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Ürün Adı</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Satış Adeti</asp:TableCell>
                                    </asp:TableRow>
                                </asp:Table>
                            </div>
                            <div class="col-md-4">
                                <h6 class="text-center">Aylık Müşteri Oranları</h6>
                                 <asp:Table ID="Table2" runat="server" Width="100%" OnLoad="Table2_Load" CssClass="table text-center">
                                    <asp:TableRow CssClass="table-success">
                                        <asp:TableCell ForeColor="Black">Cinsiyet</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Sayı</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Oran</asp:TableCell>
                                    </asp:TableRow>
                                </asp:Table>
                                  <asp:Table ID="Table3" runat="server" Width="100%" OnLoad="Table3_Load" CssClass="table text-center">
                                    <asp:TableRow CssClass="table-success">
                                        <asp:TableCell ForeColor="Black">Yaş</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Oran</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Sayı</asp:TableCell>
                                    </asp:TableRow>
                                </asp:Table>
                            </div>
                            <div class="col-md-4">
                                <h6 class="text-center">Stok Azalan Ürünler</h6>
                                <asp:Table ID="Table4" runat="server" Width="100%" OnLoad="Table4_Load" CssClass="table text-center">
                                    <asp:TableRow CssClass="table-success">
                                        <asp:TableCell ForeColor="Black">Ürün ID</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Adı</asp:TableCell>
                                        <asp:TableCell ForeColor="Black">Ürün Adeti</asp:TableCell>
                                    </asp:TableRow>
                                </asp:Table>
                            </div>
                        </div>
                    </div>--%>
                </div>
            </div>
        </form>
    </main>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
    <script src="script.js"></script>
</body>

</html>
