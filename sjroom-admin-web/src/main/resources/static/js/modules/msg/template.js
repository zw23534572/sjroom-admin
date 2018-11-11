$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'msg/template/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: "id", width: 40, key: true},
            {label: '模板标题', name: 'tmpTitle', index: "tmp_title", width: 60},
            {label: '模板内容', name: 'tmpContent', index: "tmp_Content", width: 250},
            {label: 'PHP模板ID', name: 'tmpId', index: "tmp_id", width: 40},
            {label: '更新人', name: 'createBy', index: "create_by", width: 40},
            {label: '创建人', name: 'updateBy', index: "update_by", width: 40},
            {label: '更新时间', name: 'updateTime', index: "update_time", width: 100},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 100},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        multiboxonly: true,
        beforeSelectRow: beforeSelectRow,//js方法
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
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        model: {},
        showList: true,
        title: "消息模板",
        q: {
            content: null,
        },
        apps: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.model = {};
        },
        update: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id);
        },
        getInfo: function (id) {
            $.getJSON(baseURL + "msg/template/info/" + id, function (r) {
                vm.model = r.model;
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "msg/template/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.model.id == null ? "msg/template/save" : "msg/template/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.model),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'name': vm.q.name, 'appCode': vm.q.appCode},
                page: page
            }).trigger("reloadGrid");
        },
        changeApp: function () {
            vm.reload();
        }
    }

});