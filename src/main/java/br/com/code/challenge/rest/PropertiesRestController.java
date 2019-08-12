package br.com.code.challenge.rest;

import br.com.code.challenge.enums.EnumBroker;
import br.com.code.challenge.exceptions.CodeChallengeException;
import br.com.code.challenge.rest.response.PropertiesResponse;
import br.com.code.challenge.rest.response.PropertyResponse;
import br.com.code.challenge.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Slf4j
@RestController
@RequestMapping("/properties")
public class PropertiesRestController implements Serializable {
    private static final long serialVersionUID = -8458794990024352215L;

    @Autowired
    private PropertiesService propertiesService;

    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PropertyResponse> get(@PathVariable("id") String id) throws CodeChallengeException {
        PropertyResponse response = propertiesService.getPropertyById(id);
        return ok(response);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PropertiesResponse> get(@RequestParam(value = "broker") EnumBroker broker,
                                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "100") Integer pageSize) throws CodeChallengeException {
        PropertiesResponse properties = propertiesService.getProperties(broker, pageNumber, pageSize);
        return ok(properties);
    }

}
