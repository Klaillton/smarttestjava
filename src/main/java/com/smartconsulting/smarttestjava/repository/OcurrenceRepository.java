package com.smartconsulting.smarttestjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartconsulting.smarttestjava.model.Ocurrence;

@Repository
public interface OcurrenceRepository extends JpaRepository<Ocurrence, Long> {
	
	public List<Ocurrence> findByNumber(Double double1);

}
