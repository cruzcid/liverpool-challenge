package com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.liverpool.liverpool_dev.domain.model.Order;
import com.liverpool.liverpool_dev.domain.port.out.OrderCachePort;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.document.OrderDocument;
import com.liverpool.liverpool_dev.infrastructure.adapter.out.persistance.repository.OrderMongoRepository;
import com.liverpool.liverpool_dev.shared.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderCacheMongoAdapter implements OrderCachePort{
    private final OrderMongoRepository orderRepository;

    @Override
    public void saveAll(List<Order> orders) {
        List<OrderDocument> orderDocuments = orders
            .stream()
            .map(o -> {
                OrderDocument od = OrderMapper.toDocument(o);
                od.setSyncedAt(LocalDateTime.now());
                return od; })
            .collect(Collectors.toList());

        orderRepository.saveAll(orderDocuments);
    }
    
}
