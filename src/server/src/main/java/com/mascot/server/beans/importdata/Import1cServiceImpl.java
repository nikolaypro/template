package com.mascot.server.beans.importdata;

import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobSubTypeCost;
import com.mascot.server.model.JobType;
import com.mascot.server.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * Created by Николай on 02.12.2016.
 */
@Service(Import1cService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class Import1cServiceImpl extends AbstractMascotService implements Import1cService {
    @PersistenceContext(unitName = "templateMSPersistenceUnit")
    protected EntityManager msSqlEm;
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


/*
        List<Object[]> resultList1 = msSqlEm.createNativeQuery("select " +
                "   convert(varchar(max), jobSubType._IDRRef, 2) as jobSubType_id, jobSubType._Code as jobSubTypeCode, jobSubType._Description jobSubType" +
                "   FROM _Reference6995 jobSubType").getResultList();
*/

        Map<String, Product> productsMap = new HashMap<>();
        Map<String, JobType> jobTypesMap = new HashMap<>();
        Map<String, JobSubType> jobSubTypesMap = new HashMap<>();
        Map<String, JobSubTypeCost> costsMap = new HashMap<>();

        List<Object[]> resultList = msSqlEm.createNativeQuery("select distinct " +
                "         convert(varchar(max), jobType._IDRRef, 2) as jobType_id, jobType._Code as jobTypeCode, jobType._Description jobType, " +
                "         convert(varchar(max), jobSubType._IDRRef, 2) as jobSubType_id, jobSubType._Code as jobSubTypeCode, jobSubType._Description jobSubType " +
                "         FROM _Reference6995 jobSubType " +
                "         INNER JOIN _Reference6995 jobType ON jobType._IDRRef = jobSubType._ParentIDRRef " +
                "         order by jobType._IDRRef").getResultList();

        for (Object[] objects : resultList) {
            String jobTypeId = (String) objects[0];
            String jobTypeCode = (String) objects[1];
            String jobTypeDescription = (String) objects[2];

            String jobSubTypeId = (String) objects[3];
            String jobSubTypeCode = (String) objects[4];
            String jobSubTypeDescription = (String) objects[5];

            JobType jobType = jobTypesMap.get(jobTypeId);
            if (jobType == null) {
                jobTypesMap.put(jobTypeId, jobType = new JobType());
                jobType.setExternalId(jobTypeId);
                jobType.setExternalCode(jobTypeCode);
                jobType.setName(jobTypeDescription);
                jobType.setJobSubTypes(new HashSet<>());
            }
            if (jobSubTypesMap.containsKey(jobSubTypeId)) {
                continue;
            }
            JobSubType subType = new JobSubType();
            jobSubTypesMap.put(jobSubTypeId, subType);
            subType.setExternalId(jobSubTypeId);
            subType.setExternalCode(jobSubTypeCode);
            subType.setName(jobSubTypeDescription);
            subType.setJobType(jobType);
            jobType.getJobSubTypes().add(subType);
        }

        resultList = msSqlEm.createNativeQuery("SELECT distinct " +
                "         convert(varchar(max), product._IDRRef, 2) as product_id, product._Code as productCode, product._Description as product," +
                "         convert(varchar(max), subType._IDRRef, 2) as subType_id,  subType._Code as subTypeCode, subType._Description as subType," +
                "         cost._Fld7004 cost" +
                "         FROM _Reference6995_VT7001 as cost" +
                "         INNER JOIN _Reference46 product ON cost._Fld7003RRef = product._IDRRef" +
                "         INNER JOIN _Reference6995 subType ON cost._Reference6995_IDRRef = subType._IDRRef").getResultList();


        for (Object[] objects : resultList) {
            String productId = (String) objects[0];
            String productCode = (String) objects[1];
            String productDescription = (String) objects[2];

            String jobSubTypeId = (String) objects[3];
            String jobSubTypeCode = (String) objects[4];
            String jobSubTypeDescription = (String) objects[5];
            Double cost = (Double) objects[6];

            Product product = productsMap.get(productId);
            if (product == null) {
                productsMap.put(productId, product = new Product());
                product.setExternalId(productId);
                product.setExternalCode(productCode);
                product.setName(productDescription);
            }
            final JobSubType jobSubType = jobSubTypesMap.get(jobSubTypeId);
            if (jobSubType == null) {
                logger.error("Skip cost for product id = '" + productId + "' because not found job sub type with id = '" + jobSubTypeId + "'. It is sql queries error.");
                continue;
            }

            final String costKey = "Product: " + productId + ", subType: " + jobSubTypeId;
            JobSubTypeCost jobSubTypeCost = costsMap.get(costKey);
            if (jobSubTypeCost != null) {
                logger.error("Skip cost for product id = '" + productId + "' and job sub type id = '" + jobSubTypeId + "' because already exists cost for this: " + jobSubTypeCost.getCost());
                continue;
            }
            costsMap.put(costKey, jobSubTypeCost = new JobSubTypeCost());
            jobSubTypeCost.setProduct(product);
            jobSubTypeCost.setJobSubType(jobSubType);
            jobSubTypeCost.setCost(cost);
        }

        final List<JobType> newJobTypes = new ArrayList<>(jobTypesMap.values());
        Collections.sort(newJobTypes, (e1, e2) -> e1.getExternalCode().compareTo(e2.getExternalCode()));
        int order = 0;
        for (JobType newJobType : newJobTypes) {
            newJobType.setOrder(order++);
        }

        ImportCheckData data = new ImportCheckData();
        data.setNewProducts(new ArrayList<>(productsMap.values()));
        data.setNewJobTypes(newJobTypes);
        data.setNewJobSubTypes(new ArrayList<>(jobSubTypesMap.values()));
        data.setNewCosts(new ArrayList<>(costsMap.values()));
        return data;

/*
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
*/

        /*
        msSqlEm.createNativeQuery("select " +
        "jobSubType._Description jobSubType\n" +
        "         FROM _Reference6995 jobSubType\n" +
        "").getResultList()
         */
    }

    @Override
    public ImportStat doImport() {
        final ImportCheckData importCheckData = checkImport();
        importCheckData.getNewProducts().forEach(em::persist);
        importCheckData.getNewJobTypes().forEach(em::persist);
        importCheckData.getNewJobSubTypes().forEach(em::persist);
        importCheckData.getNewCosts().forEach(em::persist);

        importCheckData.getChangedProducts().forEach(em::merge);
        importCheckData.getChangedJobTypes().forEach(em::merge);
        importCheckData.getChangedJobSubTypes().forEach(em::merge);
        importCheckData.getChangedCosts().forEach(em::merge);

        importCheckData.getRemovedProducts().forEach(em::remove);
        importCheckData.getRemovedJobTypes().forEach(em::remove);
        importCheckData.getRemovedJobSubTypes().forEach(em::remove);
        importCheckData.getRemovedCosts().forEach(em::remove);

        clearDatabase();

        ImportStat importStat = new ImportStat();

        importStat.setNewProductCount(size(importCheckData.getNewProducts()));
        importStat.setNewJobTypeCount(size(importCheckData.getNewJobTypes()));
        importStat.setNewJobSubTypeCount(size(importCheckData.getNewJobSubTypes()));
        importStat.setNewCostCount(size(importCheckData.getNewCosts()));

        importStat.setChangedProductCount(size(importCheckData.getChangedProducts()));
        importStat.setChangedJobTypeCount(size(importCheckData.getChangedJobTypes()));
        importStat.setChangedJobSubTypeCount(size(importCheckData.getChangedJobSubTypes()));
        importStat.setChangedCostCount(size(importCheckData.getChangedCosts()));

        importStat.setRemovedProductCount(size(importCheckData.getRemovedProducts()));
        importStat.setRemovedJobTypeCount(size(importCheckData.getRemovedJobTypes()));
        importStat.setRemovedJobSubTypeCount(size(importCheckData.getRemovedJobSubTypes()));
        importStat.setRemovedCostCount(size(importCheckData.getRemovedCosts()));
        return importStat;
    }

    private void clearDatabase() {
        em.createQuery("delete from JobSubTypeCost").executeUpdate();
        em.createQuery("delete from Product").executeUpdate();
        em.createQuery("delete from JobSubType").executeUpdate();
        em.createQuery("delete from JobType").executeUpdate();
    }

    private int size(List list) {
        return list != null ? list.size() : 0;
    }

    @Override
    public ImportProgress getProgress() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
