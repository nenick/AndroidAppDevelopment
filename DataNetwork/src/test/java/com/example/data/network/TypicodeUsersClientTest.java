package com.example.data.network;

import com.example.data.ApplicationTestCase;
import com.example.data.network.json.UserJson;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.fest.assertions.api.Assertions.assertThat;

public class TypicodeUsersClientTest extends ApplicationTestCase {

    TypicodeUsersClient client = new TypicodeUsersClient_();

    @Test
    public void testGetUsers() throws Exception {
        ResponseEntity<UserJson[]> users = client.getUsers();
        UserJson[] body = users.getBody();
        assertThat(body).isNotNull();
        assertThat(body).hasSize(10);
    }

    @Test
    public void testGetUser() throws Exception {
        ResponseEntity<UserJson> user = client.getUser(1);
        UserJson body = user.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getName()).isEqualTo("Leanne Graham");
    }
}