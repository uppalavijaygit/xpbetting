package com.xp.betting.app.processor;

import com.xp.betting.app.model.SessionWagerWinResponseModel;
import com.xp.betting.app.model.XPBettingModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class SessionGameWageredWonProcessor {

    /**
     * This function will help to process the Csv data and group it by Player ID, Session ID, Game Name and separate the amounts by Bet and Win
     *
     * @param csvData, Will consume the list of Csv data in the form of List<XPBettingModel> which is prepared after reading the csv file
     * @return will return back with the grouped Data
     */
    public Map<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> processData(List<XPBettingModel> csvData) {
        Map<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> groupedCsvData = csvData.stream()
                .collect(
                        groupingBy(
                                XPBettingModel::getPlayerId,
                                groupingBy(
                                        XPBettingModel::getSessionId,
                                        groupingBy(XPBettingModel::getGameName, groupingBy(
                                                i -> i.getChapter().equalsIgnoreCase("WIN"),
                                                summingDouble(XPBettingModel::getAmount)
                                        ))

                                )));
        return groupedCsvData;
    }

    /**
     * This Function will process the grouped Data and prepare the response model for output CSV
     * @param groupedCsvData It accepts the grouped data and process to response model
     * @return Collection of SessionWagerWinResponseModel
     */
    public List<SessionWagerWinResponseModel> mapGroupedDataModel(Map<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> groupedCsvData) {
        Set<Map.Entry<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>>> entries = groupedCsvData.entrySet();
        List<SessionWagerWinResponseModel> responseList = new ArrayList<>();
        SessionWagerWinResponseModel responseModel = null;
        for (Map.Entry<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> entry : entries) {
            for (Map.Entry<Integer, Map<String, Map<Boolean, Double>>> entry2 : entry.getValue().entrySet()) {
                for (Map.Entry<String, Map<Boolean, Double>> entry3 : entry2.getValue().entrySet()) {
                    responseModel = new SessionWagerWinResponseModel();
                    responseModel.setPlayerId(entry.getKey());
                    responseModel.setSessionId(entry2.getKey());
                    responseModel.setGameName(entry3.getKey());
                    responseModel.setAmountWon(entry3.getValue().get(true) == null ? 0.0 : Math.round(entry3.getValue().get(true)));
                    responseModel.setAmountWagered(entry3.getValue().get(false) == null ? 0.0 : Math.round(entry3.getValue().get(false)));
                    responseList.add(responseModel);
                }

            }
        }
        return responseList;
    }
}

