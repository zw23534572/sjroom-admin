$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'msg/email/list',
        datatype: "json",
        colModel: [
            {label: '消息ID', name: 'messageId', index: "message_id", width: 100, key: true, hidden: true},
            {label: '接收账号', name: 'userIds', index: "user_ids", width: 80},
            {label: '消息内容', name: 'sendContent', index: "send_content", width: 400},
            {label: '消息模板ID', name: 'templateId', index: "template_id", width: 100,hidden: true},
            {label: '消息模板参数', name: 'templateParams', index: "template_params", width: 100,hidden: true},
            {
                label: '消息状态', name: 'status', width: 60, formatter: function (value, options, row) {
                var statusMsg;
                if (value == 10) {
                    statusMsg = "<span class='label label-info radius'>初始化</span>";
                } else if (value == 20) {
                    statusMsg = "<span class='label label-info radius'>发送中</span>";
                } else if (value == 30) {
                    statusMsg = "<span class='label label-success radius'>发送成功</span>";
                } else if (value == 40) {
                    statusMsg = "<span class='label label-danger radius'>发送失败</span>"
                }
                return statusMsg;
            }
            },
            {label: '返回code', name: 'errCode', index: "err_code", width: 40},
            {label: '返回消息', name: 'errMsg', index: "err_msg", width: 40},
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
        reload: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'content': vm.q.content},
                page: page
            }).trigger("reloadGrid");
        }

    }
});