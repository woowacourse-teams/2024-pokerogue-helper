package com.pokerogue.helper.item.repository;

import com.pokerogue.helper.item.data.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item,String> {
}
