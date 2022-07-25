package com.cogether.api.user.dto;

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
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="skill_id",length=50)
    private String skillId;

}
