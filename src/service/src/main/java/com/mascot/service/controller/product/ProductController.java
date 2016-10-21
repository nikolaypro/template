package com.mascot.service.controller.product;

import com.mascot.server.beans.ProductService;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Product;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolay on 19.10.2016.
 */
@RestController
@RequestMapping(value = "/products")
public class ProductController extends AbstractController {
    @Inject
    private ProductService productService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public TableResult<ProductRecord> getList(@RequestBody TableParams params) {
        final BeanTableResult<Product> beanTableResult = productService.getList(params.getStartIndex(), params.count, params.orderBy);
        final Collection<Product> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<ProductRecord> result = list.stream().
                map(ProductRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new ProductRecord[result.size()]), totalCount);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord update(@RequestBody ProductRecord record) {
        logger.info("Product: name = " + record.name + ", id = " + record.id);
        final Product entity;
        if (record.id == null) {
            final Product existsProduct = productService.findByName(record.name);
            if (existsProduct != null) {
                return ResultRecord.failLocalized("product.name.already.exists", record.name);
            }
            entity = new Product();
        } else {
            final Product existsEntity = productService.find(record.id);
            if (existsEntity == null) {
                return ResultRecord.fail("Unable find product with id = " + record.id);
            }
            final Product existsNameEntity = productService.findByName(record.name);
            if (existsNameEntity != null && !existsNameEntity.getId().equals(existsEntity.getId())) {
                return ResultRecord.failLocalized("product.name.already.exists", record.name);
            }
            entity = existsEntity;
        }
        entity.setName(record.name);

        productService.update(entity);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord delete(@RequestBody Long[] ids) {
/*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        if (ids == null) {
            return ResultRecord.success();
        }
        logger.info("Delete products size: " + ids.length);
        Stream.of(ids).forEach(id -> {
                    logger.info("Delete product: " + id);
                    try {
                        if (!productService.remove(id)) {
                            logger.warn("Unable delete product: " + id);
                        }
                    } catch (Exception e) {
                        logger.error("Unable delete product: " + id, e);
                    }
                }
        );
        return ResultRecord.success();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public ProductRecord get(@PathVariable("id") Long id) {
        logger.info("Product: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("Product id: '" + userService.getCurrentUserId()+ "'");
        final Product entity = productService.find(id);
        if (entity == null) {
            logger.warn("Product with id = '" + id + "' not found");
            throw new IllegalStateException("Unable find product. May be it was deleted");
        }
        return ProductRecord.build(entity);
    }


}
