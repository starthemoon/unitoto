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
        	var temp = {
        			userid: $('#userID').val()
        	};
            window.location.href = 'homepage/main.jsp?userid=' + temp.userid;
        }
    });
}

function register() {
    window.location.href = 'homepage/register.jsp';
}