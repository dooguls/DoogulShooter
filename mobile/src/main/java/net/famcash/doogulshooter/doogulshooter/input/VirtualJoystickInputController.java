package net.famcash.doogulshooter.doogulshooter.input;

import android.view.MotionEvent;
import android.view.View;

import net.famcash.doogulshooter.doogulshooter.R;

/**
 * Created by dooguls on 8/20/16.
 */
public class VirtualJoystickInputController extends InputController {

    private float mStartingPositionX;
    private float mStartingPositionY;
    private final double mMaxDistance;

    public VirtualJoystickInputController (View view) {
        view.findViewById(R.id.vjoystick_main)
                .setOnTouchListener(new VJoystickTouchListener());
        view.findViewById(R.id.vjoystick_touch)
                .setOnTouchListener(new VFireButtonTouchListener());

        double pixelFactor = view.getHeight() / 400d;
        //mess with this if i don't like the sensitivity of the joystick
        mMaxDistance = 50*pixelFactor;
    }

    private class VFireButtonTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                mIsFiring = true;
            }
            else if (action == MotionEvent.ACTION_UP) {
                mIsFiring = false;
            }
            return true;
        }
    }

    private class VJoystickTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getActionMasked();
            if (action == MotionEvent.ACTION_DOWN) {
                mStartingPositionX = event.getX(0);
                mStartingPositionY = event.getY(0);
            }
            else if (action == MotionEvent.ACTION_UP) {
                mHorizontalFactor = 0;
                mVerticalFactor = 0;
            }
            else if (action == MotionEvent.ACTION_MOVE) {
                //get the proportion to the max
                mHorizontalFactor = (event.getX(0) - mStartingPositionX) / mMaxDistance;
                if (mHorizontalFactor > 1) {
                    mHorizontalFactor = 1;
                }
                else if (mHorizontalFactor < -1) {
                    mHorizontalFactor = -1;
                }
                mVerticalFactor = (event.getY(0) - mStartingPositionY) / mMaxDistance;
                if (mVerticalFactor > 1) {
                    mVerticalFactor = 1;
                }
                else if (mVerticalFactor < -1) {
                    mVerticalFactor = -1;
                }
            }
            return true;
        }
    }
}
