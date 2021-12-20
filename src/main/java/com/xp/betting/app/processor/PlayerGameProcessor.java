package com.xp.betting.app.processor;

import com.xp.betting.app.model.PlayerGameResponseModel;
import com.xp.betting.app.model.XPBettingModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class PlayerGameProcessor {

    /**
     * This function will help to process the Csv data and group it by Player ID, Game Name and separate the amounts by Bet and Win
     *
     * @param csvData, Will consume the list of Csv data in the form of List<XPBettingModel> which is prepared after reading the csv file
     * @return will return back with the grouped Data
     */
    public Map<Integer, Map<String, Map<Boolean, Double>>> processData(List<XPBettingModel> csvData) {
        Map<Integer, Map<String, Map<Boolean, Double>>> groupedCsvData = csvData.stream()
                .collect(
                        groupingBy(
                                XPBettingModel::getPlayerId,
                                groupingBy(
                                        XPBettingModel::getGameName,
                                        groupingBy(
                                                i -> i.getChapter().equalsIgnoreCase("WIN"),
                                                summingDouble(XPBettingModel::getAmount)
                                        )
                                )));
        return groupedCsvData;
    }

    /**
     * This Function will process the grouped Data and prepare the response model for output CSV
     * @param groupedCsvData It accepts the grouped data and process to response model
     * @return Collection of PlayerGameResponseModel
     */
    public List<PlayerGameResponseModel> mapGroupedDataModel(Map<Integer, Map<String, Map<Boolean, Double>>> groupedCsvData) {
        Set<Map.Entry<Integer, Map<String, Map<Boolean, Double>>>> entries = groupedCsvData.entrySet();
        List<PlayerGameResponseModel> responseList = new ArrayList<>();
        PlayerGameResponseModel responseModel = null;
        for (Map.Entry<Integer, Map<String, Map<Boolean, Double>>> entry : entries) {
            for (Map.Entry<String, Map<Boolean, Double>> entry2 : entry.getValue().entrySet()) {
                responseModel = new PlayerGameResponseModel();
                responseModel.setPlayerId(entry.getKey());
                responseModel.setGameName(entry2.getKey());
                responseModel.setAmountWon(entry2.getValue().get(true) == null ? 0.0 : Math.round(entry2.getValue().get(true)));
                responseModel.setAmountWagered(entry2.getValue().get(false) == null ? 0.0 : Math.round(entry2.getValue().get(false)));
                responseList.add(responseModel);
            }
        }
        return responseList;
    }
}

