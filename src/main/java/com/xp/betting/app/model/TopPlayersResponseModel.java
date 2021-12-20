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
public class TopPlayersResponseModel {
    @CsvBindByName(column = "PLAYER_ID")
    @CsvBindByPosition(position = 0)
    private Integer playerId;

    @CsvBindByName(column = "PROFIT_TO_COMPANY")
    @CsvBindByPosition(position = 1)
    private Double profitToCompany;
}
