var numberserviceURL = "/number/";
$(function () {
    $("#jqGrid").jqGrid({
        url: numberserviceURL + 'getRuleList',
        datatype: "json",
        mtype:'post',
        colModel: [
            {label: 'ID', name: 'id', index: "id", width: 40, key: true},
            {label: '规则编码', name: 'ruleCode', index: "ruleCode", width: 60},
            {label: '规则名称', name: 'ruleName', index: "ruleName", width: 60},
            {label: '规则内容', name: 'ruleContent', index: "ruleContent", width: 240},
            {label: '序列长度', name: 'seqLength', index: "seqLength", width: 40},
            {label: '序列起始值', name: 'seqStartValue', index: "seqStartValue", width: 40}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
        multiboxonly: true,
        beforeSelectRow: beforeSelectRow,//js方法
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.data",
            page: "data.pageNo",
            total: "data.totalPage",
            records: "data.totalItem"
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
        validateResult:false,
        ruleIsChange:false,
        showList: true,
        title: "单号规则",
        q:{
            ruleCode:""
        },
        apps: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "添加规则";
            vm.model = {};
        },
        del: function () {
            var rowData = vm.getSelectedRow();

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: numberserviceURL + "deleteRule",
                    contentType: "application/json",
                    data: JSON.stringify({'ruleCode':rowData.ruleCode}),
                    success: function (r) {
                        if(r.code % 1000 === 200) {
                            alert("删除成功");
                            vm.reload();
                        }else {
                            alert(r.msg);
                        }

                    }
                });
            });
        },
        save: function () {
            if(vm.ruleIsChange) {
                alert("规则内容发生改变，请验证通过后保存");
                return;
            }
            if(!vm.validateResult) {
                alert("请验证通过后进行保存");
                return;
            }
            if(!vm.model.ruleCode) {
                alert("规则编码不能为空");
                return;
            }
            if(!vm.model.ruleName) {
                alert("规则名称不能为空");
                return;
            }
            if(!vm.model.ruleContent) {
                alert("规则内容不能为空");
                return;
            }
            if(vm.model.seqStartValue && isNaN(vm.model.seqStartValue)) {
                alert("起始值只能是数字");
                return;
            }
            if(vm.model.seqLength && isNaN(vm.model.seqLength)) {
                alert("长度只能是数字");
                return;
            }
            $.ajax({
                type: "POST",
                url: numberserviceURL + "buildRule",
                contentType: "application/json",
                data: JSON.stringify(vm.model),
                success: function (r) {

                    if (r.code%1000 === 200) {
                        alert("保存成功");
                        vm.reload();
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
        ,
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'ruleCode':vm.q.ruleCode},
                page:page
            }).trigger("reloadGrid");
        },
        changeApp: function () {
            vm.reload();
        },
        validate:function () {

        },
        getSelectedRow: function() {
            var selectedRowID = $("#jqGrid").getGridParam("selrow");
            if(!selectedRowID){
                alert("请选择一条规则");
            }
            return $("#jqGrid").jqGrid("getRowData",selectedRowID);
        },
        changeRule:function () {
            // 规则内容已经发生改变，需要验证
            vm.ruleIsChange = true;
        },
        validateRule:function () {
            if(isNaN(vm.model.seqLength)) {
                vm.model.seqLength = 8;
            }
            if(isNaN(vm.model.seqStartValue)) {
                vm.model.seqStartValue = 1;
            }

            $.ajax({
                type: "POST",
                url: numberserviceURL + "validateRule",
                contentType: "application/json",
                data: JSON.stringify(vm.model),
                success: function (r) {
                    if (r.code%1000 === 200) {
                        var colors = ['red','blue','fuchsia','green','lime','maroon','navy','olive','purple','silver','teal','yellow'];
                        var html = "<div>规则验证<font color='green'><b>成功</b></font></div><div>模板: " + r.data.demoRule + "</div><div>示例: ";
                        var ruleDemo = r.data.demoRule;
                        var paramTipc = "";
                        for(var i = 0,j = r.data.demoParams.length; i < j; i++) {
                            var paramValue = Math.floor(Math.random()*100);
                            ruleDemo = ruleDemo.replace("{" + r.data.demoParams[i] + "}","<font color='" + colors[i%12] + "'>" + paramValue + "</font>");
                            paramTipc = paramTipc + "," + r.data.demoParams[i] + "：<font color='" + colors[i%12] + "'>" + paramValue + "</font>";
                        }
                        html = html + ruleDemo + (paramTipc !== "" ?  "(" + paramTipc.substring(1) + ")" : "") + "</div>";
                        $("#example").html(html);
                        vm.validateResult = true;
                    } else {
                        $("#example").html("规则验证<font color='red'><b>失败</b></font>:" + r.msg);
                        vm.validateResult = false;
                    }
                }
            });
            // 验证后
            vm.ruleIsChange = false;
        }
    }
});
