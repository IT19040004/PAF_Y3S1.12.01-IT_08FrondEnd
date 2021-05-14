$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	//If valid------------------------
	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "PaymentAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
    $("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());
	$("#ItemID").val($(this).closest("tr").find('td:eq(0)').text());
	$("#quantity").val($(this).closest("tr").find('td:eq(1)').text());
	$("#payer").val($(this).closest("tr").find('td:eq(2)').text());
	$("#paymentMethod").val($(this).closest("tr").find('td:eq(3)').text());
	$("#date").val($(this).closest("tr").find('td:eq(4)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(5)').text());
});

//Delete

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentAPI",
		type : "DELETE",
		data : "paymentID=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});
 // CLIENT-MODEL================================================================
function validatePaymentForm() {
	// ItemID
	if ($("#ItemID").val().trim() == "") {
		return "Insert Item ID.";
	}
	// quantity
	if ($("#quantity").val().trim() == "") {
		return "Insert Quantity.";
	}
	var Qnt = $("#quantity").val().trim();
    if(!$.isNumeric(Qnt))
    {
      return "Insert Numeric Value for Quantity";
    }
	// payer
	if ($("#payer").val().trim() == "") {
		return "Insert Payer.";
	}
	// paymentMethod
	if ($("#paymentMethod").val().trim() == "") {
		return "Insert Item Payment Method.";
	}
	// date
	if ($("#date").val().trim() == "") {
		return "Insert Date.";
	}
    // amount
	if ($("#amount").val().trim() == "") {
		return "Insert Amount.";
	}
	var Amt = $("#amount").val().trim();
    if(!$.isNumeric(Amt))
    {
      return "Insert Numeric Value for Amount";
    }
	return true;

}

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}