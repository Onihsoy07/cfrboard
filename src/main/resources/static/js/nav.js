$(function() {
    $(window).resize(function() {
        if($(window).width() < 575) {
            $(".navbar-toggler").addClass("toggle");
            $(".dropbtn").attr("mov", true);
            $(".dropbtn").onclick = null;
        } else {
            $(".navbar-toggler").removeClass("toggle");
            $(".dropbtn").attr("mov", false);
        }

    });
    $(document).ready(function() {
        if($(window).width() < 575) {
            $(".navbar-toggler").addClass("toggle");
            $(".dropbtn").attr("mov", true);
            $(".dropbtn").onclick = null;
        } else {
            $(".navbar-toggler").removeClass("toggle");
            $(".dropbtn").attr("mov", false);
        }
    });

    $(".navbar-toggler").click(function() {
        closeDropDown();
        if($(".nav-wrap").attr("open") == "open") {
            $(".nav-wrap").css("display", "none");
            $(".nav-wrap").attr("open", false);
        } else {
            $(".nav-wrap").css("display", "contents");
            $(".nav-wrap").attr("open", true);
        }
    });

    $(".btn-board").click(function() {
        if($(".btn-board").attr("mov") == "true") {
            dropDownFunc(".board-toggle", 148);
        }
    });

    $(".btn-member").click(function() {
        if($(".btn-member").attr("mov") == "true") {
            dropDownFunc(".member-toggle", 196);
        }
    });

});

function movPage(url) {
    if($(".dropbtn").attr("mov") == "true") {
        return false;
    } else {
        location.href = url;
    }
}

function closeDropDown() {
    $(".dropdown").css("height", "52");
    $(".dropdown").attr("open", false);
}

function dropDownFunc(className, openHeight) {
    if($(className).attr("open") == "open") {
        $(className).css("height", "52");
        $(className).attr("open", false);
    } else {
        closeDropDown();

        $(className).css("height", openHeight);
        $(className).attr("open", true);
    }
}