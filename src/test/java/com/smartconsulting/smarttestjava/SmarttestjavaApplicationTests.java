package com.smartconsulting.smarttestjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.web.servlet.MockMvc;

import com.smartconsulting.smarttestjava.model.Ocurrence;
import com.smartconsulting.smarttestjava.model.Pythagorean;
import com.smartconsulting.smarttestjava.repository.OcurrenceRepository;
import com.smartconsulting.smarttestjava.repository.PythagoreanRepository;


class SmarttestjavaApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
    private TestEntityManager entityManager;

	@Autowired
	private OcurrenceRepository ocurrenceRepository;

	@Autowired
	private PythagoreanRepository pythagoreanRepository;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		Ocurrence ocurrence = new Ocurrence();
		ocurrence.setNumber(5.0);

		Mockito.when(ocurrenceRepository.findByNumber(Double.valueOf(ocurrence.getNumber())))
		.thenReturn((List<Ocurrence>) ocurrence);

		Pythagorean pythagorean = new Pythagorean();
		pythagorean.setCodigo(1l);

		Mockito.when(pythagoreanRepository.findByCodigo(pythagorean.getCodigo()))
				.thenReturn((List<Pythagorean>) pythagorean);
	}

	@Test
	public void CalculateOcurrences_thenReturnJsonArray() throws Exception {

		// given
	    Ocurrence ocurrence = new Ocurrence();
	    ocurrence.setNumber(5.0);
	    int sumS = 0, sqrS = 0, dif = 0;
		for(int i =0; i<=ocurrence.getNumber(); i++) {
			sumS += Math.pow(i, 2);
		}
		
		for(int x=0; x<=ocurrence.getNumber(); x++) {
			sqrS += x;
		}
		sqrS = (int) Math.pow(sqrS, 2);
		
		dif = sqrS - sumS;

	    // then
	    assertThat(dif)
	      .isEqualTo(170);
	}
	
	@Test
	public void CalculateHypotenusa_thenReturnJsonArray() throws Exception {

		// given
		Pythagorean pythagorean = new Pythagorean();
		pythagorean.setNumA(2);
		pythagorean.setNumB(3);	    

	    // then
	    assertThat(Math.pow(pythagorean.getNumA(), 2)+Math.pow(pythagorean.getNumB(), 2))
	      .isEqualTo(13);
	}

}
