package io.gowalk.gowalk.entity;

import io.gowalk.gowalk.enumerations.Mode;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(value = "request_histories")
public class RequestHistoryEntity {
    @Id
    private Long id;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "latitude")
    private double latitude;

    @Column(value = "longitude")
    private double longitude;

    @Column(value = "mode")
    private Mode mode;

    @Column(value = "place_id")
    private Long placeId;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;

}
