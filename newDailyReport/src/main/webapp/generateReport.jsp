<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>generate report</title>
</head>
<body>
	<table>
		<tr >
			<td>
				<label>team : </label>
				<select id="team">
					<!-- <option></option> -->
					<option>Kronos Team5</option>
					<option>Kronos Team1&8</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td>
				<label>date : </label>
				<input id="date" type="text" value="2015-11-27"/>
			</td>
		</tr>
		
		<tr>
			<td>
				<input id="submit" type="button" value='download report' onclick="generateReport()">
			</td>
		</tr>
	</table>
</body>

<script src="js/jquery.js"></script>

<script type="text/javascript">

$(function() {
	var today = new Date();
	var dateString = today.getFullYear() + "-" + (today.getMonth()+1) + "-" + today.getDate();
	$("#date").val(dateString);
	
});

function generateReport() {
	if(!checkInput()){
		alert("please check your input!");
		return;
	}
	$.ajax({
		type : "post",
		url : "ReportGenerateServlet",
		data : getSumbitData(),
		success : function(data) {
			var fileUrl = data;
			window.location.href = fileUrl;
		},
		error : function() {
			console.error("error!");
		}
	})
}


function checkInput(){
	var team = $("#team").val();
	var date = $("#date").val();
	if(team == "" || date == ""){
		return false;
	}
	return true;
}


function getSumbitData(){
	var team = $("#team").val();
	var date = $("#date").val();
	var jsonString = "{'team':'"+team+"','date':'"+date+"','download':'true'}";
	return jsonString;
}
</script>
</html>