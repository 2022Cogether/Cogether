package com.cogether.api.liveComp.domain;

import com.cogether.api.user.dto.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LiveComp")
public class LiveComp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
    private LocalTime totalTime;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;


    public static LiveComp toEntity(User user, LocalTime totalTime) {
        return LiveComp.builder()
                .user(user)
                .totalTime(totalTime)
                .build();
    }
}