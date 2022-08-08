package com.cogether.api.follow.domain;

import com.cogether.api.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Follow")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED",nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="to_id")
    private User user;  // 내아이디.. . .ㅍ... . ...ㅍ. . . .

    @Column(name="to_email",nullable = false)
    private String toEmail;

    @Column(name="from_id",nullable = false)
    private int fromId;   // 내가 팔로잉 등록함 ㅋ.ㅋ.ㅋ.ㅋ.ㅋ.ㅋ.

    @Column(name="from_email",nullable = false)
    private String fromEmail;

}
