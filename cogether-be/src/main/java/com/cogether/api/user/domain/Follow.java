package com.cogether.api.user.domain;

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
    @JoinColumn(name="from_id")
    private User user;  // 내아이디.. . .ㅍ... . ...ㅍ. . . .

    @Column(name="to_id",nullable = false)
    private int toId;   // 내가 팔로잉 등록함 ㅋ.ㅋ.ㅋ.ㅋ.ㅋ.ㅋ.

}
