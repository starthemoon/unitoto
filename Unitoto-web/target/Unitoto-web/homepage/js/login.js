function login() {
    $.ajax({
        type: "post",
        url: "homepage/login.do",
        dataType: "text",
        data:
            {
                "userid": $('#userID').val(),
                "userpassword": $('#userPassword').val()
            },
        success: function() {
            window.location.href = 'homepage/main.jsp';
        }
    });
}

function register() {
    window.location.href = 'homepage/register.jsp';
}