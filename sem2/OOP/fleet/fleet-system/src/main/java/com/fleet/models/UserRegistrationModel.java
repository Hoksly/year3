package com.fleet.models;

import lombok.*;
@Data
public class UserRegistrationModel {
    public String username;
    public String password;
    public String email;
    public String phoneNumber;
    public String confirmPassword;
}
