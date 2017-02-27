package com.mascot.service.controller.order.product;

import com.mascot.server.model.OrderProductLine;
import com.mascot.service.controller.common.DictionaryRecord;
import com.mascot.service.controller.order.OrderLineBaseRecord;

/**
 * Created by Николай on 24.02.2017.
 */
public class OrderProductLineRecord extends OrderLineBaseRecord {
    public DictionaryRecord product;
    public DictionaryRecord mainCloth;
    public DictionaryRecord compCloth1;
    public DictionaryRecord compCloth2;
    public String stitchingType;

    public static OrderProductLineRecord build(OrderProductLine line) {
        OrderProductLineRecord result = new OrderProductLineRecord();
        result.fill(line);
        result.product = new DictionaryRecord(line.getProduct());
        result.mainCloth = new DictionaryRecord(line.getMainCloth());
        result.compCloth1 = new DictionaryRecord(line.getCompCloth1());
        result.compCloth2 = new DictionaryRecord(line.getCompCloth2());
        result.stitchingType = line.getStitchingType().name();
        return result;
    }

}
