package com.cogether.api.liveCoop.dto;

import com.cogether.api.user.dto.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDate;
import javax.persistence.*;

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
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "mem_num", nullable = false)
    private int memNum;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
    private LocalDate duration;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(name = "in_progress")
    private boolean inProgress;

}