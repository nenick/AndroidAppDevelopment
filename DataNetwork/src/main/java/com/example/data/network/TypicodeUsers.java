package com.example.data.network;

import com.example.data.network.json.User;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

@Rest(rootUrl = "http://jsonplaceholder.typicode.com",
        converters = {MappingJacksonHttpMessageConverter.class, StringHttpMessageConverter.class})
public interface TypicodeUsers {

    @Get("/users")
    ResponseEntity<User[]> getUsers();

    @Get("/users/{userId}")
    ResponseEntity<User> getUser(int userId);
}
