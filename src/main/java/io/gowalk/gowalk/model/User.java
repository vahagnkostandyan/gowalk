package io.gowalk.gowalk.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String email;
    private List<Interest> interests;
}
