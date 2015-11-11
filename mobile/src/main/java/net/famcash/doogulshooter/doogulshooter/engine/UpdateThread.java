package net.famcash.doogulshooter.doogulshooter.engine;

/**
 * Created by dooguls on 11/8/15.
 */
public class UpdateThread {

    private final GameEngine mGameEngine;
    private boolean mGameIsRunning = true;
    private boolean mPauseGame = false;
    private Object mLock = new Object();

    public UpdateThread(GameEngine gameEngine) {
        mGameEngine = gameEngine;
    }

    @Override
    public void run() {
        long previousTimeMillis;
        long currentTimeMillis;
        long elapsedMillis;
        previousTimeMillis = System.currentTimeMillis();

        while (mGameIsRunning) {
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;
            if (mPauseGame) {
                while(mPauseGame) {
                    try {
                        synchronized (mLock) {
                            mLock.wait();
                        }
                    } catch (InterruptedException e) {
                        // We stay on th loop
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            mGameEngine.onUpdate(elapsedMillis);
            previousTimeMillis = currentTimeMillis;
        }
    }

    public void pauseGame() {
        mPauseGame = true;
    }

    public void resumeGame() {
        if (mPauseGame == true) {
            mPauseGame = false;
            synchronized (mLock) {
                mLock.notify();
            }
        }
    }

    public void start() {
        mGameIsRunning = true;
        mPauseGame = false;
        super.start();
    }

    public void stopGame() {
        mGameIsRunning = false;
        resumeGame();
    }

    public boolean ismGameIsRunning() {
        return mGameIsRunning;
    }

    public boolean isGamePaused() {
        return mPauseGame;
    }
}