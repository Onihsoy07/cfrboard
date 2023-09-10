$(function() {
    $("#btn-save").click(function() {

        let target = $(".select-table option:selected").val();

        alert(selectCfrId);

        let data = {
            title : $("#title").val(),
            content : $("#content").val(),
            boardTable : target,
            cfrId : selectCfrId
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
            data : JSON.stringify(data)
        }).done(function(res) {
            if (res.success) {
                location.href = "/boards?bt=" + data.boardTable;
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

var selectCfrId = null;

function selectCfr(cfrId) {
    selectCfrId = cfrId;
    $("div").remove(".result");
    $("body").css("overflow", "auto");
    $(".b-cfrlist-outer").css("display", "none");
    $(".back-blur").css("display", "none");
    $(".cfr-result").css("visibility", "visible");
    $(".cfr-result").append("<div class='result'>" + $("#cfr-value" + cfrId).text() + "</div>");
    $(".cfr-result").append("<div class='result'>" + $("#cfr-confidence" + cfrId).text() + "</div>");
    $(".cfr-result").append("<div class='result'>" + $("#cfr-createDate" + cfrId).text() + "</div>");
}

function openCfrList() {
    $("body").css("overflow", "hidden");
    $(".b-cfrlist-outer").css("display", "block");
    $(".back-blur").css("display", "block");
}



