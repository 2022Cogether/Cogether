package com.cogether.api.liveComp.dto;

import com.cogether.api.user.dto.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
    private LocalDate totalTime;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

}