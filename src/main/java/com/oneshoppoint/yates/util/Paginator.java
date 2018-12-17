package com.oneshoppoint.yates.util;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public class Paginator<T> {
    public Pagination<T> paginate (Integer pageNo,List<T> tList,Integer parts) {
        Integer pages = (int)Math.ceil((double)tList.size()/parts);
        Integer prev = pageNo;
        Integer next = pageNo;

        if(pageNo > pages ) {
            pageNo = pages;
        }

        Integer lastRecord = pageNo * parts;
        Integer record = lastRecord - parts;

        if(lastRecord >= tList.size()) {
            lastRecord = tList.size();
            next = null;
        } else {
            next++;
        }

        if(record <= 0) {
            record = 0;
            prev = null;
        } else {
            prev--;
        }
        tList = tList.subList(record,lastRecord);
        return new Pagination<T>(tList,next,prev);

    }
}
