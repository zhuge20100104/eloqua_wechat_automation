package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class ActionResultNotificatoinTemplate {
    //活动审核结果通知
    public String templateField;
    public String templateMsgName;
    public String title;
    public String bottomTitle;
    public String link;
    public String identifier;
    public String appID;
    public String pagePath;
}
