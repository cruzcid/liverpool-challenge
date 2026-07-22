package com.liverpool.liverpooldev.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.liverpool.liverpooldev.domain.exception.CustomerNotFoundException;
import com.liverpool.liverpooldev.domain.model.Customer;
import com.liverpool.liverpooldev.domain.model.Order;
import com.liverpool.liverpooldev.domain.port.out.CustomerRepositoryPort;
import com.liverpool.liverpooldev.domain.port.out.ItemCachePort;
import com.liverpool.liverpooldev.domain.port.out.OrderCachePort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Unit Tests")
public class CustomerServiceTest {
  
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;

    @Mock
    private OrderCachePort orderCachePort;

    @Mock
    private ItemCachePort itemCachePort;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = Customer.builder()
                .userId("75c97531-abf5-4524-8107-90aa48d08efc")
                .firstName("Juan")
                .lastNamePaternal("García")
                .lastNameMaternal("López")
                .email("juan.garcia@test.com")
                .build();
    }

    @Test
    @DisplayName("create() should generate a userId when none is provided")
    void create_shouldGenerateUserId_whenNotProvided() {
        Customer noId = Customer.builder()
                .firstName("María")
                .lastNamePaternal("Pérez")
                .email("maria@test.com")
                .build();
        when(customerRepositoryPort.save(any(Customer.class))).thenReturn(noId);

        customerService.create(noId);

        verify(customerRepositoryPort).save(argThat(c -> c.getUserId() != null && !c.getUserId().isBlank()));
    }

    @Test
    @DisplayName("create() should preserve the provided userId")
    void create_shouldPreserveProvidedUserId() {
        when(customerRepositoryPort.save(any())).thenReturn(testCustomer);

        Customer result = customerService.create(testCustomer);

        verify(customerRepositoryPort).save(testCustomer);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("findByUserId() should return the customer when it exists")
    void findByUserId_shouldReturnCustomer_whenExists() {
        when(customerRepositoryPort.findByUserId(testCustomer.getUserId()))
                .thenReturn(Optional.of(testCustomer));

        Customer result = customerService.findByUserId(testCustomer.getUserId());

        assertThat(result.getUserId()).isEqualTo(testCustomer.getUserId());
        assertThat(result.getEmail()).isEqualTo(testCustomer.getEmail());
    }

    @Test
    @DisplayName("findByUserId() should throw CustomerNotFoundException when not found")
    void findByUserId_shouldThrow_whenNotFound() {
        when(customerRepositoryPort.findByUserId("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findByUserId("unknown"))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("unknown");
    }

    @Test
    @DisplayName("syncOrders() should update the orders list with orderRef values from cache")
    void syncOrders_shouldUpdateOrdersList() {
        when(customerRepositoryPort.findByUserId(testCustomer.getUserId()))
                .thenReturn(Optional.of(testCustomer));
        List<Order> orders = List.of(
                Order.builder().orderRef("ORDER-001").userId(testCustomer.getUserId()).build(),
                Order.builder().orderRef("ORDER-002").userId(testCustomer.getUserId()).build()
        );
        when(orderCachePort.findByUserId(testCustomer.getUserId())).thenReturn(orders);
        when(customerRepositoryPort.save(any())).thenReturn(testCustomer);

        customerService.syncOrders(testCustomer.getUserId());

        verify(customerRepositoryPort).save(argThat(c ->
                c.getOrders() != null
                && c.getOrders().size() == 2
                && c.getOrders().contains("ORDER-001")));
    }

    @Test
    @DisplayName("delete() should throw CustomerNotFoundException when customer does not exist")
    void delete_shouldThrow_whenNotFound() {
        when(customerRepositoryPort.existsByUserId("ghost")).thenReturn(false);

        assertThatThrownBy(() -> customerService.delete("ghost"))
                .isInstanceOf(CustomerNotFoundException.class);

        verify(customerRepositoryPort, never()).deleteByUserId(any());
    }

    @Test
    @DisplayName("delete() should delegate to repository when customer exists")
    void delete_shouldCallRepository_whenCustomerExists() {
        when(customerRepositoryPort.existsByUserId(testCustomer.getUserId())).thenReturn(true);

        customerService.delete(testCustomer.getUserId());

        verify(customerRepositoryPort).deleteByUserId(testCustomer.getUserId());
    }
}
