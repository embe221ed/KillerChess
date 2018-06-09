package com.killerchess.core.session;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.HttpCookie;
import java.util.Collections;
import java.util.List;

public class LocalSessionSingleton {

    private volatile static LocalSessionSingleton instance;
    private MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    private HttpCookie cookie;

    private LocalSessionSingleton() {
    }

    public static LocalSessionSingleton getInstance() {
        if (instance == null) {
            synchronized (LocalSessionSingleton.class) {
                if (instance == null) {
                    instance = new LocalSessionSingleton();
                }
            }
        }

        return instance;
    }

    public void addParameter(String key, String value) {
        parameters.add(key, value);
    }

    public String getParameter(String key) {
        return parameters.getFirst(key);
    }

    public HttpCookie getCookie() {
        return cookie;
    }

    public void setCookie(ResponseEntity responseEntity) {
        List<HttpCookie> cookies = HttpCookie.parse(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        this.cookie = cookies.get(0);
    }

    public void setCookie(HttpCookie cookie) {
        this.cookie = cookie;
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

    public <T> ResponseEntity<List<T>> exchange(String url, HttpMethod httpMethod, MultiValueMap<String, String>
            parameters,
                                                ParameterizedTypeReference<List<T>>
                                                        parameterizedTypeReference) {
        var requestEntity = getHttpEntity(parameters);
        var restTemplate = new RestTemplate();
        return restTemplate.exchange(url, httpMethod, requestEntity, parameterizedTypeReference);
    }
    //TODO AK przerobić wszystkie części kodu wykorzystujące tę metodę tak, by wykorzystywać exchange

    // (przykład w RoomCreatorController) i tę zrobić prywatną
    public HttpEntity<MultiValueMap<String, String>> getHttpEntity(MultiValueMap<String, String> map) {
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
