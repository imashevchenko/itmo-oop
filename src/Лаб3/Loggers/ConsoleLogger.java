package Лаб3.Loggers;

import java.io.IOException;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }

    public String getType(){
        return "ConsoleLogger";
    }
}
