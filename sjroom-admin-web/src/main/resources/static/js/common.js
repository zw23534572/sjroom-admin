//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost/index.html?id=123
// T.p('id') --> 123;
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

//请求前缀
var baseURL = "/";

//登录token
var token = localStorage.getItem("token");
if (token == 'null') {
    parent.location.href = baseURL + 'login.html';
}

//jquery全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        "token": token
    },
    xhrFields: {
        withCredentials: true
    },
    complete: function (xhr) {
        var code = xhr.responseJSON.code % 1000;
        //token过期，则跳转到登录页面
        if (code == 401) {
            parent.location.href = baseURL + 'login.html';
        }
    }
});

//jqgrid全局配置
$.extend($.jgrid.defaults, {
    ajaxGridOptions: {
        headers: {
            "token": token
        }
    }
});

//权限判断
function hasPermission(permission) {
    return true;
    if (window.parent.permissions.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

//重写alert
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
}

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']},
        function () {//确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
}

function getRowData(rowId) {
    var rowData = $("#jqGrid").jqGrid('getRowData', rowId);
    return rowData;
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

/*弹出层*/

/*
 参数解释：
 title	标题
 url		请求的url
 id		需要操作的数据id
 w		弹出层宽度（缺省调默认值）
 h		弹出层高度（缺省调默认值）
 */
function layer_show(title, url, w, h, callback) {
    if (title == null || title == '') {
        title = false;
    }
    ;
    if (url == null || url == '') {
        url = "404.html";
    }
    ;
    if (w == null || w == '') {
        w = 800;
    }
    ;
    if (h == null || h == '') {
        h = ($(window).height() - 50);
    }
    ;
    layer.open({
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            maxmin: true,
            shade: 0.4,
            title: title,
            content: url,
            end: function () {
                console.log("关闭窗体");
                if (typeof(callback) === "function") {
                    callback("ok");
                }
            }
        }
    );
}

/*关闭弹出框口*/
function layer_close() {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

/**解析url参数 **/
function get_request() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

function beforeSelectRow() {
    $("#jqGrid").jqGrid('resetSelection');
    return (true);
}