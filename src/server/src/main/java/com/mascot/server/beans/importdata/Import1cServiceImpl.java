package com.mascot.server.beans.importdata;

import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobSubTypeCost;
import com.mascot.server.model.JobType;
import com.mascot.server.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Created by Николай on 02.12.2016.
 */
@Service(Import1cService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class Import1cServiceImpl implements Import1cService {
    @Override
    public ImportCheckData checkImport() {

        /*

         SELECT
         jobType._IDRRef as jobType_id, jobType._Code as jobTypeCode, jobType._Description jobType,
         jobSubType._IDRRef as jobSubType_id, jobSubType._Code as jobSubTypeCode, jobSubType._Description jobSubType
         FROM _Reference6995 jobSubType
         INNER JOIN _Reference6995 jobType ON jobType._IDRRef = jobSubType._ParentIDRRef
         order by jobType._IDRRef


         -- COST
         SELECT
         product._IDRRef product_id, product._Code as productCode, product._Description as product,
         subType._IDRRef subType_id,  subType._Code as subTypeCode, subType._Description subType,
         cost._Fld7004 cost
         FROM _Reference6995_VT7001 as cost
         INNER JOIN _Reference46 product ON cost._Fld7003RRef = product._IDRRef
         INNER JOIN _Reference6995 subType ON cost._Reference6995_IDRRef = subType._IDRRef

         */


        ImportCheckData data = new ImportCheckData();
        Product product = new Product();
        product.setName("Новый диван 1");

        JobType jobType = new JobType();
        jobType.setName("Новый вид работы");

        JobSubType jobSubType = new JobSubType();
        jobSubType.setName("Новый подвид работы");
        jobSubType.setJobType(jobType);

        JobSubTypeCost cost = new JobSubTypeCost();
        cost.setJobSubType(jobSubType);
        cost.setProduct(product);
        cost.setCost(11.0);

        data.setNewProducts(Arrays.asList(product));
        data.setNewJobTypes(Arrays.asList(jobType));
        data.setNewJobSubTypes(Arrays.asList(jobSubType));
        data.setNewCosts(Arrays.asList(cost));
        return data;
    }

    @Override
    public ImportStat doImport() {
        ImportStat importStat = new ImportStat();
        importStat.setNewProductCount(1);
        importStat.setNewJobTypeCount(2);
        importStat.setNewJobSubTypeCount(3);
        importStat.setNewCostCount(4);
        return importStat;
    }

    @Override
    public ImportProgress getProgress() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
