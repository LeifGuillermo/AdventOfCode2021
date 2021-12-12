package com.guillermo.leif.inputReaders.caveMap;

import com.guillermo.leif.inputReaders.IntegerMapReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CaveMapHeightReader extends IntegerMapReader {

    public List<List<Integer>> readCaveHeights(String filePath) throws IOException {
        return this.readMap(filePath);
    }
}
