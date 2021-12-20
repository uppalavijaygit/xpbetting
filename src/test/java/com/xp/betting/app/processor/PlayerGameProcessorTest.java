package com.xp.betting.app.processor;

import com.xp.betting.app.model.PlayerGameResponseModel;
import com.xp.betting.app.model.XPBettingModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerGameProcessorTest {

    private List<XPBettingModel> csvData = new ArrayList<>();
    private PlayerGameProcessor playerGameProcessor = new PlayerGameProcessor();
    @BeforeEach
    void prepareData(){
        csvData.add(XPBettingModel.builder()
                .playerId(14345194)
                .gameName("Poker")
                .amount(14)
                .sessionId(46628)
                .chapter("Win")
                .build());
    }

    @Test
    void processData() {
        Map<Integer, Map<String, Map<Boolean, Double>>> processData = playerGameProcessor.processData(csvData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertTrue(processData.containsKey(14345194)),
                ()-> assertEquals(1,processData.size()),
                ()-> assertFalse(processData.containsKey(123456)),
                ()-> assertTrue(processData.get(14345194).containsKey("Poker")),
                ()-> assertEquals(14,processData.get(14345194).get("Poker").get(true)),
                ()-> assertNull(processData.get(14345194).get("Poker").get(false))
        );
    }

    @Test
    void mapGroupedDataModel() {
        Map<Integer, Map<String, Map<Boolean, Double>>> processData = playerGameProcessor.processData(csvData);
        List<PlayerGameResponseModel> playerGameResponseModels = playerGameProcessor.mapGroupedDataModel(processData);
        assertAll("Should Return the Map with Grouped Data",
                ()-> assertFalse(playerGameResponseModels == null),
                ()-> assertEquals(1, playerGameResponseModels.size()),
                ()-> assertEquals(0.0, playerGameResponseModels.get(0).getAmountWagered()),
                ()-> assertEquals(14, playerGameResponseModels.get(0).getAmountWon())
                );
    }
}