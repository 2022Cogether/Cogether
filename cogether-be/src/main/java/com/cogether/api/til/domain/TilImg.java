package com.cogether.api.til.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TilImg")
public class TilImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Til_id")
    private Til til;

    @Column(name = "url", nullable = false)
    private String url;

    public static TilImg toEntity(Til til, String url){
        return TilImg.builder()
                .til(til)
                .url(url)
                .build();
    }
}
