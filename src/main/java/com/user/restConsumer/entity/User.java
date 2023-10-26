package com.user.restConsumer.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @JsonProperty("user_id")
    private int user_id;
    @JsonProperty("user_name")
    private String user_name;
    @JsonProperty("age")
    private int age;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;



}
