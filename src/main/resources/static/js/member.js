$(function() {
    $("#password_check").click(function() {
        let password = $("#password").val();
        let memberId = $("#memberId").val();

        if(password == "") {
            alert("비밀번호를 입력하세요.");
        } else {
            var formData = new FormData();
            formData.append("password", password);

            $.ajax({
                url : "/members/" + memberId + "/confirm",
                type : "PUT",
                data : formData,
                processData: false,
                contentType: false
            }).done(function(res){
                if(res.success) {
                    location.href = "/members/" + memberId + "/edit";
                } else {
                    alert(res.msg);
                }
            }).fail(function(error){
                alert(error.responseJSON.msg);
            });
        }
    });

    $("#btn-modify").click(function() {
        let memberId = $("#memberId").val();
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
                url : "/members/" + memberId,
                type : "PUT",
                contentType: "application/json;charset=utf-8",
                data : JSON.stringify(data)
            }).done(function(res) {
                if(res.success) {
                    alert("비밀번호 수정이 완료되었습니다.");
                    location.href = "/";
                } else {
                    alert(res.msg);
                    return false;
                }
            }).fail(function(err) {
                alert(error.responseJSON.msg);
            });
        }
    });

    $("#btn-delete").click(function() {
        let memberId = $("#memberId").val();

        $.ajax({
            url : "/members/" + memberId,
            type : "DELETE",
            contentType: "application/json;charset=utf-8"
//            data : JSON.stringify(data)
        }).done(function(res) {
            if(res.success) {
                alert("회원 탈퇴 완료되었습니다.");
                location.href = "/";
            } else {
                alert("회원 탈퇴 실패하였습니다.");
            }
        }).fail(function(err) {
            alert(error.responseJSON.msg);
        });
    });


});