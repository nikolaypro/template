package com.mascot.service.controller.order.cloth;

import com.mascot.server.model.OrderClothLine;
import com.mascot.service.controller.common.DictionaryRecord;
import com.mascot.service.controller.order.OrderLineBaseRecord;

/**
 * Created by Николай on 24.02.2017.
 */
public class OrderClothLineRecord extends OrderLineBaseRecord {
    public DictionaryRecord cloth;

    public static OrderClothLineRecord build(OrderClothLine line) {
        OrderClothLineRecord result = new OrderClothLineRecord();
        result.fill(line);
        result.cloth = new DictionaryRecord(line.getCloth());
        return result;
    }



}
