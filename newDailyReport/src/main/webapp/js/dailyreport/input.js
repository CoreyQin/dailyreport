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

});

// change team options

function teamChanged() {
	// change project options
	$("#project").empty();
	$.getJSON("ProjectListServlet", {
		team : $('#team').combobox('getText')
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

	
	$.getJSON("EmployeeListServlet", {
		team : $('#team').combobox('getText')
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
			title : 'task description',
			width : 100
		}, {
			field : 'spentHours',
			title : 'spent hours',
			width : 20
		}, {
			field : 'eta',
			title : 'eta',
			width : 30
		} ] ],
		title : "task list",
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
		$('#task_dlg').dialog('open').dialog('setTitle', 'Edit task');
		$('#task_fm').form('load', row);
		flag = constant_update;
	}
}

/**
 * 
 */
function saveTask() {
	var taskDesc = $("#taskDesc").val();
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
		insertRow2TaskTable(2, newRow);
	}
	$('#task_dlg').dialog('close'); // close the dialog

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

searchTasks();

/**
 * 
 */
function searchTasks() {
	$.post('TaskListServlet', {
		name : 'Abby Li',
		project : 'Click Software SHA2 testing'
	}, function(result) {
		if (result == null || result == "") {
			return;
		}
		var employeeObject = result;
		var plans = employeeObject.plans;
		// $("#plans").val(decode(plans));
		var taskArray = employeeObject.taskList;
		for (var i = 0; i < taskArray.length; i++) {
			var task = taskArray[i];
			insertRow2TaskTable(i, converTask2Row(task));
		}
	}, 'json');
}

function insertRow2TaskTable(index, row) {
	$('#task_dg').datagrid('insertRow', {
		index : index,
		row : row
	});
}

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
	var employee = $("#employee").val();
	var project = $("#project").val();
	var role = $("#role").val();
	if (employee == "" || project == "" || role == "") {
		return false;
	}
	return true;
}

function getSumbitData() {
	var employee = $("#employee").val();
	var project = $("#project").val();
	var role = $("#role").val();
	var plans = encode($("#plans").val());
	var tableData = "";
	var rows = $('#task_dg').datagrid('getRows');
	if (rows) {
		for (var i = rows.length - 1; i >= 0; i--) {
			var row = rows[i];
			var task = row.taskDesc;
			var hours = row.spentHours;
			var eta = row.eta;
			tableData += "{'task':'" + encode(task) + "'" + ",'hours':'"
					+ hours + "'" + ",'eta':'" + eta + "'}";
		}
	}

	var jsonString = "{'name':'" + employee + "','project':'" + project
			+ "','role':'" + role + "','taskList':[" + tableData
			+ "],'plans':'" + plans + "'}";
	return jsonString;
}

function encode(value) {
	if (value == null || value == "") {
		return value;
	}
	// return base64Util.encodeBase64(value);

	return base64Util.base64encode(value);
}

function decode(value) {
	if (value == null || value == "") {
		return value;
	}
	// return base64Util.decodeBase64(value);
	return base64Util.base64decode(value);
}

function replaceSpecial(value) {
	value = value.replace(/\r\n/g, "<br>")
	value = value.replace(/\n/g, "<br>");
	return value;
}

function retrieveSpecial(value) {
	if (value == null || value == "") {
		return value;
	}
	var newvalue = value.replace(/<br>/g, "\r\n");
	return newvalue;
}