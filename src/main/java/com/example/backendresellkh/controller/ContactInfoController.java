package com.example.backendresellkh.controller;

import com.example.backendresellkh.model.entity.ContactInfo;
import com.example.backendresellkh.service.interfaces.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contact-info")
@RequiredArgsConstructor
public class ContactInfoController {

    private final ContactInfoService contactInfoService;

    @GetMapping("/{productId}")
    public ResponseEntity<List<ContactInfo>> getContactInfoByProductId(@PathVariable Long productId) {
        try {
            List<ContactInfo> contactInfos = contactInfoService.getContactInfoByProductId(productId);

            if (contactInfos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                return ResponseEntity.ok(contactInfos);
            }
        } catch (Exception e) {
            System.err.println("Error fetching contact info for product ID: " + productId);
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
