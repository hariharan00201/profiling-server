package com.example.profilingserver.controller;

import com.example.profilingserver.model.ResponseData;
import com.example.profilingserver.service.JFRExtractorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/")
public class Controller {
    private final JFRExtractorService jfrExtractorService;

    public Controller(JFRExtractorService jfrExtractorService){
        this.jfrExtractorService = jfrExtractorService;
    }

    @GetMapping()
    public ResponseEntity<?> getUsers() throws IOException {
        ResponseData responseData = jfrExtractorService.extractDataFromJFR("src/main/resources/static/sample-1.jfr");
//        System.out.println(responseData.toString());
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.put(HttpHeaders.ACCEPT, Collections.singletonList(MediaType.APPLICATION_JSON_VALUE);
//        System.out.println(headers.get(HttpHeaders.ACCEPT));
//        responseHeaders.addAll(Collections.singletonList(MediaType.APPLICATION_JSON));
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        MyObject myObject = new MyObject();
//        String json = objectMapper.writeValueAsString(responseData);
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(responseData);
        System.out.println(json);
        return ResponseEntity.ok(json);
    }

//    @ResponseBody
//    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
//    public String handleHttpMediaTypeNotAcceptableException() {
//        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
//    }
}
