package com.user.restConsumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.restConsumer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserConsumerController {

    @Autowired
    RestTemplate restTemplate;

    public String url = "http://localhost:8081/";

    @RequestMapping("/getAllUsers")
    public String getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String uriBuilder = url + "users";

        return restTemplate.exchange(uriBuilder, HttpMethod.GET, entity, String.class).getBody();

    }

    @RequestMapping("/addUser")
    public String saveNewUser(@RequestBody User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        String uriBuilder = url + "add";

        return restTemplate.exchange(uriBuilder, HttpMethod.POST, entity, String.class).getBody();
    }

    @RequestMapping("/user/{id}")
    public User findUserById(@PathVariable("id") int id) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();
        String uriBuilder = url + "/users/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        String userJson = restTemplate.exchange(uriBuilder, HttpMethod.GET, entity, String.class, params).getBody();
        return mapper.readValue(userJson, User.class);
    }

    @RequestMapping("/update/{id}")
    public String updateUserById(@RequestBody User user, @PathVariable("id") int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        String uriBuilder = url + "update/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        return restTemplate.exchange(uriBuilder, HttpMethod.PUT, entity, String.class, params).getBody();

    }

    @RequestMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String uriBuilder = url + "user/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        String userJson = null;
        try {
            userJson= restTemplate.exchange(uriBuilder, HttpMethod.DELETE, entity, String.class, params).getBody();

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            }
        }


        return new ResponseEntity<>(userJson,HttpStatus.OK);
    }


}
