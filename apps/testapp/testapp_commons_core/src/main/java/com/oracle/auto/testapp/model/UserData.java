package com.oracle.auto.testapp.model;

import com.oracle.auto.web.annotations.JsonConvertable;
import com.oracle.auto.web.annotations.JsonListConvertable;

@JsonConvertable
@JsonListConvertable
public class UserData {
    public String firstName;
    public String lastName;
    public String password;
    public String email;
    public boolean isOwner;
    public String initials;
    public String role;
}
