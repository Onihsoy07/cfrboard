$(function() {
    $("#upload_btn").click(function() {
//        $("#cfr_form").preventDefault(); // 폼의 자체 서브밋 동작을 비활성

        var imageInput = $("#imageFile")[0];
        var formData = new FormData();
        formData.append("image", imageInput.files[0]);

        $.ajax({
            url : "/cfr",
            type : "POST",
            data : formData,
            processData: false, //프로세스 데이터 설정 : false 값을 해야 form data로 인식합니다
            contentType: false //헤더의 Content-Type을 설정 : false 값을 해야 form data로 인식합니다
        }).done(function(res){
            if(res == true) {
                location.href = "/cfr/list";
            } else {
                alert("사진이 조건에 맞지 않습니다.");
            }
        }).fail(function(error){
            alert("통신에 실패하였습니다.");
        });
    });

});

function inputFile() {
    $('#imageFile').click();
}