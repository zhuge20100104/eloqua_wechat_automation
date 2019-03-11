package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class WeChatFile {
    public String name;
    public String path;
}
