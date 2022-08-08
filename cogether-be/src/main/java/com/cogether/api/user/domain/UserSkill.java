package com.cogether.api.user.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="UserSkill")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED",nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id") //유저식별자
    private User user;

    @Column(name="skill_id",length=50)  //기술스택식별자
    private String skillId;

}
