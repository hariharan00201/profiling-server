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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    @GetMapping("/charts/{filename}")
    public ResponseEntity<?> getUsers(@PathVariable String filename) throws IOException {
        ResponseData responseData = jfrExtractorService.extractDataFromJFR("target/classes/static/"+filename);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(responseData);
        System.out.println(json);
        return ResponseEntity.ok(json);
    }


    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try {
            InputStream in = file.getInputStream();
            FileOutputStream f = new FileOutputStream("target/classes/static/"+file.getOriginalFilename());
            int ch = 0;
            while ((ch = in.read()) != -1) {
                f.write(ch);
            }

            f.flush();
            f.close();
            return ResponseEntity.ok(file.getOriginalFilename());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}
