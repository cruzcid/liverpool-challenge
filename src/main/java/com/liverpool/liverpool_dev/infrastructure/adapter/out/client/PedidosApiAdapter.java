package com.liverpool.liverpool_dev.infrastructure.adapter.out.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.liverpool.liverpool_dev.domain.model.Order;
import com.liverpool.liverpool_dev.domain.port.out.PedidosClientPort;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.client.dto.PedidoApiDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PedidosApiAdapter implements PedidosClientPort {
    private final RestClient restClient;

    @Value("${external.api.pedidos-path:/pedidos}")
    private String pedidosPath;

    @Override
    public List<Order> fetchAll() {
        List<Order> result = new ArrayList<>();
        log.info("Fetching pedidos from liverpool external API");

        try {
            List<PedidoApiDto> pedidosDto = restClient
                    .get()
                    .uri(pedidosPath)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<PedidoApiDto>>() {
                    });

            result = pedidosDto
                    .stream()
                    .map(this::toDomain)
                    .peek(p -> System.out.println(p.toString()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch Pedidos from external API: {}", e.getMessage());
        }

        return result;
    }

    private Order toDomain(PedidoApiDto dto) {
        return Order.builder()
                .orderRef(dto.getOrderRef())
                .userId(dto.getUserId())
                .canal(dto.getCanal())
                .orderStatus(dto.getOrderStatus())
                .marketPlace(dto.isMarketPlace())
                .giftRegistry(dto.isGiftRegistry())
                .itemIds(dto.getItems())
                .storeName(dto.getStoreName())
                .build();
    }

}
