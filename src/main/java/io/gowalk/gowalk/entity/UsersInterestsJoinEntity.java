package io.gowalk.gowalk.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "users_interests_table")
public class UsersInterestsJoinEntity {
    @Id
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "interest_id")
    private Long interestId;
}
