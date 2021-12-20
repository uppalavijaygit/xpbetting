package com.xp.betting.app.processor;

import com.xp.betting.app.model.SessionWagerWinResponseModel;
import com.xp.betting.app.model.WagerWinResponseModel;
import com.xp.betting.app.model.XPBettingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SessionGameWageredWonProcessorTest {

    private SessionGameWageredWonProcessor playerGameProcessor = new SessionGameWageredWonProcessor();
    List<XPBettingModel> csvData = new ArrayList<>();

    @BeforeEach
    void prepareData(){
        csvData.add(XPBettingModel.builder()
                .playerId(12)
                .amount(10)
                .sessionId(2)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());
        csvData.add(XPBettingModel.builder()
                .playerId(12)
                .amount(20)
                .sessionId(1)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());

        csvData.add(XPBettingModel.builder()
                .playerId(12)
                .amount(100)
                .sessionId(1)
                .chapter("WIN")
                .gameName("Poker")
                .partnerId(99)
                .build());

        csvData.add(XPBettingModel.builder()
                .playerId(12)
                .amount(20)
                .sessionId(2)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());
    }

    @Test
    void processData() {
        Map<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> processData = playerGameProcessor.processData(csvData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertTrue(processData.containsKey(12)),
                ()-> assertEquals(1,processData.size()),
                ()-> assertFalse(processData.containsKey(123456)),
                ()-> assertTrue(processData.get(12).containsKey(2)),
                ()-> assertNull(processData.get(12).get(2).get("Poker").get(true)),
                ()-> assertEquals(30,processData.get(12).get(2).get("Poker").get(false))
        );
    }

    @Test
    void mapGroupedDataModel() {
        Map<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> processData = playerGameProcessor.processData(csvData);
        List<SessionWagerWinResponseModel> sessionWagerWinResponseModels = playerGameProcessor.mapGroupedDataModel(processData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertFalse(sessionWagerWinResponseModels == null),
                ()-> assertEquals(2, sessionWagerWinResponseModels.size()),
                ()-> assertEquals(20.0, sessionWagerWinResponseModels.get(0).getAmountWagered()),
                ()-> assertEquals(100, sessionWagerWinResponseModels.get(0).getAmountWon())
        );
    }
}