package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class MessageArtical {
    //活动审核结果通知
    public String title;
    public String author;
    public String content;
    public String contentURL;
    public boolean allUsers;
    public boolean onlyFollowers;
    public String identifier;
    public String accountName;
}
