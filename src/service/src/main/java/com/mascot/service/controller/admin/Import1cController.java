package com.mascot.service.controller.admin;

import com.mascot.server.beans.importdata.Import1cService;
import com.mascot.server.beans.importdata.ImportCheckData;
import com.mascot.server.beans.importdata.ImportStat;
import com.mascot.server.model.*;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.ResultRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Created by Николай on 02.12.2016.
 */
@RestController
@RequestMapping(value = "/import-1c")
public class Import1cController extends AbstractController {
    @Inject
    private Import1cService import1cService;

    @RequestMapping(value = "/check-import", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ImportCheckRecord checkImport() {
        ImportCheckData importCheckData = import1cService.checkImport();
        ImportCheckRecord record = new ImportCheckRecord();
        record.enableImport = importCheckData.hasData();
        record.log = buildLog(importCheckData);
        return record;
    }

    @RequestMapping(value = "/do-import", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public List<String> doImport() {
        ImportStat importStat = import1cService.doImport();
        StringBuilder result = new StringBuilder("Statistics: \n");
        result.append("New products: ").append(importStat.getNewProductCount()).append("\n");
        result.append("New job types: ").append(importStat.getNewJobTypeCount()).append("\n");
        result.append("New job sub types: ").append(importStat.getNewJobSubTypeCount()).append("\n");
        result.append("New costs: ").append(importStat.getNewCostCount()).append("\n");
        logger.info(result.toString());
        return Collections.singletonList(result.toString());
    }

    private String buildLog(ImportCheckData importCheckData) {
        StringBuilder builder = new StringBuilder();
        if (importCheckData.getNewProducts() != null && !importCheckData.getNewProducts().isEmpty()) {
            builder.append("New Products\n");
            importCheckData.getNewProducts().forEach(e -> builder.append("\t").append(buildLog(e)).append("\n"));
            builder.append("\n");
        }
        if (importCheckData.getNewJobTypes() != null && !importCheckData.getNewJobTypes().isEmpty()) {
            builder.append("New Job Types\n");
            importCheckData.getNewJobTypes().forEach(e -> builder.append("\t").append(buildLog(e)).append("\n"));
            builder.append("\n");
        }
        if (importCheckData.getNewJobSubTypes() != null && !importCheckData.getNewJobSubTypes().isEmpty()) {
            builder.append("New Job Sub Types\n");
            importCheckData.getNewJobSubTypes().forEach(e -> builder.append("\t").append(buildLog(e)).append("\n"));
            builder.append("\n");
        }
        if (importCheckData.getNewCosts() != null && !importCheckData.getNewCosts().isEmpty()) {
            builder.append("New Costs\n");
            importCheckData.getNewCosts().forEach(e -> builder.append("\t").append(buildLog(e)).append("\n"));
            builder.append("\n");
        }
        return builder.toString();
    }

    private StringBuilder buildLog(JobSubTypeCost cost) {
        StringBuilder res = new StringBuilder();
        res.append(cost.getCost());
        res.append(", of sub type: ").append(cost.getJobSubType().getName());
        res.append(", of type: ").append(cost.getJobSubType().getJobType().getName());
        res.append(", of product: ").append(cost.getProduct().getName());
        return res;
    }

    private StringBuilder buildLog(JobSubType subType) {
        StringBuilder res = new StringBuilder();
        res.append(subType.getName());
        res.append(", of type: ").append(subType.getJobType().getName());
        return res;
    }

    private StringBuilder buildLog(JobType jobType) {
        StringBuilder res = new StringBuilder();
        res.append(jobType.getName());
        return res;
    }

    private StringBuilder buildLog(Product product) {
        StringBuilder res = new StringBuilder();
        res.append(product.getName());
        return res;
    }

}
