$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ==============om==============================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	
	
	
	
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	//If valid------------------------
	var type = ($("#hidRegisterIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "RegisterAPI",
		type : type,
		data : $("#formRegister").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});
});
// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidRegisterIDSave").val($(this).closest("tr").find('#hidRegisterIDUpdate').val());
	$("#name").val($(this).closest("tr").find('td:eq(0)').text());
	$("#email").val($(this).closest("tr").find('td:eq(1)').text());
	$("#password").val($(this).closest("tr").find('td:eq(2)').text());
	$("#repassword").val($(this).closest("tr").find('td:eq(3)').text());
});

//Delete

$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "RegisterAPI",
		type : "DELETE",
		data : "registerid=" + $(this).data("registerid"),
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
		}
	});
});
// CLIENT-MODEL================================================================
function validateItemForm() {
	// Name
	if ($("#name").val().trim() == "") {
		return "Insert User name.";
	}
	// Email
	if ($("#email").val().trim() == "") {
		return "Insert User email.";
	}
	var email = $("#email").val().trim();
	if (!validateEmail(email)) {
		return "Insert Valid Email.";
	}

	//Password
	if ($("#password").val().trim() == "") {
		return "Insert User password.";
	}
	//RePassword
	if ($("#repassword").val().trim() == "") {
		return "Insert User repassword.";
	}
	var pwd = $("#password").val().trim();
	var cpwd = $("#repassword").val().trim();
	if(!checkPassword(pwd,cpwd)) {
		return "Password is not matching";
	}
	
	return true;
}

function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divRegisterGrid").html(resultSet.data);
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

	$("#hidRegisterIDSave").val("");
	$("#formRegister")[0].reset();
}

function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divRegisterGrid").html(resultSet.data);
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

function validateEmail(email) 
{
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}

function checkPassword(pwd,cpwd)
{

	if(pwd == cpwd) {
		return true;
	}
	else {
		return false;
	}
}
