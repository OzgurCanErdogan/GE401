<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Basket.aspx.cs" Inherits="PassGetWebApp.Basket1" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>PassGet Web Console </title>
    <link href="styleBasket.css" rel="stylesheet"/>
</head>  
<body>
        <div id="wrapper">
            <header>
                <div class="logo">
                    
                </div>
                <nav>
                    <ul>
                        <li>
                             <a href="../Admin Page/adminPage.aspx" >Ana Sayfa</a>
                        </li>
                        <li>
                             <a href="../Product Page/ProductPage.aspx" >Ürünler</a>
                        </li>
                        <li>
                            <a href="../Basket.aspx" >Sepetler</a>
                        </li>
                        <li>
                            <a href="deafult.aspx" >Arama</a>
                        </li>
                        <li>
                            <a href="deafult.aspx" >Ayarlar</a>
                        </li>
                    </ul>
                </nav>

            </header>

        </div>
    
    <div id="container">

        <div class="activeUser">

            <asp:Table ID="basketTable" runat="server" Width="100%" OnLoad="addRowsBasketTable"> 
                <asp:TableRow>
                    <asp:TableCell>Aktif Sepetler</asp:TableCell>
                    
                </asp:TableRow>
            </asp:Table> 
            

            <div id="basketModel" class="modalDialog">
                 <div>	<a href="#close" title="Close" class="close">X</a>

        	         <asp:Table ID="Table1" runat="server" Width="100%" OnLoad="addRowsBasketTable2"> 
                       <asp:TableRow>
                        <asp:TableCell> <b> Aktif Sepetler </b> </asp:TableCell>
                         <asp:TableCell><b>1. Ürün</b> </asp:TableCell>
                         <asp:TableCell> <b> 2. Ürün</b></asp:TableCell>
                          <asp:TableCell> <b> 3. Ürün</b></asp:TableCell> 
                        </asp:TableRow>
                    </asp:Table>
                 </div>
            </div>
      
        
        
        </div>
        
        
    </div>
  
       
   
    
</body>
</html>