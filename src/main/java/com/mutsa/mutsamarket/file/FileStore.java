package com.mutsa.mutsamarket.file;

import com.mutsa.mutsamarket.exception.FileIsEmptyException;
import com.mutsa.mutsamarket.exception.FileStoreFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;
//
//    public String getFileDir() {
//        return fileDir;
//    }

    public String storeFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileIsEmptyException();
        }

        String storedName = createStoreFileName(file.getOriginalFilename());
        String pathName = fileDir + storedName;
        try {
            file.transferTo(new File(pathName));
        } catch (IOException e) {
            throw new FileStoreFailException(e);
        }
        return pathName;
    }

//    public StoredFile storeFile(MultipartFile file) throws IOException {
//
//        if (file.isEmpty()) {
//            return null;
//        }
//
//        String fileName = file.getOriginalFilename();
//        String storedName = createStoreFileName(fileName);
//        file.transferTo(new File(fileDir, storedName));
//        return new StoredFile(fileName, storedName);
//    }

    private String createStoreFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFilename);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}
