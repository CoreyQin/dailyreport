/**
 * 
 */
function newProject() {
	$('#project_dlg').dialog('open').dialog('setTitle', 'New Project');
	$('#project_fm').form('clear');
//	var team = $('#team').combobox('getText');
//	url = 'ProjectSaveServlet';
}

/**
 * 
 */
function editProject() {
	var row = $('#project_dg').datagrid('getSelected');
	if (row) {
		$('#project_dlg').dialog('open').dialog('setTitle', 'Edit Project');
		$('#project_fm').form('clear');
		$('#project_fm').form('load', row);
//		var team = $('#team').combobox('getText');
//		url = 'ProjectSaveServlet';
	}
}

/**
 * 
 */
function saveProject() {
	$('#project_fm').find('input[name="team"]').val($('#team').combobox('getText'));
	$('#project_fm').form('submit', {
		url : 'ProjectSaveServlet',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$('#project_dlg').dialog('close'); // close the dialog
				$('#project_dg').datagrid('reload'); // reload the user data
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
function searchProject() {
	$('#project_dg').datagrid('load', {
		team : $('#team').combobox('getText')
	});
}


/**
 * 
 */
function removeProject() {
	var row = $('#project_dg').datagrid('getSelected');
	var teamName =$('#team').combobox('getText');
	if (row) {
		$.messager.confirm('Confirm',
				'Are you sure you want to remove this project?', function(r) {
					if (r) {
						$.post('ProjectDeleteServlet', {
							team : teamName,
							project : row.project
						}, function(result) {
							if (result.success) {
								$('#project_dg').datagrid('reload'); // reload
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
function initProjectGrid() {
	$('#project_dg').datagrid({
		url : 'ProjectListServlet',
		queryParams : {
			team : ''
		},
		columns : [ [ {
			field : 'project',
			title : 'project',
			width : 400
		}, {
			field : 'rfa',
			title : 'rfa',
			width : 100
		},{
			field : 'status',
			title : 'status',
			width : 100
		},{
			field : 'level',
			title : 'level',
			width : 98
		} ] ],
		title : "project list",
		singleSelect : true,
		fitColumns : true,
		toolbar : $('#project_toolbar')
	});
}