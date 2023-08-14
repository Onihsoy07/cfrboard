$(function() {
    let isIdChecked = false;

    $("#btn-duplicateCheck").click(function() {
        let username = $("#loginId").val();
        alert(username);
        console.log(username);
        $("form").load("/auth/ducheck form", "username="+username);
//        $.ajax({
//            url : "/auth/ducheck?username="+username,
//            type : "GET"
//        }).done(function(res) {
//            alert("성공");
//            location.reload();
//        }).fail(function() {
//            alert("실패");
//        })
    });

    $("#register").click(function() {
        if(isIdChecked){
            $("form").submit();
        }else {
            alert("id 중복체크를 해주세요");
        }
    });

});