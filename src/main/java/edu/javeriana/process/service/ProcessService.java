package edu.javeriana.process.service;

import edu.javeriana.process.model.Process;

import java.util.List;

public interface ProcessService {

    Process create(Process process);         //CREATE
    Process getById(Long id);                //READ
    List<Process> getAll();                  //READ ALL
    Process update(Long id, Process process);//UPDATE
    void delete(Long id);                    //DELETE
}
