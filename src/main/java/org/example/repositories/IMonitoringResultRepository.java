package org.example.repositories;

import org.example.entities.MonitoringResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMonitoringResultRepository extends JpaRepository<MonitoringResultEntity, Long>  {
}
