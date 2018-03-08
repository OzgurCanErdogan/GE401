<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LoginScreen.aspx.cs" Inherits="PassGetWebApp.LoginScreen" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Login Screen</title>
    <link href="styleLogin.css" rel="stylesheet"/>
</head>
<body>
    <div class="login-page">
  <div class="form" runat="server" >
    <form class="login-form" runat="server">
      <input id="nameID" type="text" placeholder="Kullanıcı Adı" runat="server"/>
      <input id="passID"  type="password" placeholder="Şifre" runat="server"/>
      <button id="Login" runat="server" onserverclick="login_Click">Giriş</button >
     
    </form>
  </div>
</div>
</body>
</html>

