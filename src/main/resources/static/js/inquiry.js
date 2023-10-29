$(function() {
    const token = $("meta[name='_csrf']").attr("content")
    const header = $("meta[name='_csrf_header']").attr("content");

    $("#btn-save").click(function() {
        let category = $(".select-table option:selected").val();

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
            isSecret : $("#inquiry-secret").is(":checked"),
            category : category
        }

        $.ajax({
            url : "/inquirys",
            type : "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data : JSON.stringify(data),
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function(res) {
            if (res.success) {
                alert("작성이 완료되었습니다.");
                location.href = "/inquirys";
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
            contentType: "application/json;charset=utf-8",
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
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
            contentType: "application/json;charset=utf-8",
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
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

function declaration(target, targetId) {
    let data = {
        title : "declaration",
        content : "declaration",
        isSecret : true,
        category : "declaration",
        target : target,
        targetId : targetId
    }

    $.ajax({
        url : "/inquirys",
        type : "POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data : JSON.stringify(data),
        beforeSend : function(xhr) {
           xhr.setRequestHeader(header, token);
        }
    }).done(function(res) {
        if (res.success) {
            alert("신고되었습니다.");
        } else {
            alert(res.msg);
        }
    }).fail(function(error) {
        alert(error.responseJSON.msg);
    })
}