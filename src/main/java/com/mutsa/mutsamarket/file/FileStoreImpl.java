package com.mutsa.mutsamarket.file;

import com.mutsa.mutsamarket.exception.FileIsEmptyException;
import com.mutsa.mutsamarket.exception.FileStoreFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class FileStoreImpl implements FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String storeFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileIsEmptyException();
        }

        String storedName = createStoreFileName(file.getOriginalFilename());
        String pathName = fileDir + storedName;

        try {
            file.transferTo(new File(pathName));
        } catch (IOException e) {
            log.info("파일 저장 실패", e);
            throw new FileStoreFailException(e);
        }
        return pathName;
    }

    private String createStoreFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFilename);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}
