package com.xp.betting.app.statagy;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class TopPlayersMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
    private static final String[] HEADER = new String[]{"PLAYER_ID", "PROFIT_TO_COMPANY"};

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        return HEADER;
    }
}