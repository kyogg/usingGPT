package com.example.usingGPT.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column(name = "userID")
    private String userID;

    @Column(name = "password")
    private String password;

    @Column(name = "nickName")
    private String nickName;
}
