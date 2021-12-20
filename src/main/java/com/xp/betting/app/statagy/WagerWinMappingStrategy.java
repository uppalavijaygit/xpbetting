package com.xp.betting.app.statagy;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class WagerWinMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
    private static final String[] HEADER = new String[]{"PLAYER_ID", "SESSION_ID", "AMOUNT_WAGERED", "AMOUNT_WON"};

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        return HEADER;
    }
}