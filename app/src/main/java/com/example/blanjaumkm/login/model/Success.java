package com.example.blanjaumkm.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Success {

    @SerializedName("success")
    @Expose
    private Token success;

    public void setSuccess(Token success) {
        this.success = success;
    }

    public Token getSuccess() {
        return success;
    }
}
