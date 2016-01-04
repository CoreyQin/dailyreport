$(function() {
	$.getJSON("TeamListServlet", function(json) {
		var fitData = [];
		for (var i = 0; i < json.length; i++) {
			var teamName = json[i];
			var option = {};
			option["id"] = teamName;
			option["text"] = teamName;
			fitData.push(option);
		}
		$("#team").combobox({
			data : fitData
		});
	});

	$("#team").combobox({
		onChange : function() {
			teamChanged();
		},
		onLoadSuccess : function() {
		}
	});
	
	$("#employee").combobox({
		onChange : function() {
			employeeChanged();
		},
		onLoadSuccess : function() {
		}
	});
	
	$("#project").combobox({
		onChange : function() {
			searchTasks();
		},
		onLoadSuccess : function() {
		}
	});

});

// change team options

function teamChanged() {
	// change project options
	$("#project").empty();
	$.getJSON("ProjectListServlet", {
		team : $('#team').combobox('getValue')
	}, function(json) {
		var fitData = [];
		for (var i = 0; i < json.length; i++) {
			var projectObject = json[i];
			var option = {};
			option["id"] = projectObject.project;
			option["text"] = projectObject.project;
			fitData.push(option);
		}
		$("#project").combobox({
			data : fitData
		});
	});

	$("#employee").empty();
	$.getJSON("EmployeeListServlet", {
		team : $('#team').combobox('getValue')
	}, function(json) {
		var rows = json.rows;
		var fitData = [];
		for (var i = 0; i < rows.length; i++) {
			var employeeObject = rows[i];
			var option = {};
			option["id"] = employeeObject.employee;
			option["text"] = employeeObject.employee;
			fitData.push(option);
		}
		$("#employee").combobox({
			data : fitData
		});
	});
}

/**
 * 
 */
function initTaskGrid() {
	$('#task_dg').datagrid({
		queryParams : {
			team : '',

		},
		columns : [ [ {
			field : 'taskDesc',
			title : 'Task Description',
			width : 100
		}, {
			field : 'spentHours',
			title : 'Spent hours',
			width : 20
		}, {
			field : 'eta',
			title : 'ETA',
			width : 30
		} ] ],
		title : "Task List",
		singleSelect : true,
		fitColumns : true,
		toolbar : $('#task_toolbar')
	});
}

var constant_new = "new";
var constant_update = "update";
var flag;
/**
 * 
 */
function newTask() {
	$('#task_dlg').dialog('open').dialog('setTitle', 'New Task');
	$('#task_fm').form('clear');
	flag = constant_new;
}

/**
 * 
 */
function editTask() {
	var row = $('#task_dg').datagrid('getSelected');
	if (row) {
		row.taskDesc = html2special(row.taskDesc);
		$('#task_dlg').dialog('open').dialog('setTitle', 'Edit task');
		$('#task_fm').form('load', row);
		flag = constant_update;
	}
}


/**
 * 
 */
function saveTask() {
	var taskDesc = special2html($("#taskDesc").textbox('getValue'));
	var spentHours = $("#spentHours").val();
	var eta = $("#eta").val();
	if (flag == constant_update) {
		var row = $('#task_dg').datagrid('getSelected');
		var rowIndex = $('#task_dg').datagrid('getRowIndex', row);
		$('#task_dg').datagrid('updateRow', {
			index : rowIndex,
			row : {
				taskDesc : taskDesc,
				spentHours : spentHours,
				eta : eta
			}
		});
	} else {
		var newRow = {
			taskDesc : taskDesc,
			spentHours : spentHours,
			eta : eta
		};
		var rowNumber = $('#task_dg').datagrid('getRows').length;
		insertRow2TaskTable(rowNumber, newRow);
	}
	$('#task_dlg').dialog('close'); // close the dialog

}

function insertRow2TaskTable(index, row) {
	$('#task_dg').datagrid('insertRow', {
		index : index,
		row : row
	});
}

/**
 * 
 */
function removeTask() {
	var row = $('#task_dg').datagrid('getSelected');
	if (row) {
		var rowIndex = $('#task_dg').datagrid('getRowIndex', row);
		$.messager.confirm('Confirm',
				'Are you sure you want to remove this task?', function(r) {
					if (r) {
						$('#task_dg').datagrid('deleteRow', rowIndex);
					}
				});
	}
}

/**
 * 
 */
function searchTasks() {
	$.post('TaskListServlet', {
		name : $('#employee').combobox('getValue'),
		team : $('#team').combobox('getValue'),
		project : $('#project').combobox('getValue')
	}, function(result) {
		if (result == null || result == "") {
			return;
		}
		var employeeObject = result;
		var taskArray = employeeObject.taskList;
		var newArray = convertTaskArray(taskArray);
		$('#task_dg').datagrid("loadData",newArray);
		
		var plans = employeeObject.plans;
		$('#plans').textbox('setValue',decode(plans));
	}, 'json');
}

function convertTaskArray(taskArray){
	//
	function converTask2Row(task) {
		var taskDesc = decode(task.task);
		var hours = task.hours;
		var eta = task.eta;
		var row = {
			taskDesc : taskDesc,
			spentHours : hours,
			eta : eta
		};
		return row;
	}
	
	//
	var newArray = [];
	for(var i = 0; i < taskArray.length; i++){
		var task = taskArray[i];
		newArray.push(converTask2Row(task));
	}
	return newArray;
}



function submitReport() {
	if (!checkInput()) {
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

function checkInput() {
	var employee = $('#employee').combobox('getValue');
	var project = $('#project').combobox('getValue');
	var role = $('#role').combobox('getValue');

	if (employee == "" || project == "" || role == "") {
		return false;
	}
	return true;
}

function getSumbitData() {
	var team = $('#team').combobox('getValue');
	var employee = $('#employee').combobox('getValue');
	var project = $('#project').combobox('getValue');
	var role = $('#role').combobox('getValue');
	var plans = encode($("#plans").textbox('getValue'));
	var tableData = "";
	var rows = $('#task_dg').datagrid('getRows');
	if (rows) {
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var task = row.taskDesc;
			var hours = row.spentHours;
			var eta = row.eta;
			tableData += "{'task':'" + encode(task) + "'" + ",'hours':'"
					+ hours + "'" + ",'eta':'" + eta + "'}";
			if(i < rows.length -1){
				tableData += ",";
			}
		}
	}

	var jsonString = "{'name':'" + employee + "','team':'" + team
			+ "','project':'" + project + "','role':'" + role
			+ "','taskList':[" + tableData + "],'plans':'" + plans + "'}";
	return jsonString;
}




/*var searchTasks = function(){
	$( "#task_table tbody").empty();
	$( "#plans").val();
	
	var team = $('#team').combobox('getValue');
	var employee = $('#employee').combobox('getValue');
	var project = $('#project').combobox('getValue');
	
	if(employee == "" || project == ""){
		return;
	}
	$.ajax({
			type : "post",
			url : "ReportSearchServlet",
			data : "{'name':'"+employee+"','team':'"+team+"','project':'"+project+"'}",
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
}*/

var employeeChanged = function(){
	var team = $('#team').combobox('getValue');
	var employee = $('#employee').combobox('getValue');
	var project = $('#project').combobox('getValue');
	$.ajax({
		type : "get",
		data : {employee:employee,team:team},
		url : "EmployeeSearchServlet",
		success : function(data) {
			var employee = eval("("+data+")"); 
			var project = employee.project;
			var role = employee.role;
			if(project!=null && project!=""){
				$('#project').combobox('setValue',project);
			}
			if(role!=null && role!=""){
				$('#role').combobox('setValue',role);
			}
		},
		error : function() {
			console.error("error!");
		}
	});
}


/**
 * 
 * @param value
 * @returns
 */
function encode(value) {
	if (value == null || value == "") {
		return value;
	}
	return base64Util.base64encode(value);
}

function decode(value) {
	if (value == null || value == "") {
		return value;
	}
	return base64Util.base64decode(value);
}

function special2html(value) {
	value = value.replace(/\r\n/g, "<br>")
	value = value.replace(/\n/g, "<br>");
	return value;
}

function html2special(value) {
	if (value == null || value == "") {
		return value;
	}
	var newvalue = value.replace(/<br>/g, "\r\n");
	return newvalue;
}