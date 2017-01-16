package com.mascot.server.beans.importdata;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.AbstractMascotService;
import com.mascot.server.model.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Николай on 02.12.2016.
 */
@Service(Import1cService.NAME)
@Scope("singleton")
@Transactional(propagation = Propagation.REQUIRED)
public class Import1cServiceImpl extends AbstractMascotService implements Import1cService {
    @PersistenceContext(unitName = "templateMSPersistenceUnit")
    private EntityManager msSqlEm;

    private ImportProgress progress;

    private int checkDataMaxPercent;

    @Override
    public ImportCheckData checkImport() {
        progress = new ImportProgress();
        checkDataMaxPercent = 100;
        return doCheckData();
    }

    private ImportCheckData doCheckData() {

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

        final Map<String, Product> ownDbExistingProducts = createMap(em.createQuery("select e from Product e").getResultList());
        final List<Product> changedProducts = new ArrayList<>();

        final Map<String, JobType> ownDbExistingJobTypes = createMap(em.createQuery("select e from JobType e").getResultList());

        Map<String, Product> productsMap = new HashMap<>();
        Map<String, JobType> jobTypesMap = new HashMap<>();
        Map<String, JobSubType> jobSubTypesMap = new HashMap<>();
        Map<String, JobSubTypeCost> costsMap = new HashMap<>();

        progress.setState("Load job types and job subtypes");
        progress.setPercent(1);
        List<Object[]> resultList = msSqlEm.createNativeQuery("select distinct " +
                "         convert(varchar(max), jobType._IDRRef, 2) as jobType_id, jobType._Code as jobTypeCode, jobType._Description jobType, " +
                "         convert(varchar(max), jobSubType._IDRRef, 2) as jobSubType_id, jobSubType._Code as jobSubTypeCode, jobSubType._Description jobSubType " +
                "         FROM _Reference6995 jobSubType " +
                "         INNER JOIN _Reference6995 jobType ON jobType._IDRRef = jobSubType._ParentIDRRef "
                /*"         order by jobType._IDRRef"*/).getResultList();

        doSleep(2);
        progress.setPercent(25 * checkDataMaxPercent / 100);
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
        doSleep(2);
        progress.set("Load products and job costs", 30 * checkDataMaxPercent / 100);

        resultList = msSqlEm.createNativeQuery("SELECT distinct " +
                "         convert(varchar(max), product._IDRRef, 2) as product_id, product._Code as productCode, product._Description as product," +
                "         convert(varchar(max), subType._IDRRef, 2) as subType_id,  subType._Code as subTypeCode, subType._Description as subType," +
                "         cost._Fld7004 cost" +
                "         FROM _Reference6995_VT7001 as cost" +
                "         INNER JOIN _Reference46 product ON cost._Fld7003RRef = product._IDRRef" +
                "         INNER JOIN _Reference6995 subType ON cost._Reference6995_IDRRef = subType._IDRRef").getResultList();

        progress.setPercent(60 * checkDataMaxPercent / 100);
        doSleep(2);

        for (Object[] objects : resultList) {
            String productId = (String) objects[0];
            String productCode = (String) objects[1];
            String productDescription = (String) objects[2];

            String jobSubTypeId = (String) objects[3];
            String jobSubTypeCode = (String) objects[4];
            String jobSubTypeDescription = (String) objects[5];
            BigDecimal cost = (BigDecimal) objects[6];

            Product product = productsMap.get(productId);
            if (product == null) {
                product = ownDbExistingProducts.get(productId);
                if (product == null) {
                    product = new Product();
                } else {
                    if (productModified(product, productDescription)) {
                        changedProducts.add(product);
                    }
                }
                productsMap.put(productId, product);
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
            jobSubTypeCost.setCost(cost.doubleValue());
        }

        final List<JobType> newJobTypes = new ArrayList<>(jobTypesMap.values());
        newJobTypes.sort((e1, e2) -> e1.getExternalCode().compareTo(e2.getExternalCode()));
        int order = 0;
        for (JobType newJobType : newJobTypes) {
            newJobType.setOrder(order++);
        }

        ImportCheckData data = new ImportCheckData();
        data.setNewProducts(getNew(productsMap.values()));
        data.setRemovedProducts(getRemoved(ownDbExistingProducts, productsMap));
        data.setChangedProducts(changedProducts);

        data.setNewJobTypes(newJobTypes);
        data.setNewJobSubTypes(new ArrayList<>(jobSubTypesMap.values()));
        data.setNewCosts(new ArrayList<>(costsMap.values()));

        doSleep(2);
        progress.setPercent(100 * checkDataMaxPercent / 100);

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

    private boolean productModified(Product product, String productDescription) {
        return !MascotUtils.equalsOrBothEmpty(productDescription, product.getName());
    }

    private <A extends ExternalEntity> List<A> getRemoved(Map<String, A> ownDbExisting, Map<String, A> imported) {
        return ownDbExisting.entrySet().stream().filter(e -> !imported.containsKey(e.getKey())).map(e -> e.getValue()).collect(Collectors.toList());
    }

    private <A extends ExternalEntity> List<A> getNew(Collection<A> values) {
        return values.stream().filter(e -> e.getId() == null).collect(Collectors.toList());
    }

    private <A extends ExternalEntity> Map<String, A> createMap(List<A> list) {
        return list.stream().collect(Collectors.toMap(ExternalEntity::getExternalId, e -> e));
    }

    private void doSleep(int i) {
/*
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public ImportStat doImport() {
        progress = new ImportProgress();
        checkDataMaxPercent = 50;
        final ImportCheckData importCheckData = doCheckData();

        progress.setState("Clear database");
        clearDatabase();
        int allCount = getAllCount(importCheckData);


        progress.set("New product process", 60);
        importCheckData.getNewProducts().forEach(em::persist);
        final int rem = 100 - progress.getPercent();
        progress.inc("New job types process", rem * importCheckData.getNewProducts().size() / allCount);
        importCheckData.getNewJobTypes().forEach(em::persist);
        progress.inc("New job sub types process", rem * importCheckData.getNewJobTypes().size() / allCount);
        importCheckData.getNewJobSubTypes().forEach(em::persist);
        progress.inc("New costs process", rem * importCheckData.getNewJobSubTypes().size() / allCount);
        importCheckData.getNewCosts().forEach(em::persist);

        progress.inc("Changed products process", rem * importCheckData.getNewCosts().size() / allCount);
        importCheckData.getChangedProducts().forEach(em::merge);
        progress.inc("Changed job type process", rem * importCheckData.getChangedProducts().size() / allCount);
        importCheckData.getChangedJobTypes().forEach(em::merge);
        progress.inc("Changed job sub type process", rem * importCheckData.getChangedJobTypes().size() / allCount);
        importCheckData.getChangedJobSubTypes().forEach(em::merge);
        progress.inc("Changed costs process", rem * importCheckData.getChangedJobSubTypes().size() / allCount);
        importCheckData.getChangedCosts().forEach(em::merge);

        progress.inc("Removed products process", rem * importCheckData.getChangedCosts().size() / allCount);
        importCheckData.getRemovedProducts().forEach(e -> {
            e.setDeleted(true);
            em.merge(e);
        });
        progress.inc("Removed job types process", rem * importCheckData.getRemovedProducts().size() / allCount);
        importCheckData.getRemovedJobTypes().forEach(e -> {
            e.setDeleted(true);
            em.merge(e);
        });
        progress.inc("Removed job sub types process", rem * importCheckData.getRemovedJobTypes().size() / allCount);
        importCheckData.getRemovedJobSubTypes().forEach(e -> {
            e.setDeleted(true);
            em.merge(e);
        });
        progress.inc("Removed costs process", rem * importCheckData.getRemovedJobSubTypes().size() / allCount);
        importCheckData.getRemovedCosts().forEach(e -> {
            e.setDeleted(true);
            em.merge(e);
        });

        progress.setPercent(100);


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

    private int getAllCount(ImportCheckData importCheckData) {
        return importCheckData.getNewProducts().size() +
        importCheckData.getNewJobTypes().size() +
        importCheckData.getNewJobSubTypes().size() +
        importCheckData.getNewCosts().size() +

        importCheckData.getChangedProducts().size() +
        importCheckData.getChangedJobTypes().size() +
        importCheckData.getChangedJobSubTypes().size() +
        importCheckData.getChangedCosts().size() +

        importCheckData.getRemovedProducts().size() +
        importCheckData.getRemovedJobTypes().size() +
        importCheckData.getRemovedJobSubTypes().size() +
        importCheckData.getRemovedCosts().size();
    }

    private void clearDatabase() {
        em.createQuery("delete from Job").executeUpdate();
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
        return progress;
    }
}
