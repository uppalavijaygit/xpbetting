package com.xp.betting.app.reader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvToBeanFilter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.xp.betting.app.model.XPBettingModel;
import com.xp.betting.app.utils.XPBettingConstants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class XPBettingReader {

    /**
     * This is the Reader function , will read the csv file and convert to the Collection of XPBettingModel.
     * This function also does the filtering the specific date while reading the data from the data set.
     *
     * @return Collection of XPBettingModel
     * @throws FileNotFoundException
     */
    public List<XPBettingModel> readCsv() throws FileNotFoundException {

        // This is the strategy for reading file, we will mention which type of bean will be csv will convert into
        HeaderColumnNameMappingStrategy<XPBettingModel> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(XPBettingModel.class);

        // This is the condition to filter out the Bet and Win Chapters from the data set.
        CsvToBeanFilter allowOnlyBetAndWin = fields -> Arrays.stream(fields).anyMatch(field -> field.contains("Bet") || field.contains("Win"));

        // Reading the given file along with the given strategy and filter condition
        FileReader fileReader = new FileReader(XPBettingConstants.INPUT_FILE_NAME);
        CsvToBean<XPBettingModel> builder = new CsvToBeanBuilder<XPBettingModel>(fileReader).withMappingStrategy(strategy).withFilter(allowOnlyBetAndWin).build();
        return builder.parse();
    }

}
