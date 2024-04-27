package com.exchange.model;

import java.util.Map;

public class ExchangeRates {
    private String base_code;
    private Map<String, Double> conversion_rates;

    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Moeda Base: ").append(base_code).append("\n");
        sb.append("Taxas de Conversão:\n");
        for (Map.Entry<String, Double> entry : conversion_rates.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
