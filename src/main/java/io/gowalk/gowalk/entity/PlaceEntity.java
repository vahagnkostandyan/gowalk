package io.gowalk.gowalk.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(value = "places")
public class PlaceEntity {
    @Id
    private Long id;

    @Column(value = "name")
    private String name;

    @Column(value = "types")
    private List<String> types;

    @Column(value = "rating")
    private Float rating;

    @Column(value = "place_id")
    private String placeId;

    @CreatedDate
    @Column(value = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(value = "last_modified_date")
    private LocalDateTime lastModifiedDate;
}
