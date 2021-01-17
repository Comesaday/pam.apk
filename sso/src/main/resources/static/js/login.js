$(document).ready(function () {
    // 登录功能
    $("#loginBtn").click(function (e) {
        $.ajax({
            type: "POST",
            url: ctx + "login",
            data: $("#loginForm").serialize(),
            dataType: "JSON",
            success: function (data) {
                if (data.success) {
                    window.location.href = ctx + "index";
                } else {
                    $(".message").text(data.message);
                }
            },
            error:function(jqXHR){
                $(".message").text("发生错误："+ jqXHR.status);
            }
        });
    });
});