var vm = new Vue({
    el: '#rrapp',
    data: {
        id: null,
        title: "实例列表",
    },
    methods: {
        query: function () {
            vm.reload();
        },
        initGrid: function () {
            $("#jqGrid").jqGrid({
                url: baseURL + 'rule/package/instance/list?id=' + this.id,
                datatype: "json",
                colModel: [
                    {label: '机器', name: 'ip', width: 120},
                    {label: '值', name: 'data', width: 400},
                    {label: '消费者md5', name: 'consumerMD5', width: 260},
                    {label: '提供者md5', name: 'providerMD5', width: 260},
                    {
                        label: '状态', width: 80,
                        formatter: function (value, options, row) {
                            return '<span class="label label-success">正常</span>';
                        }
                    },
                ],
                viewrecords: true,
                rownumbers: true,
                rownumWidth: 25,
                autowidth: true,
                jsonReader: //返回值json对象
                    {
                        root: "list",
                    },
                gridComplete: function () {
                    //隐藏grid底部滚动条
                    $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'id': vm.id},
                page: page
            }).trigger("reloadGrid");
        },
    },
    created: function () {
        var request = get_request();
        if (request['id'] != null) {
            this.id = request['id'];
            this.initGrid();
        }
    },

});