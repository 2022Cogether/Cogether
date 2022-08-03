package com.cogether.api.liveCoop.domain;

import com.cogether.api.user.domain.User;
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
@Table(name = "LiveCoop")
public class LiveCoop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "mem_num", nullable = false)
    private int memNum;

    @Column(name = "now_mem_num", nullable = false)
    private int nowMemNum;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 2000, nullable = false)
    private String content;

    @Column(name = "in_progress", nullable = false)
    private boolean inProgress;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

}