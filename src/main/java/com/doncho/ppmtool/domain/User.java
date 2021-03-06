package com.doncho.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be a valid email")
    @NotBlank(message = "userName is required")
    @Column(unique = true)
    private String userName;

    @NotBlank(message = "Please enter your full name")
    private String fullName;

    @NotBlank(message = "Password field is requored!")
    private String password;

    @Transient
    //@JsonIgnore - wouldn't work here, because passwords must match :)
    private String confirmPassword;

    private Date created_At;

    private Date updated_At;

    // OneToMany with project
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();


    public User()
    {
        // Default constructor in entity class
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreated_At()
    {
        return created_At;
    }

    public void setCreated_At(Date created_At)
    {
        this.created_At = created_At;
    }

    public Date getUpdated_At()
    {
        return updated_At;
    }

    public void setUpdated_At(Date updated_At)
    {
        this.updated_At = updated_At;
    }

    @PrePersist
    protected void onCreate()
    {
        this.created_At = new Date();
    }


    @PreUpdate
    protected void onUpdate()
    {
        this.updated_At = new Date();
    }

    /** UserDetails implemented methods*/

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    @JsonIgnore
    public String getUsername()
    {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled()
    {
        return true;
    }

    public List<Project> getProjects()
    {
        return projects;
    }

    public void setProjects(List<Project> projects)
    {
        this.projects = projects;
    }
}
