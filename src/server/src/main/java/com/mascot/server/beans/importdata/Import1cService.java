package com.mascot.server.beans.importdata;

/**
 * Created by Николай on 02.12.2016.
 */
public interface Import1cService {
    String NAME = "Import1cService";

    ImportCheckData checkImport();

    ImportStat doImport();

    ImportProgress getProgress();

}
