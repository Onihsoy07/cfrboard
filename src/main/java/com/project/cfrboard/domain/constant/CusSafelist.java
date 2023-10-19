package com.project.cfrboard.domain.constant;

import org.jsoup.safety.Safelist;

public class CusSafelist {

    public static Safelist boardXss() {
        return Safelist.relaxed()
                .addAttributes("image");
    }

    public static Safelist replyXss() {
        return Safelist.relaxed()
                .addAttributes("image");
    }
}
