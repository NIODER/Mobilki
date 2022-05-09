package ru.mirea.kozharinov.practice7.httpurlconnection;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class IPGetter {

    private static final String LOCATION = "https://whoer.net/v2/geoip2-city";
    private static final String IP = "https://whoer.net/resolve";
    private static final String PROVIDER = "https://whoer.net/v2/geoip2-isp";

    public Result getInfo() throws IOException {
        return new Result(getLocationInfo(), getIpInfo(), getProviderInfo());
    }

    @NonNull
    private String getLocationInfo() throws IOException {
        StringBuilder locationInfo = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(request(LOCATION));
            locationInfo.append(jsonObject.getString("continent_name")).append(" ")
                    .append(jsonObject.getString("country_name")).append(" ")
                    .append(jsonObject.getString("city_name")).append(" ");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locationInfo.toString();
    }

    @NonNull
    private String getIpInfo() throws IOException {
        StringBuilder ipInfo = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(request(IP));
            ipInfo.append(jsonObject.getString("client_ip"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ipInfo.toString();
    }

    @NonNull
    private String getProviderInfo() throws IOException {
        StringBuilder providerInfo = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(request(PROVIDER));
            providerInfo.append(jsonObject.getString("as_organization"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return providerInfo.toString();
    }

    @NonNull
    private String request(@NonNull String address) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);

            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw  new ConnectException("Server return " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return result.toString();
    }

    public static class Result {

        private final String location;
        private final String ip;
        private final String provider;

        private Result(String location, String ip, String provider) {
            this.ip = ip;
            this.location = location;
            this.provider = provider;
        }

        public String getLocation() {
            return location;
        }

        public String getIp() {
            return ip;
        }

        public String getProvider() {
            return provider;
        }
    }
}
