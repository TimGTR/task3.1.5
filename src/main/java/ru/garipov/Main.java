package ru.garipov;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://94.198.50.185:7081/api/users";
        StringBuilder sb = new StringBuilder();


        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); //User[]  .... User[].class
        String sessionID = Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).get(0);
        String finalSessionID = sessionID.substring(0, sessionID.indexOf(";"));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Cookie", finalSessionID);

       // Map<String, String> jsonData = new HashMap<>();
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte)35);

//        jsonData.put("id", "3");
//        jsonData.put("name", "James");
//        jsonData.put("lastName", "Brown");
//        jsonData.put("age", "35");
        HttpEntity<User> request = new HttpEntity<>(user, httpHeaders);
        sb.append(restTemplate.postForObject(url, request, String.class));



        user.setName("James");
        user.setLastName("Brown");
//        Map<String, String> jsonData2 = new HashMap<>();
//        jsonData2.put("id", "3");
//        jsonData2.put("name", "Thomas");
//        jsonData2.put("lastName", "Shelby");
//        jsonData2.put("age", "35");
        HttpEntity<User> request2 = new HttpEntity<>(user, httpHeaders);
        ResponseEntity<String> response2 = restTemplate.exchange(url,HttpMethod.PUT, request2, String.class);
        sb.append(response2.toString().split(",")[1]);




        String urlDelete = url + "/3";
        ResponseEntity<String> response3 = restTemplate.exchange(urlDelete, HttpMethod.DELETE, request2, String.class);
        sb.append(response3.toString().split(",")[1]);


        System.out.println("Итоговый код задания " + sb);

    }
}