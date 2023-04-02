package com.github.leanfe.updates.json;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Data {
    private DataMaintenance maintenance;
    private List<DataFile> files = new ArrayList<>();
    private List<String> ignoreFiles = new ArrayList<>();

}
