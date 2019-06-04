package project.chess.Models;

public interface CustomClock {
    void stop();
    void start();
    void setTime();
    void setTimePerRound(int timePerRound);
    void updateTime();
}
