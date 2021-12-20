package com.xp.betting.app.processor;

import com.xp.betting.app.model.XPBettingModel;
import com.xp.betting.app.model.WagerWinResponseModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class WageredWonProcessor {

    /**
     * This function will help to process the Csv data and group it by Player ID, Session ID and separate the amounts by Bet and Win
     *
     * @param csvData, Will consume the list of Csv data in the form of List<XPBettingModel> which is prepared after reading the csv file
     * @return will return back with the grouped Data
     */
    public Map<Integer, Map<Integer, Map<Boolean, Double>>> processData(List<XPBettingModel> csvData) {
        Map<Integer, Map<Integer, Map<Boolean, Double>>> groupedCsvData = csvData.stream()
                .collect(
                        groupingBy(
                                XPBettingModel::getPlayerId,
                                groupingBy(
                                        XPBettingModel::getSessionId,
                                        groupingBy(
                                                i -> i.getChapter().equalsIgnoreCase("WIN"), summingDouble(XPBettingModel::getAmount)
                                        )
                                )));
        return groupedCsvData;
    }

    /**
     * This Function will process the grouped Data and prepare the response model for output CSV
     * @param groupedCsvData It accepts the grouped data and process to response model
     * @return Collection of WagerWinResponseModel
     */
    public List<WagerWinResponseModel> mapGroupedDataModel(Map<Integer, Map<Integer, Map<Boolean, Double>>> groupedCsvData) {
        Set<Map.Entry<Integer, Map<Integer, Map<Boolean, Double>>>> entries = groupedCsvData.entrySet();
        List<WagerWinResponseModel> responseList = new ArrayList<>();
        WagerWinResponseModel responseModel = null;
        for (Map.Entry<Integer, Map<Integer, Map<Boolean, Double>>> entry : entries) {
            for (Map.Entry<Integer, Map<Boolean, Double>> entry2 : entry.getValue().entrySet()) {
                responseModel = new WagerWinResponseModel();
                responseModel.setPlayerId(entry.getKey());
                responseModel.setSessionId(entry2.getKey());
                responseModel.setAmountWon(entry2.getValue().get(true) == null ? 0.0 : Math.round(entry2.getValue().get(true)));
                responseModel.setAmountWagered(entry2.getValue().get(false) == null ? 0.0 : Math.round(entry2.getValue().get(false)));
                responseList.add(responseModel);
            }
        }
        return responseList;
    }
}

