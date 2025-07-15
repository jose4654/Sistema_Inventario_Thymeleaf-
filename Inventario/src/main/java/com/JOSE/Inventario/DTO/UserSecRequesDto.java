package com.JOSE.Inventario.DTO;

import java.util.List;

public class UserSecRequesDto {
    private String userName;
    private String password;
    private boolean enable;
    private List<Long> rolIds;  // Lista de IDs de roles

    // Getters y Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<Long> getRolIds() {
        return rolIds;
    }

    public void setRolIds(List<Long> rolIds) {
        this.rolIds = rolIds;
    }
}
