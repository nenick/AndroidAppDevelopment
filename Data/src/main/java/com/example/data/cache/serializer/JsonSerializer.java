/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.data.cache.serializer;

import com.example.data.entity.UserEntity;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

/**
 * Class user as Serializer/Deserializer for user entities.
 */
@EBean
public class JsonSerializer {

  private final Gson gson = new Gson();

  /**
   * Serialize an object to Json.
   *
   * @param userEntity {@link UserEntity} to serialize.
   */
  public String serialize(UserEntity userEntity) {
    String jsonString = gson.toJson(userEntity, UserEntity.class);
    return jsonString;
  }

  /**
   * Deserialize a json representation of an object.
   *
   * @param jsonString A json string to deserialize.
   * @return {@link UserEntity}
   */
  public UserEntity deserialize(String jsonString) {
    UserEntity userEntity = gson.fromJson(jsonString, UserEntity.class);
    return userEntity;
  }
}
