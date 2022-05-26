package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class RetryService {

    public String testAfterRestart() {
        String result = "Retry worked at {} Yeah!!!";
        log.info(result, new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        return result;
    }
}
