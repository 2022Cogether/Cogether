package com.cogether.api.skill.dto;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Skill")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED",nullable = false)
    private int id ;

    @Column(name="name",length=50)
    private String name;

    @Column(name="url", length=300)
    private String url;
}
