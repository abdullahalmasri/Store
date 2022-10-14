package com.store.Application.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserId extends UUIDBased{
    private static final long serialVersionUID = 1L;

    @JsonCreator
    public UserId(@JsonProperty("id")UUID id) {
        super(id);
    }
    public static UserId fromString(String id){
        return new UserId(UUID.fromString(id));
    }
}
