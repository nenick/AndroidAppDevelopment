package com.example.data.network;

import com.example.data.ApplicationTestCase;
import com.example.data.network.json.User;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.fest.assertions.api.Assertions.assertThat;

public class TypicodeUsersTest extends ApplicationTestCase {

    TypicodeUsers client = new TypicodeUsers_();

    @Test
    public void testGetUsers() throws Exception {
        ResponseEntity<User[]> users = client.getUsers();
        User[] body = users.getBody();
        assertThat(body).isNotNull();
        assertThat(body).hasSize(10);
    }

    @Test
    public void testGetUser() throws Exception {
        ResponseEntity<User> user = client.getUser(1);
        User body = user.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo("Leanne Graham");
    }
}