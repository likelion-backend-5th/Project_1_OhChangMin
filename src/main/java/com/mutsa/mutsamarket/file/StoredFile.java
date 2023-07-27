package com.mutsa.mutsamarket.file;

import lombok.Data;

@Data
public class StoredFile {

    private String originalName;
    private String storedName;

    public StoredFile(String originalName, String storedName) {
        this.originalName = originalName;
        this.storedName = storedName;
    }
}
