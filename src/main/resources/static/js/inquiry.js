$(function() {
    $("#btn-save").click(function() {
        let target = $(".select-table option:selected").val();

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
            isSecret : $("#inquiry-secret").is(":checked"),
            target : target
        }

        $.ajax({
            url : "/inquirys",
            type : "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data : JSON.stringify(data)
        }).done(function(res) {
            if (res.success) {
                alert("작성이 완료되었습니다.");
                location.href = "/";
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });

    $("#btn-inquiry-complete").click(function() {
        const inquiryId = $("#inquiryId").val();

        $.ajax({
            url : "/inquirys/complete/" + inquiryId,
            type : "PUT",
            contentType: "application/json;charset=utf-8"
        }).done(function(res) {
            if (res.success) {
                alert("완료되었습니다.");
                location.href = "/inquirys";
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });

    $("#btn-delete").click(function() {
        $.ajax({
            url : "/inquirys/" + $("#inquiryId").val(),
            type : "DELETE",
            contentType: "application/json;charset=utf-8"
        }).done(function(res) {
            if (res.success) {
                alert("삭제가 완료되었습니다.");
                location.href = "/inquirys";
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });

});