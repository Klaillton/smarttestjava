package com.smartconsulting.smarttestjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartconsulting.smarttestjava.model.Ocurrence;

public interface OcurrenceRepository extends JpaRepository<Ocurrence, Long> {

}
