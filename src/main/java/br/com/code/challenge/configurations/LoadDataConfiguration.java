package br.com.code.challenge.configurations;

import br.com.code.challenge.dto.PropertyDTO;
import br.com.code.challenge.exceptions.CodeChallengeException;
import br.com.code.challenge.rest.response.PropertyResponse;
import br.com.code.challenge.service.PropertiesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static br.com.code.challenge.enums.EnumBroker.*;

@Slf4j
@Component
public class LoadDataConfiguration implements Serializable {
    private static final long serialVersionUID = -5928157021046252096L;
    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 1;

    @Autowired
    private PropertiesService propertiesService;

    @Bean
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${grupo-zap.url.sources}")
    private String urlSources;

    @PostConstruct
    public void init() throws CodeChallengeException {
        log.info("LOADING DATA IN MEMORY....");
        String json = restTemplate().getForObject(urlSources, String.class);
        GsonJsonParser gson = new GsonJsonParser();

        List map = gson.parseList(json);
        map.forEach(p -> {
            JsonObject jsonObject = new Gson().toJsonTree(p).getAsJsonObject();
            try {
                PropertyResponse response = new ObjectMapper().readValue(jsonObject.toString(), PropertyResponse.class);
                PropertyDTO dto = new ModelMapper().map(response, PropertyDTO.class);
                propertiesService.addProperty(dto);
            } catch (IOException e) {
                log.error("Problems to LoadData by consuming json: ({})", jsonObject);
            }
        });

        log.info("Initial Loading for {} Properties", NOT_ELEGIBLE);
        propertiesService.getProperties(NOT_ELEGIBLE, PAGE_NUMBER, PAGE_SIZE);

        log.info("Initial Loading for {} Properties", VIVAREAL);
        propertiesService.getProperties(VIVAREAL, PAGE_NUMBER, PAGE_SIZE);

        log.info("Initial Loading for {} Properties", GRUPOZAP);
        propertiesService.getProperties(GRUPOZAP, PAGE_NUMBER, PAGE_SIZE);

        log.info("LOADED DATA IN MEMORY!");
    }


}
