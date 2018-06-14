package com.killerchess.core.session;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.*;

public class LocalSessionSingleton {

    private volatile static LocalSessionSingleton instance;
    private Map<String, String> parameters = new LinkedHashMap<>();
    private HttpCookie cookie;
    private Properties properties = new Properties();


    public static LocalSessionSingleton getInstance() {
        if (instance == null) {
            synchronized (LocalSessionSingleton.class) {
                if (instance == null) {
                    instance = new LocalSessionSingleton();
                    instance.readConfigFile();
                }
            }
        }

        return instance;
    }

    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public HttpCookie getCookie() {
        return cookie;
    }

    public void setCookie(HttpCookie cookie) {
        this.cookie = cookie;
    }

    public void setCookie(ResponseEntity responseEntity) {
        List<HttpCookie> cookies = HttpCookie.parse(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        this.cookie = cookies.get(0);
    }

    public void saveToConfigFile() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("config.properties");
            parameters.forEach((key, value) -> properties.setProperty(key, value));
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readConfigFile() {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("config.properties");

            properties.load(inputStream);
            properties.keys().asIterator().forEachRemaining(property ->
                    parameters.put(property.toString(), properties.getProperty(property.toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCookie() {
        this.cookie = null;
    }

    public boolean isCookieSet() {
        return this.cookie != null;
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod httpMethod, MultiValueMap<String, String> parameters,
                                          Class<T> responseEntity) {
        var requestEntity = getHttpEntity(parameters);
        var restTemplate = new RestTemplate();
        return restTemplate.exchange(url, httpMethod, requestEntity, responseEntity);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod httpMethod,
                                          MultiValueMap<String, String> parameters,
                                          ParameterizedTypeReference<T> parameterizedTypeReference) {
        var requestEntity = getHttpEntity(parameters);
        var restTemplate = new RestTemplate();
        return restTemplate.exchange(url, httpMethod, requestEntity, parameterizedTypeReference);
    }

    private HttpEntity<MultiValueMap<String, String>> getHttpEntity(MultiValueMap<String, String> map) {
        return new HttpEntity<>(map, getHeaders());
    }

    private HttpHeaders getHeaders() {
        if (isCookieSet()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.add("Cookie", cookie.toString());
            return headers;
        } else {
            return null;
        }
    }


}
