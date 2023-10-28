$(function() {
    const token = $("meta[name='_csrf']").attr("content")
    const header = $("meta[name='_csrf_header']").attr("content");
});

function writeReply(boardId, replyId, depth) {
        let data = {
            comment : $("#comment").val(),
            boardId : boardId,
            replyId : replyId,
            depth : depth
        }

        if(data.comment.length < 3) {
            alert("댓글을 3자 이상 써주세요.")
            return false;
        }

        $.ajax({
            url : "/replys",
            type : "POST",
            contentType: "application/json;charset=utf-8",
            dataType:"json",
            data : JSON.stringify(data),
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function (res) {
            if(res.success) {
                alert("댓글 쓰기가 완료되었습니다.");
                $(".reply-wrapper").load(location.href + " .comment-outer, .reply-outer");
            } else {
                alert("댓글 쓰기가 실패되었습니다.");
            }
        }).fail(function (error){
            console.log(error);
            alert("댓글 쓰기가 실패되었습니다.");
        });
}

function replyOpen(boardId, replyId, depth) {
    $("div").remove(".reReply-inner");
    $(".reply-inner").css("display", "none");

    let username = $("#p_username").val();

    let html = "";

    html += "<div class='reReply-inner'>";
    html += "<div class='reply-info'>";
    html += "<div class='member-info'>" + username +  "</div>";
    html += "<div class='btn-close' onclick='replyClose()'></div>";
    html += "</div>";
    html += "<div class='reply-textarea-wrapper'>";
    html += "<textarea class='reply-textarea' id='comment' maxlength='800' required='required' style='height: 99.5px;'></textarea>";
    html += "<div class='reply-button-wrapper'>";
    html += "<button class='reply-button' type='button' onclick='writeReply(" + boardId + ", " + replyId + ", " + depth + ")'>작성</button>";
    html += "</div>";
    html += "</div>";
    html += "</div>";

    $("#c_" + replyId).after(html);

}

function replyDelete(replyId) {
    $.ajax({
        url : "/replys/" + replyId,
        type : "DELETE",
        contentType: "application/json;charset=utf-8",
        beforeSend : function(xhr) {
           xhr.setRequestHeader(header, token);
        }
    }).done(function(res){
        if(res.success) {
            alert("댓글 삭제가 완료되었습니다.");
            $(".reply-wrapper").load(location.href + " .comment-outer, .reply-outer");
        } else {
            alert(res.msg);
        }
    }).fail(function(error){
        alert(error.responseJSON.msg);
    });
}

function replyBlind(replyId) {
    $.ajax({
        url : "/replys/blind/" + replyId,
        type : "PUT",
        contentType: "application/json;charset=utf-8",
        beforeSend : function(xhr) {
           xhr.setRequestHeader(header, token);
        }
    }).done(function(res){
        if(res.success) {
            alert("블라가 완료되었습니다.");
            $(".reply-wrapper").load(location.href + " .comment-outer, .reply-outer");
        } else {
            alert(res.msg);
        }
    }).fail(function(error){
        alert(error.responseJSON.msg);
    });
}

function replyUpdateOpen(replyId) {
    $("div").remove(".reReply-inner");
    $(".reply-inner").css("display", "none");

    let username = $("#p_username").val();
    let comment = $("#message_" + replyId).text();

    let html = "";

    html += "<div class='reReply-inner'>";
    html += "<div class='reply-info'>";
    html += "<div class='member-info'>" + username +  "</div>";
    html += "<div class='btn-close' onclick='replyClose()'></div>";
    html += "</div>";
    html += "<div class='reply-textarea-wrapper'>";
    html += "<textarea class='reply-textarea' id='update-comment' maxlength='800' required='required' style='height: 99.5px;'>" + comment + "</textarea>";
    html += "<div class='reply-button-wrapper'>";
    html += "<button class='reply-button' type='button' onclick='replyUpdate(" + replyId + ")'>수정</button>";
    html += "</div>";
    html += "</div>";
    html += "</div>";

    $("#c_" + replyId).after(html);
}

function replyUpdate(replyId) {
        let data = {
            comment : $("#update-comment").val()
        }

        if(data.comment.length < 3) {
            alert("댓글을 3자 이상 써주세요.")
            return false;
        }

        $.ajax({
            url : "/replys/" + replyId,
            type : "PUT",
            contentType: "application/json;charset=utf-8",
            dataType:"json",
            data : JSON.stringify(data),
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function(res){
            if(res.success) {
                alert("댓글 수정이 완료되었습니다.");
                $(".reply-wrapper").load(location.href + " .comment-outer, .reply-outer");
            } else {
                alert(res.msg);
            }
        }).fail(function(error){
            alert(error.responseJSON.msg);
        });
}

function replyClose() {
    $(".reply-inner").css("display", "block");
    $("div").remove(".reReply-inner");
}
