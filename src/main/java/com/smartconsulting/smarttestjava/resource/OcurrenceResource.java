package com.smartconsulting.smarttestjava.resource;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartconsulting.smarttestjava.model.Ocurrence;
import com.smartconsulting.smarttestjava.repository.OcurrenceRepository;

@RestController
@RequestMapping("/difference")
public class OcurrenceResource {
	
	@Autowired
	private OcurrenceRepository ocurrenceRepository;
	
	@GetMapping
	public List<Ocurrence> listar(){
		return ocurrenceRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<HashMap<String, String>> criar(@Valid @RequestBody Ocurrence ocurrence, HttpServletResponse response) {
	
		ocurrence.setDatetime(LocalDateTime.now());		
		ocurrence.setOccurrence(differenceCalc(ocurrence.getNumber()));
		
		Ocurrence ocurrenceSalva =  ocurrenceRepository.save(ocurrence);
		
		URI	uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
					.buildAndExpand(ocurrenceSalva.getCodigo()).toUri();
		
		
		response.setHeader("Location", uri.toASCIIString());
		
		HashMap<String, String> map = ocurrenceMap(ocurrenceSalva);	
		
		return ResponseEntity.ok(map);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<HashMap<String, String>> buscarPeloCodigo(@PathVariable Double codigo) {
		
		List<Ocurrence> ocurr = ocurrenceRepository.findByNumber(codigo);
		HashMap<String, String> map = new HashMap<String, String>();
		if(!ocurr.isEmpty()) {		
			 map = ocurrenceMap(ocurr.get(0));
		}
		
		return !ocurr.isEmpty() ? ResponseEntity.ok(map) : ResponseEntity.notFound().build();
	}
	
	private HashMap<String, String> ocurrenceMap(Ocurrence ocurrence) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("datetime", String.valueOf(ocurrence.getDatetime()));
		map.put("value", "solution");
		map.put("number", String.valueOf(ocurrence.getNumber()));
		map.put("ocurrences", String.valueOf(ocurrence.getOccurrence()));
		return map;
	}
	
	private Integer differenceCalc(Double double1) {
		int sumS = 0, sqrS = 0, dif = 0;
		for(int i =0; i<=double1; i++) {
			sumS += Math.pow(i, 2);
		}
		
		for(int x=0; x<=double1; x++) {
			sqrS += x;
		}
		sqrS = (int) Math.pow(sqrS, 2);
		
		dif = sqrS - sumS;
		
		return dif;
	}

}
