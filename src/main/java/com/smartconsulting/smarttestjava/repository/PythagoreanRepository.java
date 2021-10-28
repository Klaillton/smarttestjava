package com.smartconsulting.smarttestjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartconsulting.smarttestjava.model.Pythagorean;

@Repository
public interface PythagoreanRepository extends JpaRepository<Pythagorean, Long> {
	
	public List<Pythagorean> findByCodigo(Long codigo);

}
