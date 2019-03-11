package com.oracle.auto.testapp.web.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class DocHoverData {
    public String hoverDocName;
    public String hoverLastModifiedDate;
//    public String hoverDocAssignmentCompleted;
//    public String hoverDocAssignmentOpened;
}
