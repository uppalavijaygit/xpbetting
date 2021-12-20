package com.xp.betting.app.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XPBettingModel {
    @CsvBindByName(column = "PLAYER_ID")
    private Integer playerId;

    @CsvBindByName(column = "AMOUNT")
    private double amount;

    @CsvBindByName(column = "CHAPTER")
    @CsvIgnore(profiles = {"End session","Loyalty"})
    private String chapter;

    @CsvBindByName(column = "PARTNER_ID")
    private Integer partnerId;

    @CsvBindByName(column = "GAME_NAME")
    private String gameName;

    @CsvBindByName(column = "SESSION_ID")
    private Integer sessionId;

}
