package com.xp.betting.app.reader;

import com.xp.betting.app.model.XPBettingModel;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XPBettingReaderTest {

    @Test
    void readCsv() throws FileNotFoundException {
        List<XPBettingModel> xpBettingModels = new XPBettingReader().readCsv();
        assertAll(" Testing the CSV Reader",
                ()-> assertFalse(xpBettingModels.isEmpty()),
                ()-> assertEquals(1349, xpBettingModels.size())
                );
    }
}