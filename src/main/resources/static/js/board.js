$(function() {
    const token = $("meta[name='_csrf']").attr("content")
    const header = $("meta[name='_csrf_header']").attr("content");

    $("#btn-save").click(function() {

        let target = $(".select-table option:selected").val();

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
            boardTable : target,
            cfrId : $("#cfrid").text()
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
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data : JSON.stringify(data),
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function(res) {
            if (res.success) {
                location.href = "/boards/" + data.boardTable;
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });

    $("#btn-update").click(function() {
        const boardId = $("#boardId").val();
        const boardTable = $("#boardTable").val();

        let data = {
            title : $("#title").val(),
            content : $("#content").val()
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
            url : "/boards/" + boardId,
            type : "PUT",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data : JSON.stringify(data),
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function(res) {
            if (res.success) {
                location.href = "/boards/" + boardTable + "/" + boardId;
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });

    $("#btn-delete").click(function() {
        const boardId = $("#boardId").val();
        const boardTable = $("#boardTable").val();

        $.ajax({
            url : "/boards/" + boardId,
            type : "DELETE",
            contentType: "application/json;charset=utf-8",
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function(res) {
            if (res.success) {
                alert("삭제가 완료되었습니다.");
                location.href = "/boards/" + boardTable;
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });

    $("#btn-board-blind").click(function() {
        const boardId = $("#boardId").val();
        const boardTable = $("#boardTable").val();

        $.ajax({
            url : "/boards/blind/" + boardId,
            type : "PUT",
            contentType: "application/json;charset=utf-8",
            beforeSend : function(xhr) {
               xhr.setRequestHeader(header, token);
            }
        }).done(function(res) {
            if (res.success) {
                alert("블라가 완료되었습니다.");
                location.href = "/boards/" + boardTable;
            } else {
                alert(res.msg);
            }
        }).fail(function(error) {
            alert(error.responseJSON.msg);
        })

    });


    $(".select-table").change(function() {
        const listSize = $("#list-size").val();
        if(listSize <= 0) {
            $(this).val("free").prop("selected", true);
            alert("cfr 데이터가 없습니다.");
            return false;
        }

        const boardTable = this.value;
        if(boardTable == "cfr") {
            openCfrList();
            $(".btn-cfr-list").css("visibility", "visible");
        } else {
            $(".btn-cfr-list").css("visibility", "hidden");
            $(".cfr-result").css("visibility", "hidden");
        }
    });

});

var thisBoardLikes = false;

function selectCfr(cfrId) {
    $("div").remove(".result");
    $("div").remove(".result-value");
    $("body").css("overflow", "auto");
    $(".b-cfrlist-outer").css("display", "none");
    $(".back-blur").css("display", "none");
    $(".cfr-result").css("visibility", "visible");
    $(".cfr-result").append("<div id='cfrid' class='result' style='display:none;'>" + cfrId + "</div>");
    $(".cfr-result").append("<div class='result-value'>" + $("#cfr-value" + cfrId).text() + "</div>");
    $(".cfr-result").append("<div class='result'>" + $("#cfr-confidence" + cfrId).text() + "</div>");
    $(".cfr-result").append("<div class='result'>" + $("#cfr-createDate" + cfrId).text() + "</div>");
}

function openCfrList() {
    $("body").css("overflow", "hidden");
    $(".b-cfrlist-outer").css("display", "block");
    $(".back-blur").css("display", "block");
}

function goodBtn(boardId, memberId) {
    if(thisBoardLikes == true) {
        alert("좋아요는 한번만 가능합니다.");
        return false;
    }
    thisBoardLikes = true;

    let data = {
        memberId: memberId,
        boardId: boardId
    };

    $.ajax({
        url : "/likes",
        type : "POST",
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        data : JSON.stringify(data),
        beforeSend : function(xhr) {
           xhr.setRequestHeader(header, token);
        }
    }).done(function(res) {
        if (res.success) {
            $("#goodCntOuter").load(location.href + " #goodCnt");
            alert(res.msg);
        } else {
            alert(res.msg);
        }
    }).fail(function(error) {
        alert(error.responseJSON.msg);
    })
    return false;
}

function authAlam() {
    alert("로그인이 필요합니다.");
}


