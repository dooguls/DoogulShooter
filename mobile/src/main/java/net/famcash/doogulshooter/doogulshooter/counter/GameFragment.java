package net.famcash.doogulshooter.doogulshooter.counter;

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
            playOrPause();
        }
        if (v.getId() == R.id.btn_start_stop) {
            startOrStop();
        }
    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (mGameEngine.isPaused()) {
            mGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            mGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }

    private void startOrStop() {
        Button button = (Button) getView().findViewById(R.id.btn_start_stop);
        Button playPauseButton = (Button) getView().findViewById(R.id.btn_play_pause);
        if(mGameEngine.isRunning()) {
            mGameEngine.stopGame();
            button.setText(R.string.start);
            playPauseButton.setEnabled(false);
        }
        else {
            mGameEngine.startGame();
            button.setText(R.string.stop);
            playPauseButton.setEnabled(true);
            playPauseButton.setText(R.string.pause);
        }
    }


}
