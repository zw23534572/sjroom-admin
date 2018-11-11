$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'msg/message/list',
        datatype: "json",
        colModel: [
            {label: '消息ID', name: 'id', index: "id", width: 100, key: true, hidden: true},
            {label: '消息编号', name: 'msgNo', index: "msg_no", width: 100},
            {label: '消息名称', name: 'msgName', index: "send_content", width: 120},
            {label: '模板ID', name: 'templateId', index: "template_id", width: 40},
            {label: '用户组', name: 'userGroupName', index: "user_group_name", width: 100},
            {label: '接收微信', name: 'wechatGroupName', index: "wechat_group_name", width: 80},
            {label: '接收钉钉', name: 'dingdingGroupName', index: "dingding_group_name", width: 80},
            {label: '接收邮件', name: 'emailGroupName', index: "email_group_name", width: 80},
            {label: '接收短信', name: 'smsGroupName', index: "sms_group_name", width: 80},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 100},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
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
        title: null,
        q: {
            content: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            var url = "http://message-platform.dazong.com/editUserGroup";
            layer_show("新增", url, null, null);
        },
        update: function () {
            var url = "http://message-platform.dazong.com/editUserGroup?id=" + vm.model.id;
            layer_show("新增", url, null, null);
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "msg/message/delete",
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
        reload: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'content': vm.q.content},
                page: page
            }).trigger("reloadGrid");
        }

    }
});