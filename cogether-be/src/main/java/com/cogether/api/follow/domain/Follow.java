package com.cogether.api.follow.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Follow")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "to_id")
    private User user;  // 내아이디

    @Column(name = "to_email", nullable = false)
    private String toEmail;

    @Column(name = "from_id", nullable = false)
    private int fromId;   // 팔로잉 등록대상

    @Column(name = "from_email", nullable = false)
    private String fromEmail;

}
