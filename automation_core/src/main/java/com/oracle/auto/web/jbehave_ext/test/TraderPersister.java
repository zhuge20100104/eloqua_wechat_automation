package com.oracle.auto.web.jbehave_ext.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhous5 on 2017/6/15.
 */
public class TraderPersister {
    private List<Trader> traders = new ArrayList<>();

    {
        traders.add(new Trader("Stephen", 21));
        traders.add(new Trader("Sandy", 19));
    }

    public Trader retrieveTrder(String name) {
        for (Trader trader : traders) {
            if (trader.getName().equalsIgnoreCase(name))
                return trader;
        }
        return null;
    }

}
