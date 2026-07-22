package com.liverpool.liverpooldev.infrastructure.adapter.out.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.liverpool.liverpooldev.domain.model.Item;
import com.liverpool.liverpooldev.domain.port.out.ItemsClientPort;
import com.liverpool.liverpooldev.infrastructure.adapter.out.client.dto.ItemApiDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemsApiAdapter implements ItemsClientPort {

    private final RestClient restClient;

    @Value("${external.api.items-path:/items}")
    private String itemsPath;

    @Override
    public List<Item> fetchAll() {
        List<Item> result = new ArrayList<>();
        log.info("Fetching items from liverpool external API");
        try {
            List<ItemApiDto> dtos = restClient
                    .get()
                    .uri(itemsPath)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<ItemApiDto>>() {
                    });
            result = dtos.stream()
                .map(this::toDomain)
                .peek(x -> System.out.println(x))
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch items from external API: {}", e.getMessage());
        }
        
        return result;
    }

    private Item toDomain(final ItemApiDto dto) {
        return Item.builder()
                .itemId(dto.getItemId())
                .skuId(dto.getSkuId())
                .quantity(dto.getQuantity())
                .displayName(dto.getDisplayName())
                .deliveryStatus(dto.getDeliveryStatus())
                .build();
    }

}
