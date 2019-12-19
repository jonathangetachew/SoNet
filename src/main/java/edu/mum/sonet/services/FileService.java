package edu.mum.sonet.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(MultipartFile file, String rootDirectory);
    void deleteFile(String filePath);
}
