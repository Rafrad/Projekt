package project.chess.Models;

/**
 * Defines simple clock.
 */

public interface CustomClock {
    void stop();

    void start();

    void setTime();

    void setTimePerRound(int timePerRound);

    void updateTime();
}
