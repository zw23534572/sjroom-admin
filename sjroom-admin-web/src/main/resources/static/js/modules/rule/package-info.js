$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'rule/package/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: "id", width: 40, key: true},
            {label: '所属系统', name: 'appCode', index: "app_code", width: 60},
            {label: '知识包标识', name: 'code', index: "code", width: 60},
            {label: '知识包名称', name: 'name', index: "name", width: 80},
            {label: '知识包说明', name: 'remark', width: 150},
            {
                label: '实例列表', name: 'instance', width: 80,
                formatter: function (value, options, row) {
                    if (value != undefined && value != "" && value.indexOf(",")) {
                        var instanceCountArr = value.split(",");
                        var instanceSuccessCount = instanceCountArr[0];
                        var instanceFailCount = instanceCountArr[1];
                        if (instanceFailCount > 0) {
                            return '<a href="javascript:void(0);" onclick="vm.showInstanceList(' + options.rowId + ')">' + instanceSuccessCount + '台OK,' + instanceFailCount + '台fail</a>';
                        } else {
                            return '<a href="javascript:void(0);" onclick="vm.showInstanceList(' + options.rowId + ')">' + instanceSuccessCount + '台OK</a>';
                        }
                    }
                    return '';
                }
            },
            {
                label: '规则检测', name: 'ruleCount', width: 80,
                formatter: function (value, options, row) {
                    if (value != undefined && value != "" && value.indexOf(",")) {
                        var ruleCountArr = value.split(",");
                        var localRuleCount = ruleCountArr[0];
                        var remoteRuleCount = ruleCountArr[1];
                        if (localRuleCount == remoteRuleCount) {
                            return '<span class="label label-success">正常</span>';
                        } else {
                            return '<span class="label label-danger">本地(' + localRuleCount + '):服务器(' + remoteRuleCount + ')</span>';
                        }
                    }
                    return '';
                }
            },
            {
                label: '发布状态', name: 'status', width: 80,
                formatter: function (value, options, row) {
                    return value === 1 ?
                        '<span class="label label-danger">未发布</span>' :
                        '<span class="label label-success">已发布</span>';
                }
            },
            {label: '更新人', name: 'createBy', index: "create_by", width: 40},
            {label: '创建人', name: 'updateBy', index: "update_by", width: 40},
            {label: '更新时间', name: 'updateTime', index: "update_time", width: 100},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 100},
            {
                label: '操作', name: 'id', width: 50,
                formatter: function (value, options, row) {
                    return "<a href='javascript:void(0)' onclick='vm.info(" + value + ")' class=\"ml-5\" style=\"text-decoration:none\">详情</a>";
                }
            },
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

    $("#jqRuleFileGrid").jqGrid({
        url: baseURL + 'rule/file/queryByKnowledId',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: "id", width: 40, key: true},
            {label: '所属系统', name: 'appCode', index: "app_code", width: 80},
            {label: '规则标识', name: 'code', index: "code", width: 100},
            {label: '规则名称', name: 'name', index: "name", width: 100},
            {label: '规则说明', name: 'remark', width: 200},
            {label: '更新人', name: 'createBy', index: "create_by", width: 80},
            {label: '更新时间', name: 'updateTime', index: "update_time", width: 160},
            {
                label: '操作', name: 'id', width: 50,
                formatter: function (value, options, row) {
                    return "<a href='javascript:void(0)' onclick='delRow(" + value + ")'  class=\"ml-5\" style=\"text-decoration:none\">删除</a>";
                }
            },
        ],
        viewrecords: true,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        pager: "#jqRuleFileGridPager",
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
            $("#jqRuleFileGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

});

function delRow(id) {
    if (vm.isReadOnly) {
        alert("目前为只读状态，无法删除.")
        return;
    }

    $("#jqRuleFileGrid").jqGrid("delRowData", id);
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        model: {},
        showList: true,
        isReadOnly: false,
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
            vm.model = {appCode: ""};
            if (vm.q.appCode != null) vm.model.appCode = vm.q.appCode;
            vm.reloadRuleFileGrid();
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
        info: function (id) {
            vm.showList = false;
            vm.title = "查看详情";
            vm.isReadOnly = true;
            vm.getInfo(id);
        },
        getInfo: function (id) {
            $.getJSON(baseURL + "rule/package/info/" + id, function (r) {
                vm.model = r.model;
                vm.reloadRuleFileGrid();
            });
        },
        showInstanceList: function (id) { //展示实例列表
            if (id == "" || id == null) {
                alert("请先选择系统名称");
                return;
            }
            var url = baseURL + "modules/rule/package-instance-list.html?id=" + id;
            layer_show("实例列表", url, 800, 230);
        },
        addRuleFile: function () {
            var appCode = vm.model.appCode;
            if (appCode == "" || appCode == null) {
                alert("请先选择系统名称");
                return;
            }
            var ids = jQuery("#jqRuleFileGrid").jqGrid('getDataIDs');
            var url = "/modules/rule/package-file-query.html" + "?ids=" + ids + "&appCode=" + appCode;
            layer_show("添加规则文件列表", url, null, null);
        },
        appendRule: function (id, rowData) {
            $("#jqRuleFileGrid").jqGrid("addRowData", id, rowData, "end");
        },
        publish: function () {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('你确定要发布吗？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "rule/package/publish",
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
        unpublish: function () {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('你确定要取消发布吗？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "rule/package/unpublish",
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
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "rule/package/delete",
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
            //获取所有的规则文件ID
            var ids = $("#jqRuleFileGrid").jqGrid('getDataIDs');
            vm.model.ruleFileIds = ids;

            var url = vm.model.id == null ? "rule/package/save" : "rule/package/update";
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
        reloadRuleFileGrid: function () {
            vm.showList = false;
            var page = $("#jqRuleFileGrid").jqGrid('getGridParam', 'page');
            $("#jqRuleFileGrid").jqGrid('setGridParam', {
                postData: {'appCode': vm.model.appCode, 'knowledId': vm.model.id},
                page: page
            }).trigger("reloadGrid");
        },
        changeApp: function () {
            vm.model.code = null;
            vm.reloadRuleFileGrid();
        },
        changePackAgeApp: function () {
            vm.reload();
        }
    },
    created: function () {
        $.getJSON(baseURL + "sys/app/list", function (r) {
            vm.apps = r.page.records;
        });
    },

});