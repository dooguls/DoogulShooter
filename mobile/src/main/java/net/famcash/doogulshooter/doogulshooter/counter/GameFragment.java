package net.famcash.doogulshooter.doogulshooter.counter;

import android.util.Log;
//import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.famcash.doogulshooter.doogulshooter.DSActivity;
import net.famcash.doogulshooter.doogulshooter.DSBaseFragment;
import net.famcash.doogulshooter.doogulshooter.engine.GameEngine;
import net.famcash.doogulshooter.doogulshooter.R;

//import net.famcash.doogulshooter.doogulshooter.counter.GameFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class GameFragment extends DSBaseFragment implements View.OnClickListener {

    private static final String TAG = GameFragment.class.getSimpleName();
    private GameEngine mGameEngine;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        mGameEngine = new GameEngine(getActivity());
        mGameEngine.addGameObject(new ScoreGameObject(view, R.id.txt_score));
        view.findViewById(R.id.btn_start_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        mGameEngine.startGame();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
           // playOrPause();
            pauseGameAndShowPauseDialog();
        }
        if (v.getId() == R.id.btn_start_stop) {
            startOrStop();
        }
    }

    private void pauseGameAndShowPauseDialog() {
        mGameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mGameEngine.resumeGame();
                            }
                        })
                .setNegativeButton(R.string.stop,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mGameEngine.stopGame();
                                ((DSActivity)getActivity()).navigateBack();
                            }
                        })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mGameEngine.resumeGame();
                    }
                })
                .create()
                .show();
    }
    /*private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (mGameEngine.isPaused()) {
            mGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            mGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }*/

    private void startOrStop() {
        Log.d(TAG, "Made it to startOrStop");
        Button button = (Button) getView().findViewById(R.id.btn_start_stop);
        Button playPauseButton = (Button) getView().findViewById(R.id.btn_play_pause);
        if(mGameEngine.isRunning()) {
            mGameEngine.stopGame();
            ((DSActivity)getActivity()).navigateBack();
            Log.d(TAG,"just finished navigateback");
            //button.setText(R.string.start);
            //playPauseButton.setEnabled(false);
        }
        else {
            Log.d(TAG, "I shouldn't end up in the elese of startOrStop");
            mGameEngine.startGame();
            button.setText(R.string.stop);
            playPauseButton.setEnabled(true);
            playPauseButton.setText(R.string.pause);
        }
    }

    @Override
    public boolean onBackPressed() {
        if(mGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameEngine.stopGame();
    }
}
