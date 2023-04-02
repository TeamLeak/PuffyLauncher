package com.github.leanfe.updates.json;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataMaintenance {
    private boolean maintenance;
    private String message;

    public boolean isMaintenance() {
        return maintenance;
    }

}
