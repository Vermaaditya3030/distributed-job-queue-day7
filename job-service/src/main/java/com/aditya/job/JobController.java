package com.aditya.job;
import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/jobs") public class JobController {
 private final JobStore store; public JobController(JobStore s){this.store=s;}
 @PostMapping public ResponseEntity<Job> create(@Valid @RequestBody CreateJobRequest r){return ResponseEntity.status(HttpStatus.ACCEPTED).body(store.create(r));}
 @GetMapping("/{id}") public ResponseEntity<Job> get(@PathVariable String id){return store.find(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","job-service","status","UP");}
}
