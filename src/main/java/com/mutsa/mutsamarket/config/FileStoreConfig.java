package com.mutsa.mutsamarket.config;

import com.mutsa.mutsamarket.file.FileStore;
import com.mutsa.mutsamarket.file.FileStoreImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FileStoreConfig {

    @Bean
    @Profile("dev")
    public FileStore fileStore() {
        return new FileStoreImpl();
    }

    @Bean
    @Profile("test")
    public FileStore testFileStore() {
        return file -> "pathName";
    }
}
