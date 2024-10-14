package com.pokerogue.helper.item.service;


import com.pokerogue.helper.item.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> findItems() {
        return itemRepository.findAll().stream().map(ItemResponse::from).toList();

    }
}
