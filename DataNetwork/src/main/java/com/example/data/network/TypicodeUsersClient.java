package com.example.data.network;

import com.example.data.network.json.UserJson;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

@Rest(rootUrl = "http://jsonplaceholder.typicode.com",
        converters = {MappingJacksonHttpMessageConverter.class, StringHttpMessageConverter.class})
interface TypicodeUsersClient {

    @Get("/users")
    ResponseEntity<UserJson[]> getUsers();

    @Get("/users/{userId}")
    ResponseEntity<UserJson> getUser(int userId);
}
