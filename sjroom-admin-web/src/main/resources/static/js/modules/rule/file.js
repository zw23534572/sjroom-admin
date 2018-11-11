$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'rule/file/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: "id", width: 40, key: true},
            {label: '所属系统', name: 'appCode', index: "app_code", width: 60},
            {label: '规则标识', name: 'code', index: "code", width: 60},
            {label: '规则名称', name: 'name', index: "name", width: 80},
            {label: '规则说明', name: 'remark', width: 180},
            {label: '更新人', name: 'createBy', index: "create_by", width: 40},
            {label: '创建人', name: 'updateBy', index: "update_by", width: 40},
            {label: '更新时间', name: 'updateTime', index: "update_time", width: 100},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 100},
            // {
            //     label: '操作', width: 50,
            //     formatter: function (value, options, row) {
            //         return "<a href=\"javascript:;\" onclick=\"del(this,'" + value + "')\" class=\"ml-5\" style=\"text-decoration:none\">查看详情</a>";
            //     }
            // },
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

    new AjaxUpload('#upload', {
        action: baseURL + 'rule/file/upload?token=' + token,
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            return true;
            if (!(extension && /^(drl)$/.test(extension.toLowerCase()))) {
                alert('只支持drl文件！');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r.code == 0) {
                vm.model.ruleText = r.content;
            } else {
                alert(r.msg);
            }
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        model: {},
        showList: true,
        title: null,
        q: {
            name: null,
            appCode: "",
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
            vm.model = {appCode: "", ruleText: ""};
            if (vm.q.appCode != null) vm.model.appCode = vm.q.appCode;
        },
        addBatch: function () {
            var url = baseURL + "modules/rule/file-add-batch.html";
            layer_show("批量新增", url, null, null);
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
            $.getJSON(baseURL + "rule/file/info/" + id, function (r) {
                vm.model = r.model;
            });
        },
        deploy: function () {
            alert("开始发布");
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "rule/file/delete",
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
            var url = vm.model.id == null ? "rule/file/save" : "rule/file/update";
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
        saveBatch: function () {
            var url = "rule/file/saveBatch";
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
    },
    created: function () {
        $.getJSON(baseURL + "sys/app/list", function (r) {
            vm.apps = r.page.records;
        });
    },
});