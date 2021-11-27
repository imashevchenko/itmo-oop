package Лаб35.Loggers;

public class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }

    public String getType(){
        return "ConsoleLogger";
    }
}
