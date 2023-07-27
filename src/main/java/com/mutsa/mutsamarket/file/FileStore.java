package com.mutsa.mutsamarket.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStore {

    public String storeFile(MultipartFile file);
}
