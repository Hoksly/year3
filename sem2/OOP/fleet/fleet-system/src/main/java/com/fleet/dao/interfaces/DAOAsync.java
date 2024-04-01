package com.fleet.dao.interfaces;

import com.fleet.models.Flight;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface DAOAsync<Entity>{
    CompletableFuture<List<Entity>> getAll();

    CompletableFuture<Entity> getById(Long id);

    CompletableFuture<Void> save(Entity flight);

    CompletableFuture<Void> update(Entity flight);

    CompletableFuture<Void> delete(Long id);
}

