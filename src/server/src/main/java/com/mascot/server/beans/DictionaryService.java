package com.mascot.server.beans;

import com.mascot.server.model.Cloth;
import com.mascot.server.model.Product;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Николай on 24.02.2017.
 */
public interface DictionaryService {
    String NAME = "DictionaryService";

    List<Product> getProducts();

    List<Cloth> getMainClothes(Long productId);

    List<Cloth> getCompClothes1(Long productId);

    List<Cloth> getCompClothes2(Long productId);

    Product findProduct(Long productId);

    Cloth findCloth(Long clothId);
}
