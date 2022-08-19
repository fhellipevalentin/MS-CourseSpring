package io.github.fhellipevalentin.hrworker.repositories;

import io.github.fhellipevalentin.hrworker.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}