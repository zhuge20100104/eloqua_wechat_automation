package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class Filters {
    public String tag;
    public String gender;
    public String system;
    public String region;
    public String city;
    public String district;
    public String language;
}
