package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

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
            String currency;
            Random randomNumbers = new Random();
            BigDecimal maxLimit, balance;
            String rqUID = requestDTO.getRqUID();
            if (firstDigit == '8') {
                maxLimit = (new BigDecimal(2000)).setScale(2, RoundingMode.HALF_UP);
                balance = (new BigDecimal(Math.random() * 2000)).setScale(2, RoundingMode.HALF_UP);
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = (new BigDecimal(1000)).setScale(2, RoundingMode.HALF_UP);
                balance = (new BigDecimal(Math.random() * 1000)).setScale(2, RoundingMode.HALF_UP);
                currency = "EU";
            } else {
                maxLimit = (new BigDecimal(10000)).setScale(2, RoundingMode.HALF_UP);
                balance = (new BigDecimal(Math.random() * 10000)).setScale(2, RoundingMode.HALF_UP);
                currency = "RUB";
            }
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return  responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
