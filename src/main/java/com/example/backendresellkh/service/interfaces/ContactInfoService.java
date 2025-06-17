package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.model.entity.ContactInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ContactInfoService {
    List<ContactInfo> getContactInfoByProductId(Long productId);
}
