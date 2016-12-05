package com.mascot.server.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Nikolay on 05.12.2016.
 */
@MappedSuperclass
public class ExternalEntity extends IdentifiedDeleted {

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "external_code")
    private String externalCode;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }
}
