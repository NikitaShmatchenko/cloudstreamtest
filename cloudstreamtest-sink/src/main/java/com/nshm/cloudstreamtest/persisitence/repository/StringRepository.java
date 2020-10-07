package com.nshm.cloudstreamtest.persisitence.repository;

import com.nshm.cloudstreamtest.persisitence.entity.ProcessedString;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StringRepository extends CrudRepository<ProcessedString, UUID> {
}
