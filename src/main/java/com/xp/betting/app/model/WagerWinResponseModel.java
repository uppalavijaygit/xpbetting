package com.xp.betting.app.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WagerWinResponseModel {
    @CsvBindByName(column = "PLAYER_ID")
    @CsvBindByPosition(position = 0)
    private Integer playerId;

    @CsvBindByName(column = "SESSION_ID")
    @CsvBindByPosition(position = 1)
    private Integer sessionId;

    @CsvBindByName(column = "AMOUNT_WAGERED")
    @CsvBindByPosition(position = 2)
    private double amountWagered;

    @CsvBindByName(column = "AMOUNT_WON")
    @CsvBindByPosition(position = 3)
    private double amountWon;
}
