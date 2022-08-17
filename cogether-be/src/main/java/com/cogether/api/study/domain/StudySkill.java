package com.cogether.api.study.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "StudySkill")
public class StudySkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Study_id")
    private Study study;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    public static StudySkill toEntity(Study study, String skillName) {
        return StudySkill.builder()
                .study(study)
                .skillName(skillName)
                .build();
    }

}
