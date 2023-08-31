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

    $("#btn-modify").click(function() {
        let data = {
            password : $("#password").val(),
            passwordCheck : $("#passwordCheck").val()
        };

        if(data.password == "") {
            alert("비밀번호를 입력하세요.");
        } else if(data.passwordCheck == "") {
            alert("비밀번호 확인을 입력하세요.");
        } else if(data.password != data.passwordCheck) {
            alert("비밀번호와 비밀번호 확인이 다릅니다.");
        } else {
            $.ajax({
                url : "/member",
                type : "PUT",
                contentType: "application/json;charset=utf-8",
                data : JSON.stringify(data)
            }).done(function(res) {
                if(res == "ok") {
                    alert("비밀번호 수정이 완료되었습니다.");
                    location.href = "/";
                } else if(res = "same") {
                    alert("기존 비밀번호와 새 비밀번호가 일치합니다.");
                    return false;
                } else if(res = "non") {
                    alert("비밀번호와 비밀번호 확인이 다릅니다.");
                    return false;
                }
            }).fail(function(err) {
                alert("통신에 실패하였습니다.");
            });
        }
    });

    $("#btn-delete").click(function() {
        const username = $("#loginId").val();

        $.ajax({
            url : "/member?id=" + username,
            type : "DELETE",
            contentType: "application/json;charset=utf-8"
//            data : JSON.stringify(data)
        }).done(function(res) {
            if(res == true) {
                alert("회원 탈퇴 완료되었습니다.");
                location.href = "/";
            } else {
                alert("회원 탈퇴 실패하였습니다.");
            }
        }).fail(function(err) {
            alert("통신에 실패하였습니`다.");
        });
    });


});