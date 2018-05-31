package com.killerchess.core.session;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.HttpCookie;
import java.util.Arrays;
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

    public void setCookie(HttpCookie cookie) {
        this.cookie = cookie;
    }

    public void setCookie(ResponseEntity responseEntity) {
        List<HttpCookie> cookies = HttpCookie.parse(responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        this.cookie = cookies.get(0);
    }

    public void clearCookie() {
        this.cookie = null;
    }

    public boolean isSetCookie() {
        return this.cookie != null;
    }

    public HttpHeaders getHeaders() {
        if (isSetCookie()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.ALL));
            headers.add("Cookie", cookie.toString());
            return headers;
        } else {
            return null;
        }
    }

    public HttpEntity<MultiValueMap<String, String>> getHttpEntity(MultiValueMap<String, String> map) {
        return new HttpEntity<>(map, getHeaders());
    }
}
