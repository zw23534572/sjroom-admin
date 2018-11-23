var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        menu: {
            id:null,
            parentName: null,
            parentId: 0,
            type: 1,
            orderNum: 0
        }
    },
    methods: {
        getMenu: function (menuId) {
            //加载菜单树
            $.get(baseURL + "sys/menu/select", function (r) {
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.data);
                var node = ztree.getNodeByParam("id", menuId);
                ztree.selectNode(node);

                vm.menu.parentName = node.name;
            })
        },
        add: function () {
            var menuId = getMenuId();
            if (menuId == null) {
                return;
            }

            $.get(baseURL + "sys/menu/info/" + menuId, function (r) {
                vm.showList = false;
                vm.title = "添加";
                vm.menu = r.data;
                vm.menu.id = null;
                vm.menu.parentId = menuId;
                vm.getMenu(menuId);
            });
        },
        update: function () {
            var menuId = getMenuId();
            if (menuId == null) {
                return;
            }

            $.get(baseURL + "sys/menu/info/" + menuId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.data;
                vm.getMenu(vm.menu.parentId);
            });
        },
        del: function () {
            var menuId = getMenuId();
            if (menuId == null) {
                return;
            }

            if (confirm('确定要删除选中的记录？')){
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/menu/delete",
                    data: "menuId=" + menuId,
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
            };
        },
        saveOrUpdate: function () {
            if (vm.validator()) {
                return;
            }

            var url = vm.menu.id == null ? "sys/menu/save" : "sys/menu/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.menu),
                success: function(r){
                    if(r.success){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.menu.parentId = node[0].id;
                    vm.menu.parentName = node[0].name;

                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            Menu.table.refresh();
        },
        goback: function () {
            vm.showList = true;
        },
        validator: function () {
            if (isBlank(vm.menu.name)) {
                alert("菜单名称不能为空");
                return true;
            }

            //菜单
            if (vm.menu.type === 1 && isBlank(vm.menu.url)){
                alert("菜单URL不能为空");
                return true;
            }
        }
    }
});


var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '60px'},
        {
            title: '类型',
            field: 'type',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '60px',
            formatter: function (item, index) {
                if (item.type === 0) {
                    return '<span class="label label-primary">目录</span>';
                }
                if (item.type === 1) {
                    return '<span class="label label-success">菜单</span>';
                }
                if (item.type === 2) {
                    return '<span class="label label-warning">按钮</span>';
                }
            }
        },
        {title: '菜单名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '250px'},
        // {title: '上级菜单', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '150px'},
        {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '400px'},
        {title: '授权标识', field: 'perms', align: 'center', valign: 'middle', sortable: true},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'}
    ]
    return columns;
};


function getMenuId() {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "sys/menu/list", colunms);
    table.setExpandColumn(3);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
});
