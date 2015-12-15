/**
 * 
 */
function newUser() {
	$('#employee_dlg').dialog('open').dialog('setTitle', 'New User');
	$('#employee_fm').form('clear');
	var team = $('#team').combobox('getText');
	url = 'EmployeeSaveServlet';
}

/**
 * 
 */
function editUser() {
	var row = $('#employee_dg').datagrid('getSelected');
	if (row) {
		$('#employee_dlg').dialog('open').dialog('setTitle', 'Edit Employee');
		$('#employee_fm').form('load', row);
		var team = $('#team').combobox('getText');
		url = 'EmployeeSaveServlet';
	}
}

/**
 * 
 */
function saveUser() {
	$('#employee_fm').find('input[name="team"]').val($('#team').combobox('getText'));
	$('#employee_fm').form('submit', {
		url : url,
		data : {team : $('#team').combobox('getText')},
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$('#employee_dlg').dialog('close'); // close the dialog
				$('#employee_dg').datagrid('reload'); // reload the user data
			} else {
				$.messager.show({
					title : 'Error',
					msg : result.msg
				});
			}
		}
	});
}

/**
 * 
 */
function removeUser() {
	var row = $('#employee_dg').datagrid('getSelected');
	var teamName =$('#team').combobox('getText');
	if (row) {
		$.messager.confirm('Confirm',
				'Are you sure you want to remove this user?', function(r) {
					if (r) {
						$.post('EmployeeDeleteServlet?', {
							team : teamName,
							employee : row.employee
						}, function(result) {
							if (result.success) {
								$('#employee_dg').datagrid('reload'); // reload
								// the
								// user
								// data
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.msg
								});
							}
						}, 'json');
					}
				});
	}
}

/**
 * 
 */
function searchEmployee() {
	$('#employee_dg').datagrid('load', {
		team : $('#team').combobox('getText')
	});
}

/**
 * 
 */
function initEmployeeGrid() {
	$('#employee_dg').datagrid({
		url : 'EmployeeListServlet',
		queryParams : {
			team : ''
		},
		columns : [ [ {
			field : 'employee',
			title : 'employee',
			width : 50
		}, {
			field : 'project',
			title : 'project',
			width : 50
		}, {
			field : 'role',
			title : 'role',
			width : 50
		}, {
			field : 'email',
			title : 'email',
			width : 50
		} ] ],
		title : "employee list",
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		fitColumns : true,
		toolbar : $('#employee_toolbar')
	});
}