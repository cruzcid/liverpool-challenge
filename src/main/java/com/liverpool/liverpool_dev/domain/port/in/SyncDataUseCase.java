package com.liverpool.liverpool_dev.domain.port.in;

/**
 * Action to syncronize data from /pedidos and /items
*/
public interface SyncDataUseCase {
    void syncAll();
}
