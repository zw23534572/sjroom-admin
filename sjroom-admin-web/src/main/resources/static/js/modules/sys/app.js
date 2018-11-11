$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/app/list',
        datatype: "json",
        colModel: [			
       		{ label: '系统ID', name: 'id', width: 70, key: true },
			{ label: '系统标识', name: 'appCode', width: 70 },
			{ label: '系统名', name: 'appName'},
			
			{ label: '状态', name: 'yn', width: 70, formatter: function(value, options, row){
				return value === 0 ? 
					'<span class="label label-danger">删除</span>' : 
					'<span class="label label-success">正常</span>';
			}},
			{ label: 'yn', name: 'yn', hidden: true},
			{ label: '创建人', name: 'createUser', width: 80 },
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.records",
            page: "page.current",
            total: "page.pages",
            records: "page.total"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			appName: null
		},
		showList: true,
		title:null,
		app:{
			
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增系统";
			vm.app = {};
		},
		update: function () {
			var appId = getSelectedRow();
			if(appId == null){
				return ;
			}
			
			var appRow = getRowData(appId);
			if(appRow.yn == 0){
				alert("已删除不能编辑");
                return;
			}
			
			vm.showList = false;
            vm.title = "修改系统";
			
			vm.getApp(appId);
		},
		del: function () {
			var appId = getSelectedRow();
			if(appId == null){
				return ;
			}
			
			var appRow = getRowData(appId);
			if(appRow.yn == 0){
				alert("已删除不能再删除");
                return;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "sys/app/delete",
                    contentType: "application/json",
				    data: JSON.stringify(appId),
				    success: function(r){
						if(r.success){
							alert('操作成功', function(){
                                vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

			var url = vm.app.id == null ? "sys/app/save" : "sys/app/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.app),
			    success: function(r){
			    	if(r.success){
						alert('操作成功', function(){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getApp: function(appId){
			$.get(baseURL + "sys/app/info/"+appId, function(r){
				vm.app = r.data.app;
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'appName': vm.q.appName},
                page:page
            }).trigger("reloadGrid");
		},
        validator: function () {
            if(isBlank(vm.app.appCode)){
                alert("系统标识不能为空");
                return true;
            }

            if(isBlank(vm.app.appName)){
                alert("系统名不能为空");
                return true;
            }
        },
        receiveNumberEdit: function () {
        	return false;
        }
	}
});