$(function() {
    let isIdChecked = false;

    $("#btn-duplicateCheck").click(function() {
        const idMsg = document.getElementById("idMsg");
        let username = $("#loginId").val();
        if(username == "") {
            alert("아이디를 입력하세요.");
        } else {
            let data = {
                username : username
            }

            $.ajax({
                url : "/auth/duplicate-check",
                type : "POST",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data : JSON.stringify(data)
            }).done(function(res) {
                if(res.data) {
                    idMsg.innerHTML = "중복 아이디가 존재합니다.";
                    idMsg.style.display = "block";
                } else {
                    idMsg.style.display = "none";
                    isIdChecked = true;
                    $("#btn-duplicateCheck").attr("disabled", true);
                    $("#loginId").attr("disabled", true);
                    $("#mainId").attr("value", username);
                }
            }).fail(function(error) {
                alert(error.responseJSON.msg);
            })
        }
    });

    $("#register").click(function() {
        let data = {
            username : $("#mainId").val(),
            password : $("#password").val(),
            passwordCheck : $("#passwordCheck").val()
        }

        if(isIdChecked == false) {
            alert("id 중복체크를 해주세요");
            return false;
        } else if(data.password == "") {
            alert("비밀번호를 입력하세요.");
            return false;
        } else if(data.passwordCheck == "") {
            alert("비밀번호 확인을 입력하세요.");
            return false;
        } else if(data.password != data.passwordCheck) {
            alert("비밀번호가 다릅니다.");
            return false;
        }

        $.ajax({
            url : "/auth/register",
            type : "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data : JSON.stringify(data)
        }).done(function(res) {
            if (res.success) {
                location.href = "/";
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })
    });

});

