package com.cogether.api.chat.dto;

import com.cogether.api.liveCoop.dto.LiveCoop;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChatRoom")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;

    @Column
    private boolean type;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;
}