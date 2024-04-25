package com.huuduc.veladstore.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {

    String uploadFile(MultipartFile file);

    Resource downloadFile(String filename);

}
