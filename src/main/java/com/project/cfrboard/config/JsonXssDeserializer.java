package com.project.cfrboard.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class JsonXssDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String xssResult;
        if ("content".equals(p.getCurrentName())) {
            xssResult = xssContent(p.getText());
        } else if ("comment".equals(p.getCurrentName())) {
            xssResult = xssComment(p.getText());
        } else {
            xssResult = xssBasic(p.getText());
        }

        return xssResult;
    }

    private String xssContent(String text) {
        return Jsoup.clean(text, cusContextSafelist());
    }

    private String xssComment(String text) {
        return Jsoup.clean(text, cusCommentSafelist());
    }

    private String xssBasic(String text) {
        return Jsoup.clean(text, Safelist.none());
    }

    private Safelist cusContextSafelist() {
        return Safelist.relaxed()
                .addProtocols("img", "src", "data")
                .addTags("font", "tr", "td")
                .addAttributes("font", "style", "color")
                .addAttributes("table", "class");
    }

    private Safelist cusCommentSafelist() {
        return Safelist.relaxed()
                .addProtocols("img", "src", "data");
    }

}
