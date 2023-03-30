package io.gowalk.gowalk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "interests")
public class InterestEntity {
    @Id
    private Long id;

    @Column(value = "interest")
    private String interest;
}
