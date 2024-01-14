package com.example.mdpcw2.ui.newreminder;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mdpcw2.R;
import com.example.mdpcw2.databinding.FragmentNewReminderBinding;

public class NewReminderFragment extends Fragment {

    private FragmentNewReminderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewReminderViewModel NewReminderViewModel =
                new ViewModelProvider(this).get(NewReminderViewModel.class);

        binding = FragmentNewReminderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button useCurrentLocationButton = view.findViewById(R.id.newReminderSetCurrentLocationButton);
        Button useCustomLocationButton = view.findViewById(R.id.setReminderCustomLocationButton);
        Button cancelButton = view.findViewById(R.id.newReminderCancelButton);

        useCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useCurrentLocation(v);
            }
        });

        useCustomLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { useCustomLocation(v); }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Navigation.findNavController(v).navigate(R.id.action_navigation_new_reminder_to_navigation_reminders); }
        });
    }

    private void useCustomLocation(View v) {
        // TODO: Database and location stuff

        Navigation.findNavController(v).navigate(R.id.action_navigation_new_reminder_to_navigation_reminders);
    }

    private void useCurrentLocation(View v) {
        // TODO: Database and location stuff

        Navigation.findNavController(v).navigate(R.id.action_navigation_new_reminder_to_navigation_reminders);
    }

}