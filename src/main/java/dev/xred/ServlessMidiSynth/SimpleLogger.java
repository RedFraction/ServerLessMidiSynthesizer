package dev.xred.ServlessMidiSynth;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class SimpleLogger {

    private Object point;

    public SimpleLogger(Object clazz){
        point = clazz;
    }

    public void print(String logMsg){
        try(FileWriter fw = new FileWriter("processed-input.log", true)){
            var sb = new StringBuilder();

            sb.append("| ")
                .append(LocalDateTime.now())
                .append(" | - at class ")
                .append(point.getClass().getName())
                .append(" - ")
                .append(logMsg)
                .append("\n");

            fw.append(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
