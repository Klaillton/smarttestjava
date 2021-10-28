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

import com.smartconsulting.smarttestjava.model.Pythagorean;
import com.smartconsulting.smarttestjava.repository.PythagoreanRepository;

@RestController
@RequestMapping("/pythagorean")
public class PythagoreanResource {
	boolean flag;

	@Autowired
	private PythagoreanRepository pythagoreanRepository;

	@GetMapping
	public List<Pythagorean> listar() {
		return pythagoreanRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<HashMap<String, String>> criar(@Valid @RequestBody Pythagorean pythagorean,
			HttpServletResponse response) {
		flag = false;

		validateNumber(pythagorean);

		pythagorean.setResultado(
				CalculatePythagoreanTheorem(pythagorean.getNumA(), pythagorean.getNumB(), pythagorean.getNumC()));

		Pythagorean pythagoreanSalva = pythagoreanRepository.save(pythagorean);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pythagoreanSalva.getCodigo()).toUri();



		response.setHeader("Location", uri.toASCIIString());

		HashMap<String, String> map = ocurrenceMap(pythagoreanSalva);

		return ResponseEntity.ok(map);
	}

	private void validateNumber(Pythagorean pythagorean) {
		if ((int) pythagorean.getNumC() == 0) {
			pythagorean.setNumC((int) Math.pow(pythagorean.getNumA(), 2) + (int) Math.pow(pythagorean.getNumB(), 2));
			flag = true;
		} else if ((int) pythagorean.getNumB() == 0) {
			pythagorean.setNumB((int) Math.pow(pythagorean.getNumA(), 2) + (int) Math.pow(pythagorean.getNumC(), 2));
			flag = true;
		} else if ((int) pythagorean.getNumA() == 0) {
			pythagorean.setNumA((int) Math.pow(pythagorean.getNumB(), 2) + (int) Math.pow(pythagorean.getNumC(), 2));
			flag = true;
		}

	}

	private boolean CalculatePythagoreanTheorem(int numA, int numB, double numC) {
		if (!flag)
			numA = (int) Math.pow(numA, 2);
		if (!flag)
			numB = (int) Math.pow(numB, 2);
		if (!flag)
			numC = (int) Math.pow(numC, 2);
		return (numA + numB) == numC;

	}

	@GetMapping("/{codigo}")
	public ResponseEntity<HashMap<String, String>> buscarPeloCodigo(@PathVariable Long codigo) {

		List<Pythagorean> ocurr = pythagoreanRepository.findByCodigo(codigo);
		HashMap<String, String> map = new HashMap<String, String>();
		if (!ocurr.isEmpty()) {
			map = ocurrenceMap(ocurr.get(0));
		}

		return !ocurr.isEmpty() ? ResponseEntity.ok(map) : ResponseEntity.notFound().build();
	}

	private HashMap<String, String> ocurrenceMap(Pythagorean pythagorean) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("numA", String.valueOf(pythagorean.getNumA()));
		map.put("numB", String.valueOf(pythagorean.getNumB()));
		map.put("numC", String.valueOf(pythagorean.getNumC()));
		map.put("resultado", String.valueOf(pythagorean.isResultado()));
		return map;
	}

}
