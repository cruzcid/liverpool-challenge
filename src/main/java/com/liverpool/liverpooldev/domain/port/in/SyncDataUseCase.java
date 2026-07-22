package com.liverpool.liverpooldev.domain.port.in;

/**
 * Action to syncronize data from /pedidos and /items
*/
public interface SyncDataUseCase {
    void syncAll();
}
