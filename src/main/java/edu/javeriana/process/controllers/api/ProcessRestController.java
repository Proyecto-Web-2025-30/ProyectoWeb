package edu.javeriana.process.controllers.api;

import edu.javeriana.process.DTOs.ApiErrorResponse;
import edu.javeriana.process.model.Process;
import edu.javeriana.process.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/processes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProcessRestController {

    private final ProcessService processService;

    @GetMapping
    public ResponseEntity<List<Process>> getAllProcesses() {
        try {
            List<Process> processes = processService.getAll();
            return ResponseEntity.ok(processes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProcessById(@PathVariable Long id) {
        try {
            Process process = processService.getById(id);
            return ResponseEntity.ok(process);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("Proceso no encontrado", 404));
        }
    }

    @PostMapping
    public ResponseEntity<?> createProcess(@RequestBody Process process) {
        try {
            Process created = processService.create(process);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("Error creando proceso", 400));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProcess(@PathVariable Long id, @RequestBody Process process) {
        try {
            Process updated = processService.update(id, process);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("Error actualizando proceso", 400));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProcess(@PathVariable Long id) {
        try {
            processService.delete(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Proceso eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("Error eliminando proceso", 400));
        }
    }
}

