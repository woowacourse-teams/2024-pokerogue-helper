package com.pokerogue.helper.item.service;

import com.pokerogue.helper.item.data.Item;

public record ItemResponse(String id, String imageId, String koName, String description) {

    public static ItemResponse from(Item item) {
        return new ItemResponse(item.getId(), item.getImageId(), item.getKoName(), item.getDescription());
    }
}
