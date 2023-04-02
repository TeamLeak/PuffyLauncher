package com.github.leanfe.updates.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataFile {
    private String path;
    private String hash;
    private String url;

    public DataFile(String path, String hash, String url) {
        this.path = path;
        this.hash = hash;
        this.url = url;
    }

}
