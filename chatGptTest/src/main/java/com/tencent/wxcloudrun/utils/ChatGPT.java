package com.tencent.wxcloudrun.utils;//package com;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * @Description
 * @Author CentAtankie
 **/
public class ChatGPT {
//    public static void main(String[] args) throws Exception {
//
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "7890");
//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "7890");
//
//        // Set the API endpoint
//        String apiEndpoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
//
//        // Set the prompt to ask ChatGPT
//        String prompt = "Hello, my name is ChatGPT. What can I help you with today?";
//
//        // Set the parameters for generating the response
//        double temperature = 0.7;
//        int maxTokens = 100;
//
//        // Set the OpenAI API key
//        String apiKey = "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";
//
//        // Build the request payload
//        JsonObject payload = new JsonObject();
//        payload.addProperty("prompt", prompt);
//        payload.addProperty("temperature", temperature);
//        payload.addProperty("max_tokens", maxTokens);
//
//        // Send the request to the OpenAI API
//        URL url = new URL(apiEndpoint);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
//        connection.setDoOutput(true);
//        connection.getOutputStream().write(payload.toString().getBytes());
//
//        // Read the response from the OpenAI API
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        StringBuilder response = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            response.append(line);
//        }
//
//        // Parse the response from the OpenAI API
//        JsonElement jsonResponse = JsonParser.parseString(response.toString());
//        JsonArray choices = jsonResponse.getAsJsonObject().getAsJsonArray("choices");
//        JsonObject choice = choices.get(0).getAsJsonObject();
//        String text = choice.get("text").getAsString();
//
//        // Print the response from ChatGPT
//        System.out.println(text);
//    }

    public static void main(String[] args) {
        try {
            String apiKey = "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";
            String prompt = "Hello, world!";
            String urlParameters = "{\"prompt\": \"" + prompt + "\",\"max_tokens\": 5}";
            URL url = new URL("https://api.openai.com/v1/engines/davinci-codex/completions");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new java.net.InetSocketAddress("127.0.0.1", 8080));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);
            connection.getOutputStream().write(urlParameters.getBytes("UTF-8"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
