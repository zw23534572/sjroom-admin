$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'id', index: "id", width: 45, key: true},
            {label: '账号', name: 'account', index: "account", width: 75},
            {label: '用户名', name: 'userName', index: "user_name", width: 40},
            {label: '钉钉ID', name: 'dingdingId', index: "dingding_id", width: 90},
            {label: '微信ID', name: 'weixinId', index: "weixin_id", width: 90},
            {label: '邮箱', name: 'email', width: 120},
            {label: '手机号', name: 'mobile', width: 65},
            {
                label: '状态', name: 'status', width: 40, formatter: function (value, options, row) {
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
                }
            },
            {label: '创建时间', name: 'createTime', index: "create_time"}
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
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            user_name: null
        },
        showList: true,
        title: null,
        roleList: {},
        user: {
            status: 1,
            roleIdList: []
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {status: 1, roleIdList: []};

            //获取角色信息
            this.getRoleList();
        },
        update: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getUser(id);
            //获取角色信息
            this.getRoleList();
        },
        del: function () {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.success) {
                            alert('操作成功', function () {
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
            if (vm.validator()) {
                return;
            }

            var url = vm.user.id == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function (r) {
                    if (r.success) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getUser: function (id) {
            $.get(baseURL + "sys/user/info/" + id, function (r) {
                vm.user = r.data.user;
                vm.user.password = null;
            });
        },
        getRoleList: function () {
            $.get(baseURL + "sys/role/select", function (r) {
                vm.roleList = r.data.list;
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'user_name': vm.q.user_name},
                page: page
            }).trigger("reloadGrid");
        },
        validator: function () {
            // if (isBlank(vm.user.account)) {
            //     alert("用户账号不能为空");
            //     return true;
            // }

            if (isBlank(vm.user.userName)) {
                alert("用户名称不能为空");
                return true;
            }

            if (vm.user.id == null && isBlank(vm.user.password)) {
                alert("密码不能为空");
                return true;
            }

            if (isBlank(vm.user.email)) {
                alert("邮箱不能为空");
                return true;
            }

            if (!validator.isEmail(vm.user.email)) {
                alert("邮箱格式不正确");
                return true;
            }
        }
    }
});