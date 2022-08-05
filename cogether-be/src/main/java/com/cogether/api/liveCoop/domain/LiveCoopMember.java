package com.cogether.api.liveCoop.domain;

import com.cogether.api.hunting.domain.Hunting;
import com.cogether.api.hunting.domain.HuntingRequest;
import com.cogether.api.user.dto.User;
import lombok.*;

import javax.persistence.*;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LiveCoopMember")
public class LiveCoopMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livecoop_id")
    private LiveCoop liveCoop;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "code", length = 15000)
    private String code;

    public static LiveCoopMember toEntity(User user, LiveCoop liveCoop) {
        return LiveCoopMember.builder()
                .user(user)
                .liveCoop(liveCoop)
                .build();
    }
}