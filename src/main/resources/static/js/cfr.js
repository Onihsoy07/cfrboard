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

//이미지 압축
async function handleImageUpload(event) {

    const imageFile = event.target.files[0];
    document.getElementById("preview").src = "";

    if(imageFile.size/1024/1024 >= 1.99) {
        var resize_progress = document.querySelector(".resize_progress");
        resize_progress.style.display = "block";
        resize_progress.value = "0";

        const options = {
            maxSizeMB: 1.9,
            maxWidthOrHeight: 1920,
            useWebWorker: true,
            onProgress: function(c) {
                resize_progress.value = c;
                if(c >= 100) {
                    resize_progress.style.display = "none";
                }
            }
        }

        try {
            const compressedFile = await imageCompression(imageFile, options);
            const resizingFile = new File([compressedFile], imageFile.name, { type: imageFile.type });
            readURL(resizingFile);
        } catch (error) {
            console.log(error);
        }
    } else {
        readURL(imageFile);
    }
}

//이미지 미리보기
function readURL(file) {
    var reader = new FileReader();
    reader.onload = function(e) {
      document.getElementById("preview").src = e.target.result;
    };
    reader.readAsDataURL(file);
}