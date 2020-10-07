package com.nshm.cloudstreamtest.persisitence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "processed_string")
public class ProcessedString {
    @Id
    @Column
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String processedString;
}
