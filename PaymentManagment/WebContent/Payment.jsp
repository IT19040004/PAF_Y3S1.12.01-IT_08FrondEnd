<%@page import="com.Payment"%>
<%@page import="com.PaymentAPI"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/Payment.js"></script>
</head>
<body> 

<div class="container"><div class="row"><div class="col-6"> 
		<h2>Payment Management</h2>
<form id="formPayment" name="formPayment">

 Item ID: 
 <input id="ItemID" name="ItemID" type="text" 
 class="form-control form-control-sm">
 <br> Quantity: 
 <input id="quantity" name="quantity" type="text" 
 class="form-control form-control-sm">
 <br> Payer: 
 <input id="payer" name="payer" type="text" 
 class="form-control form-control-sm">
 <br> Payment Method: 
 <input id="paymentMethod" name="paymentMethod" type="text" 
 class="form-control form-control-sm">
 <br>Date: 
 <input id="date" name="date" type="text" 
 class="form-control form-control-sm">
 <br> Amount: 
 <input id="amount" name="amount" type="text" 
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidPaymentIDSave" 
 name="hidPaymentIDSave" value="">
 
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divPaymentGrid">
		 <%
		 Payment PaymentObj = new Payment(); 
		 		 out.print(PaymentObj.readPayment());
		 %>
</div>
</div> </div> </div> 

</body>
</html>