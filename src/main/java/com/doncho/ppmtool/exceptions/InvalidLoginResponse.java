package com.doncho.ppmtool.exceptions;

public class InvalidLoginResponse
{
    private String userName, password;

    public InvalidLoginResponse()
    {
        this.userName = "Invalid Username";
        this.password = "Invalid Password";
    }

    public String getUsername()
    {
        return userName;
    }

    public void setUsername(String username)
    {
        this.userName = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
