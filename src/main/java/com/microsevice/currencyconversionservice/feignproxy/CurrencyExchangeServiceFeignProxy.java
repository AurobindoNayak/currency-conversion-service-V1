package com.microsevice.currencyconversionservice.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microsevice.currencyconversionservice.entity.ExchangeValue;


//@FeignClient(name="currency-exchange-service",url="http://localhost:8000")
@RibbonClient(name="currency-exchange-service")
@FeignClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceFeignProxy {
	
	@GetMapping("/get/from/{from}/to/{to}")
	public ExchangeValue getExchangeAmount(@PathVariable String from, @PathVariable String to);

}
