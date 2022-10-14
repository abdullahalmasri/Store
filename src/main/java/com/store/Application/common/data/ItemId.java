package com.store.Application.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ItemId extends UUIDBased {
    @JsonCreator
    public ItemId(@JsonProperty("id") UUID id){
        super(id);
    }
    public static ItemId fromString(String itemId){
        return new ItemId(UUID.fromString(itemId));
    }
}
