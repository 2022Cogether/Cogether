package com.cogether.api.user.dto;

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

    @Column(name="nickname",length = 15,nullable = false)
    private String nickname;

    @Column(name="exp",nullable = false)
    private int exp;

    @Column(name="created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name="admin")
    private boolean admin;

    @Column(name="verified")
    private boolean verified;

    @Column(name="refresh_token" ,length=500)
    private String refreshToken;

    @Column(name="git_url")
    private String gitUrl;

    @Column(name="velog_url")
    private String velogUrl;

    @Column(name="tistory_url")
    private String tistoryUrl;

    @Column(name="notion_url")
    private String notionUrl;

    @Column(name="etc_url")
    private String etcUrl;

    @Column(name="intro")
    private String intro;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="resign")
    private boolean resign;
}
