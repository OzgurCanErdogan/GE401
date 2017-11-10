<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="adminPage.aspx.cs" Inherits="PassGetWebApp.adminPage" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>PassGet Web Console </title>
    <link href="style.css" rel="stylesheet"/>
</head>
<body>
    <form id="form1" runat="server">
        <div id="wrapper">
            <header>
                <div class="logo">
                LOGO
                </div>
                <nav>
                    <ul>
                        <li>
                            <a href="deafult.aspx" >Ürünler</a>
                        
                        </li>
                        <li class="konular-wrap">
                             <a href="deafult.aspx" >Sepetler</a>

                            <div class="Konular">
                                <ul>
                                    <li>  Satın Alımlar  </li>
                                    <li>  Destek  </li>
                                </ul>
                            </div>
                        </li>
                        <li>
                             <a href="deafult.aspx" >Duyurular</a>
                        </li>
                        <li>
                            <a href="deafult.aspx" >Iletisim</a>
                        </li>
                        <li>
                            <a href="deafult.aspx" >Arama</a>
                        </li>
                    </ul>
                </nav>

                <div class="Search">

                    <asp:TextBox ID="txtArama" CssClass="txtArama" runat="server"  placeholder="arama"/>

                    <asp:Button ID="btnArama" CssClass="btnArama" Text="Ara" runat="server" OnClick ="Searchbtn"/>


                </div>
            </header>


            <div class=" banner">

                Alışverişte yeni trend
            </div>
             <div class="sol-taraf">

                <div class="hizli-kayit">
                    <div class="ust">
                        Hizli Kayit Ol
                    </div>
                    <div class="alt">
                        <span> Kullanici adi </span>
                        <asp:TextBox ID="txtKullanici" CssClass="textbox" runat="server" />
                        <span> Sifre </span>
                        <asp:TextBox ID="txtSifre" CssClass="textbox" runat="server" />
                         <asp:Button ID="btnKayit" CssClass="btnKayit" Text="text" runat="server" OnClick="btnKayit_Click"/>
                         <asp:Label ID="lblSonuc" Text="" runat="server"/>



                     </div>
                </div>

                 <div class="duyurular">
                     <div class="ust">
                     </div>
                        <div class="alt ">
                          <div class="duyuru-wrap ">
                             <asp:Label Text="text" runat="server"/> <br/>
                             <asp:Label Text="Duyurumuz" runat="server"/><br/>
                             <asp:Label Text="Tarih" runat="server"/><br/>
                         </div>
                    </div>
                </div>


            </div>

            <div class="icerik">

                
                <asp:TextBox ID="TextBox1" runat="server" CssClass ="Product" BackColor="#FFCCFF" Height="22px" Width="238px" placeholder="urunTipi"></asp:TextBox>
                <asp:TextBox ID="TextBox2" runat="server" CssClass ="Product" BackColor="#FFCCFF" Height="22px"  Width="154px" placeholder="urun ID"></asp:TextBox>
                <asp:TextBox ID="TextBox3" runat="server" CssClass ="Product" BackColor="#FFCCFF" Height="22px"  Width="154px" placeholder="Marka"></asp:TextBox>
                <asp:Button ID="Button3" CssClass="AddButton" Text="Kaydet" runat="server" OnClick="Button3_Click" Width="60px"/>
            </div>
            <footer>
                PassGet
            </footer>

        </div>
    </form>
</body>
</html>
