package com.xp.betting.app.writer;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.xp.betting.app.model.PlayerGameResponseModel;
import com.xp.betting.app.model.SessionWagerWinResponseModel;
import com.xp.betting.app.model.TopPlayersResponseModel;
import com.xp.betting.app.model.WagerWinResponseModel;
import com.xp.betting.app.statagy.SessionGameMappingStrategy;
import com.xp.betting.app.statagy.TopPlayersMappingStrategy;
import com.xp.betting.app.statagy.WagerWinMappingStrategy;
import com.xp.betting.app.statagy.PlayerGameMappingStrategy;
import com.xp.betting.app.utils.XPBettingConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class XPBettingWriter {

    /**
     * This function will take the Processed Collection of data and will write the csv file
     * @param wageredWonData
     * @throws IOException
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     */
    public void writeWagerWinCsvFile(List<WagerWinResponseModel> wageredWonData) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final WagerWinMappingStrategy<WagerWinResponseModel> wageredWonDataStrategy = new WagerWinMappingStrategy<>();
        wageredWonDataStrategy.setType(WagerWinResponseModel.class);
        wageredWonDataStrategy.setColumnMapping("PLAYER_ID", "SESSION_ID", "AMOUNT_WAGERED", "AMOUNT_WON");
        Writer writer = new FileWriter(XPBettingConstants.WAGER_WIN_FILE_NAME);
        StatefulBeanToCsv<WagerWinResponseModel> beanToCsv = new StatefulBeanToCsvBuilder<WagerWinResponseModel>(writer)
                .withMappingStrategy(wageredWonDataStrategy)
                .withSeparator(',')
                .build();
        beanToCsv.write(wageredWonData);
        writer.close();
    }

    /**
     * This function will take the Processed Collection of data and will write the csv file
     * @param wageredWonData
     * @throws IOException
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     */
    public void writePlayerGameCsvFile(List<PlayerGameResponseModel> wageredWonData) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final PlayerGameMappingStrategy<PlayerGameResponseModel> playerGameDataStrategy = new PlayerGameMappingStrategy<>();
        playerGameDataStrategy.setType(PlayerGameResponseModel.class);
        playerGameDataStrategy.setColumnMapping("PLAYER_ID", "GAME_NAME", "AMOUNT_WAGERED", "AMOUNT_WON");
        Writer writer = new FileWriter(XPBettingConstants.PLAYER_GAME_FILE_NAME);
        StatefulBeanToCsv<PlayerGameResponseModel> beanToCsv = new StatefulBeanToCsvBuilder<PlayerGameResponseModel>(writer)
                .withMappingStrategy(playerGameDataStrategy)
                .withSeparator(',')
                .build();
        beanToCsv.write(wageredWonData);
        writer.close();
    }


    /**
     * This function will take the Processed Collection of data and will write the csv file
     * @param topPlayers
     * @throws IOException
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     */
    public void writeTopPlayersCsvFile(List<TopPlayersResponseModel>  topPlayers) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final TopPlayersMappingStrategy<TopPlayersResponseModel> playerGameDataStrategy = new TopPlayersMappingStrategy<>();
        playerGameDataStrategy.setType(TopPlayersResponseModel.class);
        playerGameDataStrategy.setColumnMapping("PLAYER_ID", "PROFIT_TO_COMPANY");
        Writer writer = new FileWriter(XPBettingConstants.TOP_FIVE_FILE_NAME);
        StatefulBeanToCsv<TopPlayersResponseModel> beanToCsv = new StatefulBeanToCsvBuilder<TopPlayersResponseModel>(writer)
                .withMappingStrategy(playerGameDataStrategy)
                .withSeparator(',')
                .build();
        beanToCsv.write(topPlayers);
        writer.close();
    }

    /**
     * This function will take the Processed Collection of data and will write the csv file
     * @param wageredWonData
     * @throws IOException
     * @throws CsvRequiredFieldEmptyException
     * @throws CsvDataTypeMismatchException
     */
    public void writeSessionGameCsvFile(List<SessionWagerWinResponseModel> wageredWonData) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final SessionGameMappingStrategy<SessionWagerWinResponseModel> sessionGameDataStrategy = new SessionGameMappingStrategy<>();
        sessionGameDataStrategy.setType(SessionWagerWinResponseModel.class);
        sessionGameDataStrategy.setColumnMapping("PLAYER_ID", "SESSION_ID", "GAME_NAME", "AMOUNT_WAGERED", "AMOUNT_WON");
        Writer writer = new FileWriter(XPBettingConstants.SESSION_GAME_FILE_NAME);
        StatefulBeanToCsv<SessionWagerWinResponseModel> beanToCsv = new StatefulBeanToCsvBuilder<SessionWagerWinResponseModel>(writer)
                .withMappingStrategy(sessionGameDataStrategy)
                .withSeparator(',')
                .build();
        beanToCsv.write(wageredWonData);
        writer.close();
    }
}
