package com.cogether.api.liveCoop.domain;

import com.cogether.api.user.domain.User;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livecoop_id")
    private LiveCoop liveCoop;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "code", length = 20000)
    private String code;
}