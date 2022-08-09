package com.cogether.api.rank.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ranking")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "til_cnt")
    private int tilCnt;

    @Column(name = "week")
    private int week;

    @Column(name = "month")
    private int month;

    @Column(name = "total")
    private int total;
}
