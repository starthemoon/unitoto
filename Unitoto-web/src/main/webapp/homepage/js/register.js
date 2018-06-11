function register() {
    $.ajax({
        type: "post",
        url: "register.do",
        dataType: "text",
        data:
            {
                "userid": $('#userID').val(),
                "userpassword": $('#userPassword').val(),
                "username": $('#userName').val()
            },
        success: function() {
            window.location.href = 'main.jsp';
        }
    });
}