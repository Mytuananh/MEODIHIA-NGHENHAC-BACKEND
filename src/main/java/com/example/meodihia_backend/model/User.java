package com.example.meodihia_backend.model;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @NotBlank
    @Size(min = 6, max = 100)
    private String re_password;

    @Size(min = 3, max = 50)
    private String phoneNumber;

    @Size(min = 3, max = 60)
    private String fullName;

    @Size(max = 50)
    @Email
    private String email;

    private String address;

    @Lob
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<>();

    public User(
                  @NotBlank @Size(min = 3, max = 50)String username,
                  @NotBlank @Size(min = 3, max = 50) String phoneNumber,
                  @NotBlank @Size(min = 6, max = 100)String encode,
                  String avatar) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = encode;
        this.re_password = this.password;
        this.avatar =avatar;
    }

    public User() {
    }
}
