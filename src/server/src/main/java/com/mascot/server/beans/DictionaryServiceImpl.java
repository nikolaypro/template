package com.mascot.server.beans;

import com.mascot.server.model.Cloth;
import com.mascot.server.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Николай on 24.02.2017.
 */
@Service(DictionaryService.NAME)
public class DictionaryServiceImpl extends AbstractMascotService implements DictionaryService {

    @Override
    public List<Product> getProducts() {
        return em.createQuery("select e from Product e where e.deleted <> :deleted").setParameter("deleted", true).getResultList();
    }

    @Override
    public List<Cloth> getMainClothes(Long productId) {
        return em.createQuery("select e from Cloth e where e.deleted <> :deleted").setParameter("deleted", true).getResultList();
    }

    @Override
    public List<Cloth> getCompClothes1(Long productId) {
        return em.createQuery("select e from Cloth e where e.deleted <> :deleted").setParameter("deleted", true).getResultList();
    }

    @Override
    public List<Cloth> getCompClothes2(Long productId) {
        return em.createQuery("select e from Cloth e where e.deleted <> :deleted").setParameter("deleted", true).getResultList();
    }
}
