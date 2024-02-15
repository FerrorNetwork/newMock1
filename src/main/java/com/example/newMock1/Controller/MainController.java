package com.example.newMock1.Controller;

import com.example.newMock1.Model.RequestDTO;
import com.example.newMock1.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

import java.math.BigDecimal;

@RestController
public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
             BigDecimal maxLimit;
            ResponseDTO responseDTO = new ResponseDTO();
            Random random = new Random();

             if(firstDigit == '8') {
                 maxLimit = new BigDecimal(2000);
                 responseDTO.setCurrency("US");
             } else if (firstDigit == '9') {
                 maxLimit = new BigDecimal(1000);
                 responseDTO.setCurrency("EU");
             } else {
                 maxLimit = new BigDecimal(10000);
                 responseDTO.setCurrency("RUB");
             }


            responseDTO.setRqUID(requestDTO.getRqUID());
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setBalance(String.valueOf(random.nextInt(maxLimit.intValue() + 1)));
            responseDTO.setMaxLimit(maxLimit.toString());

            log.error("******** RequestDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("******** ResponseDTO *******" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
