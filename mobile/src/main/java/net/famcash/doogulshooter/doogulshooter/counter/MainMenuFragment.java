package net.famcash.doogulshooter.doogulshooter.counter;

/**
 * Created by dooguls on 1/2/16.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.famcash.doogulshooter.doogulshooter.DSActivity;
import net.famcash.doogulshooter.doogulshooter.R;
import net.famcash.doogulshooter.doogulshooter.DSBaseFragment;

public class MainMenuFragment extends DSBaseFragment implements View.OnClickListener {
    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((DSActivity)getActivity()).startGame();
    }
}