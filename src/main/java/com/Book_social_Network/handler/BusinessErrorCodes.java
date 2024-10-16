package com.Book_social_Network.handler;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes  {

    NO_CODE(0, NOT_IMPLEMENTED, "NO CODE"),

    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST,"CURRENT PASSWORD is incorrect"),

    NEW_PASSWORD_DOES_NOT_MATCH(301, BAD_REQUEST,"New password does not match"),

    ACCOUNT_DISABLED(303, BAD_REQUEST,"User account is disabled"),

    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),

    BAD_CREDENTIALS(304, BAD_REQUEST,"Login and password is incorrect"),


    ;
    private final int code ;
    private final String description;
    private final HttpStatusCode httpStatusCode;
    BusinessErrorCodes(int code, HttpStatusCode httpStatusCode,String description ) {
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }


}
