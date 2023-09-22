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
            data : JSON.stringify(data)
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
        contentType: "application/json;charset=utf-8"
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