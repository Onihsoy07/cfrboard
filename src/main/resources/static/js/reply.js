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
//                $('#replyWin').load(location.href + ' #replyBox');

            } else {
                alert("댓글 쓰기가 실패되었습니다.");
            }
        }).fail(function (error){
            console.log(error);
            alert("댓글 쓰기가 실패되었습니다.");
        });
}