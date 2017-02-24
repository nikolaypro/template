package com.mascot.service.controller.common;

import com.mascot.server.model.Identified;
import com.mascot.server.model.Named;

/**
 * Created by Николай on 24.02.2017.
 */
public class DictionaryRecord {
    public Long id;
    public String name;

    public DictionaryRecord() {
    }

    public <T extends Identified & Named> DictionaryRecord(T el) {
        this.id = el.getId();
        this.name = el.getName();
    }
}
