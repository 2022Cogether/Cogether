package com.cogether.api.user.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name="user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED",nullable = false)
    private int id;

    @Column(name="email",length = 50,nullable = false)
    private String email;

    @Column(name="password",nullable = false)
    private String password;

    @Column(name="nickname",length = 15)
    private String nickname;

    @Column(name="exp",nullable = false)
    private int exp;

    @Column(name="created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name="admin")
    private boolean admin;

    @Column(name="comp")
    private boolean comp;

    @Column(name="verified")
    private boolean verified;

    @Column(name="git_url", length=100)
    private String gitUrl;

    @Column(name="velog_url", length=100)
    private String velogUrl;

    @Column(name="tistory_url", length=100)
    private String tistoryUrl;

    @Column(name="notion_url", length =150)
    private String notionUrl;

    @Column(name="etc_url" ,length=200)
    private String etcUrl;

    @Column(name="intro", length=100)
    private String intro;

    @Column(name="img_url", length =300)
    private String imgUrl;

    @Column(name="resign")
    private boolean resign;




}
