package com.pokerogue.helper.item.service;


import com.pokerogue.helper.global.exception.ErrorMessage;
import com.pokerogue.helper.global.exception.GlobalCustomException;
import com.pokerogue.helper.item.dto.ItemResponse;
import com.pokerogue.helper.item.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResponse> findAll() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::from)
                .toList();
    }

    public ItemResponse findById(String id) {
        return itemRepository.findById(id)
                .map(ItemResponse::from)
                .orElseThrow(() -> new GlobalCustomException(ErrorMessage.ITEM_NOT_FOUND));
    }
}
