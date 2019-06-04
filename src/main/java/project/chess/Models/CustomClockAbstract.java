package project.chess.Models;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class CustomClockAbstract implements CustomClock {
    private ScheduledExecutorService executorService;
    public int time = 0;

    protected abstract void onTimeStep();

    protected abstract void onTimeEnd();

//    protected abstract void updateTime();

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
        this.time = time;
    }

    @Override
    public void setTimePerRound(int timeToAdd) {
        time += timeToAdd;
    }

    @Override
    public void updateTime() {

    }
}
