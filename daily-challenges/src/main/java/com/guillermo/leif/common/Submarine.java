package com.guillermo.leif.common;

import lombok.Getter;
import lombok.ToString;
import org.javatuples.KeyValue;

@ToString
@Getter
public class Submarine {

    int depth = 0;
    int horizontalPosition = 0;
    int aim = 0;

    private void forward(int x) {
        horizontalPosition += x;
        depth += (aim * x);
    }

    private void down(int x) {
//        depth += x;
        aim += x;
    }

    private void up(int x) {
//        depth -= x;
        aim -= x;
    }

    private void callDirectionMethodFromString(String direction, int amount) throws Exception {
        if (direction.contains("forward")) {
            forward(amount);
        } else if (direction.contains("down")) {
            down(amount);
        } else if (direction.contains("up")) {
            up(amount);
        } else {
            throw new Exception("Unknown direction: \"" + direction + "\"");
        }
    }

    public void callDirectionFromTuple(KeyValue tuple) throws Exception {
        String direction = (String) tuple.getKey();
        Integer amount = (Integer) tuple.getValue();
        callDirectionMethodFromString(direction, amount);
    }
}
