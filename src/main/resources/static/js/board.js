$(function() {
    $("#btn-save").click(function() {

        let target = $(".select-table option:selected").val();
        var cfrId = null;

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
            boardTable : target,
            cfrId : cfrId
        }

        if(data.title.length < 5) {
            alert("제목을 5글자 이상 써주세요.");
            return false;
        }

        if(data.content.length < 16) {
            alert("내용을 10글자 이상 써주세요.");
            return false;
        }

        $.ajax({
            url : "/boards",
            type : "POST",
            data : data
        }).done(function(res) {
            if (res.success) {
                location.href = "/boards?bt=" + data.boardTable;
            } else {
                alert(res.msg);
            }
        }).fail(function() {
            alert(error.responseJSON.msg);
        })

    });

});




