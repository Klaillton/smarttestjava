package com.smartconsulting.smarttestjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartconsulting.smarttestjava.model.Ocurrence;

public interface OcurrenceRepository extends JpaRepository<Ocurrence, Long> {
	
	public List<Ocurrence> findByNumber(Long codigo);

}
