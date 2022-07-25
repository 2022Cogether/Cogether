package com.cogether.api.liveCoop.dto;

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
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livecoop_id")
    private LiveCoop liveCoop;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String code;
}