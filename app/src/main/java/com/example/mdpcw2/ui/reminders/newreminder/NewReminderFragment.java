package com.example.mdpcw2.ui.reminders.newreminder;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mdpcw2.R;
import com.example.mdpcw2.databinding.FragmentNewReminderBinding;
import com.example.mdpcw2.ui.reminders.newreminder.NewReminderViewModel;

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

}