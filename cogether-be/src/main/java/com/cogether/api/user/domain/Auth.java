package com.cogether.api.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "auth")
@RequiredArgsConstructor
public class Auth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Auth(String refreshToken, User user) {
        this.refreshToken = refreshToken;
        this.user = user;
    }


    public void refreshUpdate(String refreshToken) {
        this.refreshToken = refreshToken;
    }


}
