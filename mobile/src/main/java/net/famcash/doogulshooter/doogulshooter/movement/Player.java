package net.famcash.doogulshooter.doogulshooter.movement;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.famcash.doogulshooter.doogulshooter.R;
import net.famcash.doogulshooter.doogulshooter.engine.GameEngine;
import net.famcash.doogulshooter.doogulshooter.engine.GameObject;
import net.famcash.doogulshooter.doogulshooter.input.InputController;

import java.util.ArrayList;
import java.util.List;

import static net.famcash.doogulshooter.doogulshooter.R.drawable.shipvert;

/**
 * Created by dooguls on 7/2/16.
 */
public class Player extends GameObject{

    private static final String TAG = Player.class.getSimpleName();

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEEN_BULLETS = 250;
    private final View mView;

    List<Bullet> mBullets = new ArrayList<Bullet>();

    private final ImageView mShip;
    private int mMaxX;
    private int mMaxY;

    private final TextView mTextView;

    double mPositionX;
    double mPositionY;

    double mSpeedFactor;
    private double mPixelFactor;
    private long mTimeSinceLastFire;

    //Ship graphics from http://opengameart.org/content/space-ship-building-bits-volume-1
    //Author: Stephen Challener (Redshrike), hosted by OpenGameArt.org
    //License: Creative Commons

    public Player(final View view) {
        mView = view;
        mPixelFactor = view.getHeight() / 400d;
        mSpeedFactor = mPixelFactor * 100d / 1000d;
        mMaxX = view.getWidth() - view.getPaddingRight() - view.getPaddingRight();
        mMaxY = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();

        mTextView = (TextView)view.findViewById(R.id.txt_score);

        mShip = new ImageView(view.getContext());
        Drawable shipDrawable = view.getContext().getResources().getDrawable(R.drawable.shipvert);
        mShip.setLayoutParams(new ViewGroup.LayoutParams(
                (int) (shipDrawable.getIntrinsicWidth() * mPixelFactor),
                (int) (shipDrawable.getIntrinsicHeight() * mPixelFactor)));
        mShip.setImageDrawable(shipDrawable);
        Log.d(TAG,"about to adview(mShip)");
        ((FrameLayout) view).addView(mShip);

        mMaxX -= (shipDrawable.getIntrinsicWidth()*mPixelFactor);
        mMaxY -= (shipDrawable.getIntrinsicHeight()*mPixelFactor);

        initBulletPool();
    }

    @Override
    public void startGame() {
        mPositionX = mMaxX / 2;
        mPositionY = mMaxY / 2;
    }

    /*@Override
    public void onUpdate(long elapsedMills, GameEngine gameEngine){
        InputController inputController = gameEngine.mInputController;
        mPositionX += mSpeedFactor*inputController.mHorizontalFactor*elapsedMills;
        if(mPositionX < 0) {
            mPositionX = 0;
        }
        if(mPositionX > mMaxX) {
            mPositionX = mMaxX;
        }
        mPositionY += mSpeedFactor*inputController.mVerticalFactor*elapsedMills;
        if(mPositionY < 0) {
            mPositionY = 0;
        }
        if(mPositionY > mMaxY) {
            mPositionY = mMaxY;
        }
    }*/

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if(gameEngine.mInputController.mIsFiring
                && mTimeSinceLastFire > TIME_BETWEEEN_BULLETS) {
            Bullet b = getBullet();
            if (b == null) {
                return;
            }
            b.init(this,mPositionX + mShip.getWidth()/2, mPositionY);
            gameEngine.addGameObject(b);
            mTimeSinceLastFire = 0;
        }
        else {
            mTimeSinceLastFire += elapsedMillis;
        }
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        updatePosition(elapsedMillis, gameEngine.mInputController);
        checkFiring(elapsedMillis, gameEngine);
    }

    private void updatePosition (long elapsedMillis, InputController inputController) {
        mPositionX += mSpeedFactor * inputController.mHorizontalFactor * elapsedMillis;
        if(mPositionX < 0) {
            mPositionX = 0;
        }
        if (mPositionX > mMaxX) {
            mPositionX = mMaxX;
        }
        mPositionY += mSpeedFactor * inputController.mVerticalFactor * elapsedMillis;
        if (mPositionY < 0) {
            mPositionY = 0;
        }
        if (mPositionY > mMaxY) {
            mPositionY = mMaxY;
        }
    }

    @Override
    public void onDraw() {
        mTextView.setText("["+(int) (mPositionX)+","+(int) (mPositionY)+"]");
        /*mShip.setTranslationX((int) mPositionX);
        mShip.setTranslationY((int) mPositionY);*/
        mShip.animate().translationX((int) mPositionX).translationY((int) mPositionY)
                .setDuration(1)
                .start();
    }

    private Bullet getBullet() {
        if(mBullets.isEmpty()) {
            return null;
        }
        return mBullets.remove(0);
    }

    void releaseBullet(Bullet b) {
        mBullets.add(b);
    }

    private void initBulletPool() {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            mBullets.add(new Bullet(mView,mPixelFactor));
        }
    }
}
