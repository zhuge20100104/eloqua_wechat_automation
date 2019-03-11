package com.oracle.auto.apps.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class EmailData {
    public String from;
    public String subject;
    public String content;
}
