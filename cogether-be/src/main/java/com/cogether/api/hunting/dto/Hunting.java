package com.cogether.api.hunting.dto;

import com.cogether.api.user.dto.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hunting")
public class Hunting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    public static Hunting toEntity(User user, HuntingRequest.Create request) {
        return Hunting.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

}