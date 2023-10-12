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
        if($(".nav-wrap").attr("open") == "open") {
            $(".nav-wrap").css("display", "none");
            $(".nav-wrap").attr("open", false);
        } else {
            $(".nav-wrap").css("display", "contents");
            $(".nav-wrap").attr("open", true);
        }
    });

    $(".dropbtn").click(function() {
        if($(".dropbtn").attr("mov") == "true") {
            alert("안녕");
        } else {
            return false;
        }
    });



});