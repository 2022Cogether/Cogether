package com.cogether.api.user.dto;

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
    private User user;

    @Column(name="to_id",nullable = false)
    private int toId;

}
