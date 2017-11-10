<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LoginScreen.aspx.cs" Inherits="PassGetWebApp.LoginScreen" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Login Screen</title>
    <link href="styleLogin.css" rel="stylesheet"/>
</head>
<body>
    <div style="margin-left:auto; margin-right:auto; text-align:center;">
        <p>PassGet Login Page</p>
    </div>
    
    <form id="form1" runat="server">
        <div style="margin-left:auto; margin-right:auto; text-align:center;">
             <asp:Label ID="UserIDLabel" runat="server" Text="Enter UserID"></asp:Label>
             &nbsp;&nbsp;&nbsp;&nbsp;
             <asp:TextBox ID="UserID" runat="server"></asp:TextBox>
             <br />
             <br />
        </div>
        <div style="margin-left:auto; margin-right:auto; text-align:center;">
             <asp:Label ID="PasswordLabel" runat="server" Text="Enter Password"></asp:Label>
             &nbsp;<asp:TextBox ID="Password" runat="server"></asp:TextBox>
             <br />
             <br />
            <asp:Button ID="Login" runat="server" Text="Login" OnClick="Login_Click" />
        </div>
       
        
       
    </form>
</body>
</html>
