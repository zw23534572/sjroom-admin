$(function () {

    var $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;

    uploader = WebUploader.create({

        // 不压缩image
        resize: false,

        // swf文件路径
        swf: baseURL + 'libs/webuploader/Uploader.swf',

        // 文件接收服务端。
        server: baseURL + 'rule/file/upload-batch?token=' + token,

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        // fileSizeLimit: 1024,

        // 只允许选择图片文件。
        // accept: {
        //     title: '只允许选择drl文件',
        //     extensions: 'drl',
        // }
    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        $list.append('<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
            '</div>');
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
        }

        $li.find('p.state').text('上传中');

        $percent.css('width', percentage * 100 + '%');
    });


    uploader.on('uploadBeforeSend', function (obj, data, headers) {

        data.appCode = vm.model.appCode;
        data.name = vm.model.name;
        data.remark = vm.model.remark;
    });

    uploader.on('uploadSuccess', function (file, r) {
        if (r.code == 0) {
            $('#' + file.id).find('p.state').text('已上传');
        } else {
            state = 'uploading'
            $('#' + file.id).find('p.state').html('上传出错:<span style="color: red">' + r.msg + '</span>');
        }
    });

    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').fadeOut();
    });

    uploader.on('all', function (type) {
        if (type === 'startUpload') {
            state = 'uploading';
        } else if (type === 'stopUpload') {
            state = 'paused';
        } else if (type === 'uploadFinished') {
            state = 'done';
        }

        if (state === 'uploading') {
            $btn.text('暂停上传');
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on('click', function () {
        var appCode = vm.model.appCode;
        if (appCode == "" || appCode == null) {
            alert("系统名称不能为空");
            return;
        }
        if (state === 'uploading') {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        title: "批量新增",
        model: {appCode: ""},
        title: null,
        apps: []
    },
    methods: {
        btnCancel: function () {
            layer_close();
            parent.vm.reload();
        },
    },
    created: function () {
        $.getJSON(baseURL + "sys/app/list", function (r) {
            vm.apps = r.page.records;
        });
    },
});