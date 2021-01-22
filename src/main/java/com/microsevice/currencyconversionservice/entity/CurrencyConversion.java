package com.microsevice.currencyconversionservice.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {
	
	
	private int id;
	private String from;
	private String to;
	private BigDecimal currencyValue;
	private BigDecimal quantity;
	private BigDecimal totalAmount;
	private int port;
	

}
