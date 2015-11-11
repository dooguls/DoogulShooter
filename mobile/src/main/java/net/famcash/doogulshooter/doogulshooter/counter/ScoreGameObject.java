package net.famcash.doogulshooter.doogulshooter.counter;

import android.view.View;
import android.widget.TextView;

import net.famcash.doogulshooter.doogulshooter.engine.GameEngine;
import net.famcash.doogulshooter.doogulshooter.engine.GameObject;

/**
 * Created by dooguls on 11/8/15.
 */
public class ScoreGameObject extends GameObject
{
    private final TextView mText;
    private long mTotalMilis;

    public ScoreGameObject(View view, int viewResId) {
        mText = (TextView) view.findViewById(viewResId);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mTotalMilis += elapsedMillis;
    }

    @Override
    public void startGame() {
        mTotalMilis = 0;
    }

    @Override
    public void onDraw() {
        mText.setText(String.valueOf(mTotalMilis));
    }
}
