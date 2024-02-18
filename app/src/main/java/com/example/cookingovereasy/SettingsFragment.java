package com.example.cookingovereasy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Class that manages the settings fragment.
 */
public class SettingsFragment extends PreferenceFragment {

    // instance vars needed for preferences
    String[] dietList;
    String[] allergyList;
    String[] preferredProteins;
    // instance vars needed for logout functionality:
    FirebaseUser user;
    FirebaseAuth auth;

    /** onCreate sets up settings fragment layout and includes functionality for logging out,
    * updating username, and changing diet, allergy, and protein preferences
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }
        else {
            addPreferencesFromResource(R.xml.settings);
        }
        //code for logout button:
        Preference button = findPreference(getString(R.string.logOutButton));
        button.setOnPreferenceClickListener(preference -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            Toast.makeText(getActivity(), "Successfully logged out.",
                    Toast.LENGTH_SHORT).show();
            return true;
        });

        //code for switching to change username activity:
        Preference changeUsernameBtn = findPreference("changeUsernameBtn");

        changeUsernameBtn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference){
                Intent intent = new Intent(getActivity(), ChangeUsername.class);
                startActivity(intent);
                return true;
            }
        });
    }
}