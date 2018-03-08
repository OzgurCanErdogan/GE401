<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="ProductPage.aspx.cs" Inherits="PassGetWebApp.ProductPage" Async="true" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>PassGet | Products</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
    crossorigin="anonymous">
  <link href="./style.css" rel="stylesheet" />
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
</head>

<body>
  <!-- Modal for showing details on a product -->
  <div class="modal fade" id="showProductModal" tabindex="-1" role="dialog" aria-labelledby="showProductModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="showProductModalLabel">Product:
            <span id="product-id">
          </h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <table class="table text-center table-striped">
            <thead class="thead-light">
              <tr>
                <th scope="col">ID</th>
                <th scope="col">Ürün</th>
                <th scope="col">Stok Adeti</th>   
                <th scope="col">Bu ay toplam satılan</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td id="product-id"></td>
                <td id="product-name"></td>
                <td id="product-count"></td>
                <td id="product-sold"></td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Kapat</button>
          <button type="button" class="btn btn-primary">Ürünü Değiştir</button>
          <button type="button" class="btn btn-danger">Ürünü Sil</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Modal to add a new product -->
  <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="addProductModalLabel">Ürün ekle
          </h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form runat="server">
            <div class="form-group">
              <label for="productNameInput">Ürün Adı</label>
              <input type="text" runat="server" class="form-control" id="productNameInput" placeholder="Ürün adı giriniz.">
            </div>
              <div class="form-group">
              <label for="productIDinput">Ürün ID</label>
              <input type="text" runat="server" class="form-control" id="productIDinput" placeholder="Ürün ID'sini giriniz.">
            </div>
              <div class="form-group">
              <label for="productTypeInput">Ürün Türü</label>
              <input type="text" runat="server" class="form-control" id="productTypeInput" placeholder="Ürün türünü giriniz.">
            </div>
              <div class="form-group">
              <label for="productBedenInput">Ürün Bedeni</label>
              <input type="text" runat="server" class="form-control" id="productBedenInput" placeholder="Ürün bedenini giriniz.">
            </div>
            <div class="form-group">
              <label for="productPriceInput">Ürün Fiyatı</label>
              <input type="number" runat="server" class="form-control" id="productPriceInput" placeholder="Ürün fiyatı(TL)">
            </div>
            <div class="form-group">
              <label for="productCountInput">Stok Sayısı</label>
              <input type="text" runat="server" class="form-control" id="productCountInput" placeholder="Ürün Stoğu(Adet)">
            </div>
            <button id="productAddBtn" type="submit" runat="server" onserverclick="addProduct" class="btn btn-success">Ekle</button>
              <%--<asp:Button ID="Button1" class="btn btn-success" runat="server" Text="Ekle" OnClick="addProduct" />--%>
          </form>
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
          <li class="nav-item">
            <a class="nav-link" href="../Admin Page/adminPage.aspx">Anasayfa</a>
          </li>
          <li class="nav-item active">
            <a class="nav-link" href="../ProductPage.aspx">Ürünler</a>
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
    <h3>Ürünler</h3>
    <a href="#" class="button" id="button-add-product">Ürün Ekle</a>
    <div class="products">
      <table class="table text-center table-striped" id="ProductInnerTable" runat="server">
        <thead class="thead-light">
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Ürün</th>
            <th scope="col">Stok Adeti</th>
            <th scope="col">Bu ay toplam satılan</th>
          </tr>
        </thead>
        <tbody>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
          <tr class="product">
            <td>123</td>
            <td>T-Shirt</td>
            <td>112</td>
            <td>43</td>
          </tr>
        </tbody>
      </table>
    </div>
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