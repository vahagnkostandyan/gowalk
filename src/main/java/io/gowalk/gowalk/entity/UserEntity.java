package io.gowalk.gowalk.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "users")
public class UserEntity {
    @Id
    private Long id;

    @Column(value = "email")
    private String email;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;
}
