package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class QRCodeEmail {
    public String cloudContentName;
    public String emailName;
    public String emailSubject;
    public String QRCodeName;
    public String accountName;
    public String textContent;
    public String richMediaMessageTitle;
}
