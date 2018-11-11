var vm = new Vue({
    el: '#rrapp',
    data: {
        model: {},
        showList: true,
        title: null,
        q: {
            name: null,
            appCode: null,
        },
        apps: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        btnCancel: function () {
            layer_close();
        },
        btnOk: function () {
            var ids = getSelectedRows();
            for (index in ids) {
                var id = ids[index];
                var rowData = $("#jqGrid").jqGrid("getRowData", ids[index]);
                parent.vm.appendRule(id, rowData);
            }

            layer_close();
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name, 'appCode': vm.q.appCode},
                page: page
            }).trigger("reloadGrid");
        },
        initGrid: function () {
            $("#jqGrid").jqGrid({
                url: baseURL + 'rule/file/list?excludeIds=' + vm.q.ids + '&appCode=' + vm.q.appCode,
                datatype: "json",
                colModel: [
                    {label: 'ID', name: 'id', index: "id", width: 40, key: true},
                    {label: '所属系统', name: 'appCode', index: "app_code", width: 80},
                    {label: '规则标识', name: 'code', index: "code", width: 80},
                    {label: '规则名称', name: 'name', index: "name", width: 80},
                    {label: '规则说明', name: 'remark', width: 180},
                    {label: '更新人', name: 'createBy', index: "create_by", width: 80},
                    {label: '更新时间', name: 'updateTime', index: "update_time", width: 160},
                ],
                viewrecords: true,
                height: 385,
                rowNum: 10,
                rowList: [10, 30, 50],
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                multiselect: true,
                pager: "#jqGridPager",
                jsonReader: {
                    root: "page.records",
                    page: "page.current",
                    total: "page.pages",
                    records: "page.total"
                },
                prmNames: {
                    page: "page",
                    rows: "limit",
                    order: "order"
                },
                gridComplete: function () {
                    //隐藏grid底部滚动条
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        },
        validator: function () {
            if (isBlank(vm.model.name)) {
                alert("规则文件不能为空");
                return true;
            }
        },
        init: function (id) {
            $.getJSON(baseURL + "rule/package/info/" + id, function (r) {
                vm.model = r.model;
                vm.reloadRuleFileGrid();
            });
        },
    },
    created: function () {
        $.getJSON(baseURL + "sys/app/list", function (r) {
            vm.apps = r.page.records;
            var request = get_request();
            if (request['appCode'] != null) {
                vm.q.appCode = request['appCode'];
            }
            if (request['ids'] != null) {
                vm.q.ids = request['ids'];
            }
            vm.initGrid();
        });
    },
});