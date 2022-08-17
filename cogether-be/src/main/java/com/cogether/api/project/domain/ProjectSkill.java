package com.cogether.api.project.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProjectSkill")
public class ProjectSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Project_id")
    private Project project;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    public static ProjectSkill toEntity(Project project, String skillName) {
        return ProjectSkill.builder()
                .project(project)
                .skillName(skillName)
                .build();
    }
}
