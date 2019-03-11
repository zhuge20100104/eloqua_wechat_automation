package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class Message {
    public String account;
    public String name;
    public String title;
    public String author;
    public String keyword;
    public String content;
    public String url;
    public String openedURL;
}
