package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 5/6/16.
 */
public class UserPassword {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

    public void setOldPassword (String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPassword () {
        return oldPassword;
    }

    public void setNewPassword (String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword () {
        return newPassword;
    }
}
