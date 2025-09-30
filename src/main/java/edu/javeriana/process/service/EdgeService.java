package edu.javeriana.process.service;

import edu.javeriana.process.model.Edge;
import java.util.List;

public interface EdgeService {
    Edge create(Edge edge);           // CREATE
    Edge getById(Long id);            // READ
    List<Edge> getAll();              // READ ALL
    Edge update(Long id, Edge edge);  // UPDATE
    void delete(Long id);             // DELETE
}
