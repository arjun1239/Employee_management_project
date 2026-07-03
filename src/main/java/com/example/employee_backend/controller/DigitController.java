

// added the code again for  digit controller 

//package com.example.demo.controller;

//
//
//package com.example.employee_backend.controller;
//
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.*;
//import org.springframework.util.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "*")
////@CrossOrigin(origins = "http://localhost:4200")
//public class DigitController {
//
//    private final RestTemplate restTemplate;
//
//    // ✅ Constructor Injection
//    public DigitController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @PostMapping("/predict")
//    public ResponseEntity<?> predict(@RequestParam("file") MultipartFile file) {
//
//        String fastApiUrl = "http://127.0.0.1:8000/predict";
//
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            // ✅ FIXED WAY
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("file", new InputStreamResource(file.getInputStream()) {
//                @Override
//                public String getFilename() {
//                    return file.getOriginalFilename();
//                }
//            });
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity =
//                    new HttpEntity<>(body, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(
//                    fastApiUrl,
//                    requestEntity,
//                    String.class
//            );
//
//            return ResponseEntity.ok(response.getBody());
//
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("File error: " + e.getMessage());
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error: " + e.getMessage());
//        }
//    }
//}


package com.example.employee_backend.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DigitController {

    private final RestTemplate restTemplate;

    // ✅ Constructor Injection
    public DigitController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("file") MultipartFile file) {

        String fastApiUrl = "http://127.0.0.1:8000/predict";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // ✅ FIX: Use ByteArrayResource instead of InputStreamResource
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            body.add("file", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    fastApiUrl,
                    requestEntity,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


}
