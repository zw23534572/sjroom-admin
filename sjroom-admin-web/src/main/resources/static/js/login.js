var vm = new Vue({
    el: '#rrapp',
    data: {
        username: '',
        password: '',
        account: '',
        error: false,
        showList: true,
        errorMsg: '',
        token: ''
    },
    beforeCreate: function () {
        if (self != top) {
            top.location.href = self.location.href;
        }
    },
    methods: {
        login: function () {
            vm.initErrorMsg();
            var data = "username=" + vm.username + "&password=" + vm.password;
            $.ajax({
                type: "POST",
                url: baseURL + "sys/login",
                data: data,
                dataType: "json",
                success: function (r) {
                    if (r.success) {//登录成功
                        vm.token = r.data.token;
                        if (r.success) {//登录成功
                            localStorage.setItem("token", r.data.token);
                            parent.location.href = 'index.html';
                        } else {
                            vm.error = true;
                            vm.errorMsg = r.msg;
                        }
                    } else {
                        vm.error = true;
                        vm.errorMsg = r.msg;
                    }
                }
            });
        },
        validator: function () {
            if (isBlank(vm.account)) {
                alert("账号不能为空");
                return false;
            }
            return true;
        },
        initErrorMsg: function () {
            vm.error = false;
            vm.errorMsg = "";
        }
    }
});