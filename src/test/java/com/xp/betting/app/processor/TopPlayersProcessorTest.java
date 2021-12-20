package com.xp.betting.app.processor;

import com.xp.betting.app.model.TopPlayersResponseModel;
import com.xp.betting.app.model.XPBettingModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TopPlayersProcessorTest {

    private static TopPlayersProcessor topPlayersProcessor = new TopPlayersProcessor();
    static List<XPBettingModel> csvData = new ArrayList<>();
    static Map<Integer, Map<Boolean, Double>> processData = null;

    @BeforeAll
    static void prepareData(){
        csvData.add(XPBettingModel.builder()
                .playerId(11)
                .amount(10)
                .sessionId(2)
                .chapter("BET")
                .gameName("Poker")
                .partnerId(99)
                .build());
        csvData.add(XPBettingModel.builder()
                .playerId(13)
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

        processData = topPlayersProcessor.processData(csvData);
    }

    @Test
    void processData() {
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertTrue(processData.containsKey(12)),
                ()-> assertEquals(6,processData.size()),
                ()-> assertFalse(processData.containsKey(123456)),
                ()-> assertEquals(100,processData.get(12).get(true)),
                ()-> assertEquals(20,processData.get(12).get(false))
        );
    }

    @Test
    void mapGroupedDataModel() {
        Map<Integer, Map<Boolean, Double>> processData = topPlayersProcessor.processData(csvData);
        List<TopPlayersResponseModel> playerGameResponseModels = topPlayersProcessor.mapGroupedDataModel(processData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertFalse(playerGameResponseModels == null),
                ()-> assertEquals(5, playerGameResponseModels.size()),
                ()-> assertEquals(16, playerGameResponseModels.get(0).getPlayerId())
        );
    }
}