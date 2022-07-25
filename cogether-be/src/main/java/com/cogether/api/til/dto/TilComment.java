package com.cogether.api.til.dto;

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
@Table(name = "TilComment")
public class TilComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Til_id")
    private Til til;

    @Column(name = "content")
    private String content;

    @Column(name ="user_id")
    private int userId;

    @Column (name = "created_at", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

}
