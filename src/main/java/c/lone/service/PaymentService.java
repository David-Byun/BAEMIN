package c.lone.service;

import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

@Service
public class PaymentService {

    @Value("${imp_key}")
    private String impKey;

    @Value("${imp_secret}")
    private String impSecret;

    @Data
    private class Response{
        private PaymentInfo response;
    }

    @Data
    private class PaymentInfo {
        private int amount;
    }

    public String getToken() throws IOException {
        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/users/getToken");
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        JsonObject json = new JsonObject();
        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);



    }

}










