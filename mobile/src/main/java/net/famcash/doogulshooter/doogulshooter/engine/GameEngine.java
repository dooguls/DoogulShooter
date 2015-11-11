package net.famcash.doogulshooter.doogulshooter.engine;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dooguls on 11/8/15.
 */
public class GameEngine {

    private List<GameObject> mGameObjects = new ArrayList<GameObject>();
    private List<GameObject> mObjectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> mObjectsToRemove = new ArrayList<GameObject>();

    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;
    private Activity mActivity;

    public GameEngine (Activity a) {
        mActivity = a;
    }

    private Runnable mDrawRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (mGameObjects) {
                int numGameObjects = mGameObjects.size();
                for (int ii = 0; ii < numGameObjects; ii++) {
                    mGameObjects.get(ii).onDraw();
                }
            }
        }
    };

    public void startGame() {
        //stop a game if it is running
        stopGame();

        //setup game objects
        int numGameObjects = mGameObjects.size();
        for (int ii=0; ii < numGameObjects; ii++) {
            mGameObjects.get(ii).startGame();
        }

        //start the update thread
        mUpdateThread = new UpdateThread(this);
        mUpdateThread.start();

        //start the drawing thread
        mDrawThread = new DrawThread(this);
        mDrawThread.start();
    }

    public void stopGame() {
        if (mUpdateThread != null) {
            mUpdateThread.stopGame();
        }
        if (mDrawThread != null) {
            mDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (mUpdateThread != null) {
            mUpdateThread.pauseGame();
        }
        if (mDrawThread != null) {
            mDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (mUpdateThread != null) {
            mUpdateThread.resumeGame();
        }
        if (mDrawThread != null) {
            mDrawThread.resumeGame();
        }
    }

    public void addGameObject(final GameObject gameObject) {
        if (isRunning()) {
            mObjectsToAdd.add(gameObject);
        }
        else {
            mGameObjects.add(gameObject);
        }
        mActivity.runOnUiThread(gameObject.mOnAddedRunnable);
    }

    public void removeGameObject(final GameObject gameObject) {
        mObjectsToRemove.add(gameObject);
        mActivity.runOnUiThread(gameObject.mOnRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = mGameObjects.size();
        for (int ii = 0; ii < numGameObjects; ii++) {
            mGameObjects.get(ii).onUpdate(elapsedMillis,this);
        }
        synchronized (mGameObjects) {
            while (!mObjectsToRemove.isEmpty()) {
                mGameObjects.remove(mObjectsToRemove.remove(0));
            }
            while (!mObjectsToAdd.isEmpty()) {
                mGameObjects.add(mObjectsToAdd.remove(0));
            }
        }
    }

    public void onDraw() {
        mActivity.runOnUiThread(mDrawRunnable);
    }

    public boolean isRunning() {
        return mUpdateThread != null && mUpdateThread.ismGameIsRunning();
    }

    public boolean isPaused() {
        return mUpdateThread != null && mUpdateThread.isGamePaused();
    }

}
