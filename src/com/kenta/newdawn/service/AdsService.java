package com.kenta.newdawn.service;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Dawn Service
 */
public class AdsService extends JacksonSpringAndroidSpiceService {

    /**
     * Timeout when calling a web service (in ms).
     */
    private static final int WEB_SERVICE_TIMEOUT = 30000;

    @Override
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(WEB_SERVICE_TIMEOUT);
        httpRequestFactory.setConnectTimeout(WEB_SERVICE_TIMEOUT);
        restTemplate.setRequestFactory(httpRequestFactory);

        return restTemplate;
    }
}
