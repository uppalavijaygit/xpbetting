package com.xp.betting.app.file.generator;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.xp.betting.app.model.*;
import com.xp.betting.app.processor.PlayerGameProcessor;
import com.xp.betting.app.processor.SessionGameWageredWonProcessor;
import com.xp.betting.app.processor.TopPlayersProcessor;
import com.xp.betting.app.processor.WageredWonProcessor;
import com.xp.betting.app.reader.XPBettingReader;
import com.xp.betting.app.writer.XPBettingWriter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WageredWinFileGenerator {

    // This is main function will call and generate all requested files
    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        // Reading the CSV file
        List<XPBettingModel> xpBettingModels = new XPBettingReader().readCsv();

        // Call to Generate WagerWin File
        generateWagerWinFile(xpBettingModels);

        // Call to Generate Player Games File
        generatePlayerGameFile(xpBettingModels);

        // Call to Generate Session Amounts File
        generateSessionWagerWinFile(xpBettingModels);

        // Call to Generate Top five Player File
        generateTopFivePlayersFile(xpBettingModels);

    }

    /**
     * This function will be generating top five player who are most profitable to the company
     * @param xpBettingModels: This function consume the List of betting model after reading the content from the given CSV
     */
    @SneakyThrows
    private static void generateTopFivePlayersFile(List<XPBettingModel> xpBettingModels) {
        Map<Integer, Map<Boolean, Double>> integerMapMap = new TopPlayersProcessor().processData(xpBettingModels);
        List<TopPlayersResponseModel> topPlayersWithAmount = new TopPlayersProcessor().mapGroupedDataModel(integerMapMap);
        new XPBettingWriter().writeTopPlayersCsvFile(topPlayersWithAmount);

    }
    /**
     * This function will be generating Players Games Amounts file
     * @param xpBettingModels: This function consume the List of betting model after reading the content from the given CSV
     */
    private static void generatePlayerGameFile(List<XPBettingModel> xpBettingModels) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Map<Integer, Map<String, Map<Boolean, Double>>> integerMapMap = new PlayerGameProcessor().processData(xpBettingModels);
        List<PlayerGameResponseModel> wagerWinResponseModels = new PlayerGameProcessor().mapGroupedDataModel(integerMapMap);
        new XPBettingWriter().writePlayerGameCsvFile(wagerWinResponseModels);
    }

    /**
     * This function will be generating Wager and winning amounts of player
     * @param xpBettingModels: This function consume the List of betting model after reading the content from the given CSV
     */
    private static void generateWagerWinFile(List<XPBettingModel> xpBettingModels) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Map<Integer, Map<Integer, Map<Boolean, Double>>> integerMapMap = new WageredWonProcessor().processData(xpBettingModels);
        List<WagerWinResponseModel> wagerWinResponseModels = new WageredWonProcessor().mapGroupedDataModel(integerMapMap);
        new XPBettingWriter().writeWagerWinCsvFile(wagerWinResponseModels);
    }

    /**
     * This function will be generating Wager and winning amounts of player by session
     * @param xpBettingModels: This function consume the List of betting model after reading the content from the given CSV
     */
    private static void generateSessionWagerWinFile(List<XPBettingModel> xpBettingModels) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        Map<Integer, Map<Integer, Map<String, Map<Boolean, Double>>>> integerMapMap = new SessionGameWageredWonProcessor().processData(xpBettingModels);
        List<SessionWagerWinResponseModel> wagerWinResponseModels = new SessionGameWageredWonProcessor().mapGroupedDataModel(integerMapMap);
        new XPBettingWriter().writeSessionGameCsvFile(wagerWinResponseModels);
    }
}
