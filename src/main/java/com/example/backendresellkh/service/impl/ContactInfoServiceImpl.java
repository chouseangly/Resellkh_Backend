package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.model.entity.ContactInfo;
import com.example.backendresellkh.repository.ContactInfoRepository;
import com.example.backendresellkh.service.interfaces.ContactInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;
    public List<ContactInfo> getContactInfoByProductId(Long productId){
        return contactInfoRepository.getContactInfoByProductId(productId);
    }
}
