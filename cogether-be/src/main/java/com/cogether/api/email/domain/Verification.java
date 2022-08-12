package com.cogether.api.email.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="Verification")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Verification {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name="email")
    private String email;

    @Column(name="code")
    private String code;
}
