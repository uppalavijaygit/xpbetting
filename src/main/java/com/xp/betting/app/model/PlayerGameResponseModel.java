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
public class PlayerGameResponseModel {
    @CsvBindByName(column = "PLAYER_ID")
    @CsvBindByPosition(position = 0)
    private Integer playerId;

    @CsvBindByName(column = "GAME_NAME")
    @CsvBindByPosition(position = 1)
    private String gameName;

    @CsvBindByName(column = "AMOUNT_WAGERED")
    @CsvBindByPosition(position = 2)
    private double amountWagered;

    @CsvBindByName(column = "AMOUNT_WON")
    @CsvBindByPosition(position = 3)
    private double amountWon;
}
