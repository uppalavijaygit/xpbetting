package com.xp.betting.app.statagy;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class PlayerGameMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
    private static final String[] HEADER = new String[]{"PLAYER_ID", "GAME_NAME", "AMOUNT_WAGERED", "AMOUNT_WON"};

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        return HEADER;
    }
}