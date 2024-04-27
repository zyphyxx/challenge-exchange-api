package com.exchange;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Challenge {

    public static void main(String[] args) throws IOException, InterruptedException {

        String api_url = "https://v6.exchangerate-api.com/v6/47facc66f2b3b7e35f2c52da/latest/USD";

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(api_url)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Verifica se a resposta foi bem-sucedida (código de status 200)
        if (response.statusCode() == 200) {
            // Converte a resposta JSON para um objeto Java usando Gson
            Gson gson = new Gson();
            ExchangeRates exchangeRates = gson.fromJson(response.body(), ExchangeRates.class);

            // Exibe as taxas de câmbio
            System.out.println("Taxas de câmbio em relação ao dólar dos EUA:");
            System.out.println(exchangeRates);
        } else {
            System.out.println("Falha ao obter taxas de câmbio. Código de status: " + response.statusCode());
        }
    }
}
