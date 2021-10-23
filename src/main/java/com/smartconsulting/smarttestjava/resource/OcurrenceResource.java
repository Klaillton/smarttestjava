package com.smartconsulting.smarttestjava.resource;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
	public ResponseEntity<Ocurrence> criar(@RequestBody Ocurrence ocurrence, HttpServletResponse response) {
		Ocurrence ocurrenceSalva = new Ocurrence();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/difference").build().toUri();
		
		ocurrence.setDatatime(LocalDateTime.now());
		
		if(ocurrence.getNumber()<100) {
			ocurrenceSalva =  ocurrenceRepository.save(ocurrence);
		
			uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
					.buildAndExpand(ocurrenceSalva.getCodigo()).toUri();
		} else {
			return ResponseEntity.badRequest().build();
		}
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(ocurrenceSalva);
	}
	
	private Integer differenceCalc(Integer number) {
		int sumS = 0, sqrS = 0, dif = 0;
		for(int i =0; i<=number; i++) {
			sumS += Math.pow(i, 2);
		}
		
		for(int x=0; x<=number; x++) {
			sqrS += x;
		}
		sqrS = (int) Math.pow(sqrS, 2);
		
		dif = sqrS - sumS;
		
		return dif;
	}

	@GetMapping("/{codigo}")
	public Map<String, String> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Ocurrence> ocurr = ocurrenceRepository.findById(codigo);
		HashMap<String, String> map = new HashMap<String, String>();
		if(ocurr.isPresent()) {		
			map.put("datetime", ocurr.get().getDatatime().toString());
			map.put("value", "solution");
			map.put("number", ocurr.get().getNumber().toString());
			map.put("ocurrences", differenceCalc(ocurr.get().getNumber()).toString());
		}
		
		return map;
	}

}
