$(function() {
    let isIdChecked = false;

    $("#btn-duplicateCheck").click(function() {
        const idMsg = document.getElementById("idMsg");
        let username = $("#loginId").val();
        if(username == "") {
            alert("아이디를 입력하세요.");
        } else {
            $.ajax({
                url : "/auth/duplicate-check?username="+username,
                type : "GET"
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
            }).fail(function() {
                console.log("에러");
            })
        }
    });

    $("#register").click(function() {
        let password = $("#password").val();
        let passwordCheck = $("#passwordCheck").val();

        if(isIdChecked == false) {
            alert("id 중복체크를 해주세요");
        } else if(password == "") {
            alert("비밀번호를 입력하세요.");
        } else if(passwordCheck == "") {
            alert("비밀번호 확인을 입력하세요.");
        } else if(password != passwordCheck) {
            alert("비밀번호가 다릅니다.");
        } else {
            $("form").submit();
        }
    });

});

