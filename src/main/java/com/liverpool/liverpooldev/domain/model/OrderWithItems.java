package com.liverpool.liverpooldev.domain.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * This is the result of joining an Order with its Items for API responses.
 */
@Data
@Builder
public class OrderWithItems {

    private String orderRef;

    /** "online" / "physical". */
    private String canal;

    private String orderStatus;

    private String storeName;

    private boolean marketPlace;

   private boolean giftRegistry;

    private List<Item> items;
}
