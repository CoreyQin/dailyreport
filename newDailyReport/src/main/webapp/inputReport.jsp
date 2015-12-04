<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/base/jquery.ui.all.css">

<title>daily report</title>
</head>

<style>
body {
	font-size: 15px;
}

label, input {
	display: block;
}

input.text {
	margin-bottom: 12px;
	width: 95%;
	padding: .4em;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td, div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 4px;
	text-align: left;
}
</style>
<body>

	<div>
		<h2>user info:</h2>
		<table style="width: 600px; height: 100px;">
			<tr>
				<td align="right">team:</td>
				<td align="left"><select id="team">
						<option></option>
						<option>team1</option>
						<option>team2</option>
				</select></td>
				<td align="right">employee name:</td>
				<td align="left"><select id="employee">
						<!-- <option></option> -->
						<option>corey</option>
						<option>gary</option>
				</select></td>
			</tr>
			<tr>
				<td align="right">project :</td>
				<td align="left"><select id="project">
						<option></option>
						<option>project1</option>
						<option>project2</option>
				</select></td>
				<td align="right">role:</td>
				<td><select id="role">
						<option>QA</option>
						<option>DEV</option>
						<option>TL</option>
						<option>Arch</option>
				</select></td>
			</tr>
			<tr>
				<td></td>
			</tr>
		</table>

	</div>

	<div id="dialog-form" title="Create/Edit User">
		<form>
			<fieldset>
				<label for="taskDesc">task desc</label>
				<textarea id="taskDesc" rows="6" cols="50" class="text"></textarea>
				<label for="spentHours">spent hours</label> <input type="text"
					name="spentHours" id="spentHours" value="" class="text" /> <label
					for="eta">eta</label> <input type="text" name="eta" id="eta"
					value="" class="text" /> <input type="hidden" name="rowindex"
					id="rowindex" value="" />
			</fieldset>
		</form>
	</div>


	<div id="users-contain">
		<h2>Tasks:</h2>

		<input id="addTask" type="button" value="add new task">

		<table id="task_table">
			<thead>
				<tr class="ui-widget-header ">
					<th>task description</th>
					<th>spent hours</th>
					<th>eta</th>
					<th style="width: 12em;">Operation</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>

	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div>
		<h2>plans for tomorrow:</h2>
		<textarea id="plans" rows="8" cols="100"></textarea>
	</div>

	<br>
	<br>
	<input id="submit" type="button" value='submit' onclick="submitTasks()">

	<script src="js/jquery.js"></script>
	<script src="js/jquery-ui-1.8.21.custom.js"></script>
	<script src="js/Base64Util.js"></script>
	<script type="text/javascript">
		function submitTasks() {
			if(!checkInput()){
				alert("please check your input!");
				return;
			}
			$.ajax({
				type : "post",
				url : "SubmitReportServlet",
				data : getSumbitData(),
				success : function(data) {
					alert("your data has been submitted!");
				},
				error : function() {
					console.error("refresh imagelist error");
				}
			})
		}
		
		function checkInput(){
			var employee = $("#employee").val();
			var project = $("#project").val();
			var role = $("#role").val();
			if(employee == "" || project == "" || role == ""){
				return false;
			}
			return true;
		}
		
		
		function getSumbitData(){
			var employee = $("#employee").val();
			var project = $("#project").val();
			var role = $("#role").val();
			//var date = new Date();
			//var dateString = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate();
			var plans = encode($("#plans").val());
			var trows =  $("#task_table tbody").children();
			var tableData = "";
			for(var i=0;i<trows.length;i++){
				var row = $(trows[i]);
				var tds = row.children();
				var task = tds.eq(0).html().toString();
				var hours = tds.eq(1).text();
				var eta = tds.eq(2).text();
				tableData += "{'task':'"+encode(task)+"'"+",'hours':'"+hours+"'"+",'eta':'"+eta+"'}";
				if(i < trows.length -1){
					tableData += ",";
				}
			}
			
			var jsonString = "{'name':'"+employee+"','project':'"+project+"','role':'"+role+"','taskList':["+tableData+"],'plans':'"+plans+"'}";
			return jsonString;
		}
		
		/* function getPlans(){
			var plans = $("#plans").val();
		    plans=plans.replace(/\r\n/g,"<BR>")  
        	plans=plans.replace(/\n/g,"<BR>");
			return plans;
		} */
		
		function encode(value){
			if(value == null || value == ""){
				return value;
			}
			//return base64Util.encodeBase64(value);
			
			return base64Util.base64encode(value);
		}
		
		function decode(value){
			if(value == null || value == ""){
				return value;
			}
			//return base64Util.decodeBase64(value);
			return base64Util.base64decode(value);
		}
		
		function replaceSpecial(value){
		 	value = value.replace(/\r\n/g,"<br>")  
        	value = value.replace(/\n/g,"<br>");
			return value;
		}
		
		function retrieveSpecial(value){
			if(value == null || value == ""){
				return value;
			}
			var newvalue = value.replace(/<br>/g,"\r\n");
			return newvalue;
		}
		
		
		
	</script>
	<script>
	$(function() {
		var taskDesc = $( "#taskDesc" ),
			spentHours = $( "#spentHours" ),
			eta = $( "#eta" ),
			rowindex = $( "#rowindex" ),
			allFields = $( [] ).add( taskDesc ).add( spentHours ).add( eta ).add( rowindex );
		
		$( "#dialog-form" ).dialog({
			autoOpen: false,
			height: 450,
			width: 600,
			modal: true,
			buttons: {
				"Create/Edit": function() {
					if (rowindex.val()==""){
						$( "#task_table tbody" ).append( "<tr>" +
							"<td>" + replaceSpecial(taskDesc.val()) + "</td>" + 
							"<td>" + spentHours.val() + "</td>" + 
							"<td>" + eta.val() + "</td>" +
							'<td><button class="EditButton" >Edit</button><button class="DeleteButton">Delete</button></td>'+
						"</tr>" ); 
						bindEditEvent();
					}else{ 
						var idx = rowindex.val();
						var tr = $("#task_table>tbody>tr").eq(idx);
						//$("#debug").text(tr.html());
						tr.children().eq(0).html(replaceSpecial(taskDesc.val()));
						tr.children().eq(1).text(spentHours.val());
						tr.children().eq(2).text(eta.val());
					}
					$( this ).dialog( "close" );
				},
				Cancel: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				//allFields.val( "" ).removeClass( "ui-state-error" );
				;
			}
		});
		
		
		function bindEditEvent(){
			$(".EditButton").click(function(){
					var b = $(this);
					var tr = b.parents("tr");
					var tds = tr.children();
					taskDesc.val(retrieveSpecial(tds.eq(0).html()));
					spentHours.val(tds.eq(1).html());
					eta.val(tds.eq(2).html());
					
					var trs = b.parents("tbody").children();
					rowindex.val(trs.index(tr));
					
					$( "#dialog-form" ).dialog( "open" );
			});
			
			$(".DeleteButton").click(function(){
				var tr = $(this).parents("tr");
				tr.remove();
			});
		};
		
		bindEditEvent();
		
		$( "#addTask" )
			.button()
			.click(function() {
				if(!checkInput()){
					alert("please input user info first!");
					return;
				}
				// clear all the fileds on the modal page
				allFields.each(function(idx){
					this.value="";
				});
				$( "#dialog-form" ).dialog( "open" );
			});
			
		var searchTasks = function(){
			$( "#task_table tbody").empty();
			$( "#plans").val();
			
			var employee = $("#employee").val();
			var project = $("#project").val();
			if(employee == "" || project == ""){
				return;
			}
			$.ajax({
					type : "post",
					url : "ReportSearchServlet",
					data : "{'name':'"+$("#employee").val()+"','project':'"+$("#project").val()+"'}",
					success : function(data) {
						if(data == null || data == ""){
							return;
						}
						var employeeObject = eval("("+data+")");  
						var plans = employeeObject.plans;
						$("#plans").val(decode(plans));
						var taskArray = employeeObject.taskList;
						for(var i= 0; i < taskArray.length;i++){  
							var task = taskArray[i];
							$( "#task_table tbody" ).append( "<tr>" +
							"<td>" + decode(task.task) + "</td>" + 
							"<td>" + task.hours + "</td>" + 
							"<td>" + task.eta + "</td>" +
							'<td><button class="EditButton" >Edit</button><button class="DeleteButton">Delete</button></td>'+
							"</tr>" ); 
						}  
						bindEditEvent();
					},
					error : function() {
						console.error("refresh imagelist error");
					}
				})
		}
		
		$("#employee").change(searchTasks);
		$("#project").change(searchTasks);
		
		
		// change team options
		$.ajax({
				type : "post",
				url : "TeamListServlet",
				success : function(data) {
					var teamList = eval("("+data+")"); 
					var teamArray = teamList.teamList;
					fillData2Select($("#team"),teamArray);
					teamChanged();
				},
				error : function() {
					console.error("error!");
				}
			})
			
			
		function teamChanged(){
				// change project options
				$("#project").empty();
				$.ajax({
						type : "post",
						url : "ProjectListServlet",
						data : "{'team':'"+$("#team").val()+"'}",
						success : function(data) {
							var projectList = eval("("+data+")"); 
							var projectArray = projectList.projectList;
							fillData2Select($("#project"),projectArray);
						},
						error : function() {
							console.error("error!");
						}
					})
			
				// change employee options
				$("#employee").empty();
				$.ajax({
					type : "post",
					url : "EmployeeListServlet",
					data : "{'team':'"+$("#team").val()+"'}",
					success : function(data) {
						var employeeList = eval("("+data+")"); 
						var employeeArray = employeeList.employeeList;
						fillData2Select($("#employee"), employeeArray);
					},
					error : function() {
						console.error("error!");
					}
				})
		}
			
			
		function fillData2Select(selectWidget, optionArray){
			selectWidget.empty();
			selectWidget.append("<option> - </option" ); 
			for(var i= 0; i < optionArray.length;i++){  
				var option = optionArray[i];
				selectWidget.append("<option>"+option+"</option" ); 
			}  
		}
		
		$("#team").change(teamChanged);
	});
	
	</script>



</body>
</html>