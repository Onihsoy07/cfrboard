$(function() {
    $("#password_check").click(function() {
        let password = $("#password").val();

        if(password == "") {
            alert("비밀번호를 입력하세요.");
        } else {
            var formData = new FormData();
            formData.append("password", password);

            $.ajax({
                url : "/member/confirm",
                type : "POST",
                data : formData,
                processData: false,
                contentType: false
            }).done(function(res){
                if(res == true) {
                    location.href = "/member";
                } else {
                    alert("비밀번호가 다릅니다.");
                }
            }).fail(function(error){
                alert("통신에 실패하였습니다.");
            });
        }
    });
});