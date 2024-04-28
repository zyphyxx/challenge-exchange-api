package com.exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/47facc66f2b3b7e35f2c52da/latest/USD";

    public static void main(String[] args) {
        try {
            // Faz a requisição HTTP para obter as taxas de câmbio
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = responseReader.readLine()) != null) {
                response.append(line);
            }
            responseReader.close();

            // Analisa o JSON de resposta
            JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            // Filtra apenas as moedas específicas
            Map<String, Double> specificRates = new HashMap<>();
            specificRates.put("ARS", rates.has("ARS") ? rates.get("ARS").getAsDouble() : null);
            specificRates.put("BOB", rates.has("BOB") ? rates.get("BOB").getAsDouble() : null);
            specificRates.put("BRL", rates.has("BRL") ? rates.get("BRL").getAsDouble() : null);
            specificRates.put("CLP", rates.has("CLP") ? rates.get("CLP").getAsDouble() : null);
            specificRates.put("COP", rates.has("COP") ? rates.get("COP").getAsDouble() : null);
            specificRates.put("USD", rates.has("USD") ? rates.get("USD").getAsDouble() : null);

            // Solicita ao usuário que selecione as moedas para conversão
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Selecione a moeda base \n1 - USD \n2 - ARS\n3 - BOB\n4 - BRL\n5 - CLP\n6 - COP \n");
            int baseIndex = Integer.parseInt(reader.readLine());
            System.out.print("Selecione a moeda de destino \n1 - USD \n2 - ARS\n3 - BOB\n4 - BRL\n5 - CLP\n6 - COP \n");
            int targetIndex = Integer.parseInt(reader.readLine());

            // Mapeia os índices selecionados para as moedas correspondentes
            String[] currencies = {"USD", "ARS", "BOB", "BRL", "CLP", "COP"};
            String baseCurrency = currencies[baseIndex - 1];
            String targetCurrency = currencies[targetIndex - 1];

            System.out.print("Digite o valor a ser convertido: ");
            double amount = Double.parseDouble(reader.readLine());

            // Verifica se as moedas selecionadas estão presentes nas taxas de câmbio
            if (specificRates.containsKey(baseCurrency) && specificRates.containsKey(targetCurrency)) {
                double baseRate = specificRates.get(baseCurrency);
                double targetRate = specificRates.get(targetCurrency);
                double convertedAmount = amount * (targetRate / baseRate);

                // Exibe o resultado da conversão
                System.out.println(amount + " " + baseCurrency + " equivalem a " + convertedAmount + " " + targetCurrency);
            } else {
                System.out.println("Moeda selecionada não encontrada.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
