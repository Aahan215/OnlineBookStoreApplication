package com.aahan.onlineBookStore.pojo;

import com.aahan.onlineBookStore.model.EnumData;

import javax.persistence.*;

@Entity
public class UserPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumData.Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EnumData.Role getRole() {
        return role;
    }

    public void setRole(EnumData.Role role) {
        this.role = role;
    }
}
