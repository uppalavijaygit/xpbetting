package com.xp.betting.app.processor;

import com.xp.betting.app.model.WagerWinResponseModel;
import com.xp.betting.app.model.XPBettingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WageredWonProcessorTest {

    private WageredWonProcessor playerGameProcessor = new WageredWonProcessor();
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

        csvData.add(XPBettingModel.builder()
                .playerId(18)
                .amount(20)
                .sessionId(2)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());

        csvData.add(XPBettingModel.builder()
                .playerId(17)
                .amount(20)
                .sessionId(2)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());

        csvData.add(XPBettingModel.builder()
                .playerId(16)
                .amount(20)
                .sessionId(2)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());
    }

    @Test
    void processData() {
        Map<Integer, Map<Integer, Map<Boolean, Double>>> processData = playerGameProcessor.processData(csvData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertTrue(processData.containsKey(12)),
                ()-> assertEquals(4,processData.size()),
                ()-> assertFalse(processData.containsKey(123456)),
                ()-> assertTrue(processData.get(12).containsKey(2)),
                ()-> assertNull(processData.get(12).get(2).get(true)),
                ()-> assertEquals(30,processData.get(12).get(2).get(false))
        );
    }

    @Test
    void mapGroupedDataModel() {
        Map<Integer, Map<Integer, Map<Boolean, Double>>> processData = playerGameProcessor.processData(csvData);
        List<WagerWinResponseModel> playerGameResponseModels = playerGameProcessor.mapGroupedDataModel(processData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertFalse(playerGameResponseModels == null),
                ()-> assertEquals(5, playerGameResponseModels.size()),
                ()-> assertEquals(20.0, playerGameResponseModels.get(0).getAmountWagered()),
                ()-> assertEquals(0.0, playerGameResponseModels.get(0).getAmountWon())
        );
    }
}