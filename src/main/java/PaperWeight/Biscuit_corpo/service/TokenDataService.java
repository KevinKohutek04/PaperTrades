package PaperWeight.Biscuit_corpo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.URI;

@Service
public class TokenDataService {
    private static double BTC;
    private static double ETH;
    private static double SOL;
    public static double getCryptoData(int type) {
        switch (type) {
            case 1:
                return BTC;
            case 2:
                return ETH;
            case 3:
                return SOL;
        }
        System.err.println("\n\nBROKEN TOKEN SERVICE DATA LINE 32\n\n");
        return 0.0;
    }
    public static void setCryptoData(double price, int type) {
        switch (type) {
            case 1:
                BTC = price;
            break;
            case 2:
                ETH = price;
            break;
            case 3:
                SOL = price;
            break;
        }
    }
    @Scheduled(fixedRate = 6000)
    public static void updateC() {

        String arrayC[] = {"https://api.coincap.io/v2/assets/ethereum", "https://api.coincap.io/v2/assets/bitcoin", "https://api.coincap.io/v2/assets/solana"};
        int i = 0;

        for(String SType : arrayC) {
            double price = 0;
            try {
                HttpClient cleint = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(java.net.URI.create(SType)).build();

                HttpResponse<String> response = cleint.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String input = response.body();
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(input);
                    JsonNode dataNode = rootNode.get("data");
                    JsonNode priceUsdNode = dataNode.get("priceUsd");
                    price = Double.parseDouble(priceUsdNode.asText());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //return restTemplate.getForObject(API_URL, CryptoData.class);
            setCryptoData(price, i);
            i++;
        }

    }

    public double getBTC() {
        return BTC;
    }

    public void setBTC(double BTC) {
        this.BTC = BTC;
    }

    public double getETH() {
        return ETH;
    }

    public void setETH(double ETH) {
        this.ETH = ETH;
    }

    public double getSOL() {
        return SOL;
    }

    public void setSOL(double SOL) {
        this.SOL = SOL;
    }
}
