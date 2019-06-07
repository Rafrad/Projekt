package project.chess.Models;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Defines how player clock behaves.
 */

public abstract class CustomClockAbstract implements CustomClock {
    private ScheduledExecutorService executorService;
    protected int time;

    /**
     * This method is called every second passed in clock/
     */

    protected abstract void onTimeStep();

    /**
     * This method is called when time in clock ends.
     */

    protected abstract void onTimeEnd();

    @Override
    public void stop() {
        executorService.shutdownNow();
    }

    @Override
    public void start() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(() -> {
            if (time > 0) {
                onTimeStep();
                time--;
            } else {
                onTimeEnd();
                stop();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void setTime() {
    }

    /**
     * In some game modes, we have to add time in every move.
     * This method adds time to current time.
     * @param timeToAdd how much time is add to clock
     */

    @Override
    public void setTimePerRound(int timeToAdd) {
        time += timeToAdd;
    }

    @Override
    public void updateTime() {

    }
}
