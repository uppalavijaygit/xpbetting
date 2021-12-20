package com.xp.betting.app.processor;

import com.xp.betting.app.model.TopPlayersResponseModel;
import com.xp.betting.app.model.XPBettingModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class TopPlayersProcessor {

    /**
     * This function will help to process the Csv data and group it by Player ID, Amount Spent and separate the amounts by Bet and Win
     *
     * @param csvData, Will consume the list of Csv data in the form of List<XPBettingModel> which is prepared after reading the csv file
     * @return will return back with the grouped Data
     */
    public Map<Integer, Map<Boolean, Double>> processData(List<XPBettingModel> csvData) {

        Map<Integer, Map<Boolean, Double>> groupedCsvData = csvData.stream()
                .collect(
                        groupingBy(
                                XPBettingModel::getPlayerId,
                                groupingBy(
                                        i -> i.getChapter().equalsIgnoreCase("WIN"),
                                        summingDouble(XPBettingModel::getAmount)
                                )
                        ));
        return groupedCsvData;
    }

    /**
     * This Function will process the grouped Data and prepare the response model for output CSV
     * @param groupedCsvData It accepts the grouped data and process to response model
     * @return Collection of TopPlayersResponseModel
     */
    public List<TopPlayersResponseModel> mapGroupedDataModel(Map<Integer, Map<Boolean, Double>> groupedCsvData) {
        Set<Map.Entry<Integer, Map<Boolean, Double>>> entries = groupedCsvData.entrySet();
        Map<Integer, Double> playerWithProfits = new HashMap<>();
        entries.stream().forEach(i -> {
            Double winningAmount = i.getValue().get(true) == null ? 0.0 : i.getValue().get(true);
            Double bettingAmount = i.getValue().get(false) == null ? 0.0 : i.getValue().get(false);
            Double totalBetAmountAfterWin = bettingAmount - winningAmount;
            playerWithProfits.put(i.getKey(), totalBetAmountAfterWin);
        });
        return getTops(playerWithProfits);
    }

    /**
     * This function will list the TopPlayers from the final Result
     * @param playerWithProfits Will accept the segregated data
     * @return will return the list of Top 5 Players
     */
    private List<TopPlayersResponseModel> getTops(Map<Integer, Double> playerWithProfits) {
        List<TopPlayersResponseModel> listOfTopPlayers = playerWithProfits.entrySet().stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .limit(5)
                .map(i -> {
                    return TopPlayersResponseModel.builder()
                            .playerId(i.getKey())
                            .profitToCompany(i.getValue())
                            .build();
                })
                .collect(toList());
        return listOfTopPlayers;
    }
}

