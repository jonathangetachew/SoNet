package edu.mum.sonet.services.impl;

import edu.mum.sonet.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveFile(MultipartFile file, String rootDirectory ) {
//        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        String path = null;
        try {
        if (file != null && !file.isEmpty()) {
            java.util.Date date = new java.util.Date();
            int i = (int) (date.getTime() / 1000);
            String extention = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String fileName =file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
            path = rootDirectory + fileName + "-" + i + "."+ extention;

            File finalFilePath = new File(path);
            //create all path directories if not exist
            finalFilePath.getParentFile().mkdirs();
            file.transferTo(new File(path));
        }
            } catch (IOException e) {
                e.printStackTrace();
            }
              return path;
    }

    @Override
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

}
