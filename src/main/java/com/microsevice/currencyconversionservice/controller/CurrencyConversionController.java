package com.microsevice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microsevice.currencyconversionservice.entity.CurrencyConversion;
import com.microsevice.currencyconversionservice.entity.ExchangeValue;
import com.microsevice.currencyconversionservice.feignproxy.CurrencyExchangeServiceFeignProxy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeServiceFeignProxy proxy;

	@GetMapping("/getAmount/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getAmount(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String, String> uriValues = new HashMap<>();
		uriValues.put("from", from);
		uriValues.put("to", to);

		ResponseEntity<ExchangeValue> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8000/get/from/{from}/to/{to}", ExchangeValue.class, uriValues);
		ExchangeValue exchangeValue = responseEntity.getBody();
		return new CurrencyConversion(exchangeValue.getId(), from, to, exchangeValue.getConversionMultiple(), quantity,
				quantity.multiply(exchangeValue.getConversionMultiple()), exchangeValue.getPort());
	}

	@HystrixCommand(fallbackMethod = "getData")
	@GetMapping("/proxy/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion getAmountByProxy(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		ExchangeValue exchangeValue = proxy.getExchangeAmount(from, to);
		return new CurrencyConversion(exchangeValue.getId(), from, to, exchangeValue.getConversionMultiple(), quantity,
				quantity.multiply(exchangeValue.getConversionMultiple()), exchangeValue.getPort());
	}

	public CurrencyConversion getData(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		return new CurrencyConversion(1, from, to, BigDecimal.valueOf(60), quantity,
				quantity.multiply(BigDecimal.valueOf(60)), 8080);
	}

}
