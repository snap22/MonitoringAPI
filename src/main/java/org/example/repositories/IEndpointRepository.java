package org.example.repositories;

import org.example.entities.EndpointEntity;
import org.example.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IEndpointRepository extends JpaRepository<EndpointEntity, Long>  {

}
