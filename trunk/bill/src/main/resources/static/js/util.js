$(function () {
    // if (!sessionStorage.state) {
    //     alert("用户登录状态失效");
    //     location.replace("/static/html/login.html")
    // }
});

function sessionFind(key) {
    return JSON.parse(sessionStorage[key]);
}

function sessionSave(key, value) {
    sessionStorage[key] = JSON.stringify(value);
}

function timestamp2String(timestamp) {
    return new Date(timestamp).toLocaleString();
}
function getUtil(url, params) {
    var rst;
    $.ajax({
        type: "get",
        url: "/web/" + url,
        data: params,
        dataType: "json",
        async: false,
        success: function (data) {
            rst = data.data;
        },
        error: function (e) {
            console.error(e);
            alert("操作失败，请稍后再试。" + e);
        }
    });
    return rst;
}