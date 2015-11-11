package net.famcash.doogulshooter.doogulshooter.engine;

/**
 * Created by dooguls on 11/8/15.
 */
public abstract class GameObject {

    public abstract void startGame();
    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine);
    public abstract void onDraw();
    public final Runnable mOnAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public final Runnable mOnRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread(){

    }

    public void onAddedToGameUiThread() {

    }
}
