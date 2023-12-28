package com.example.mdpcw2.ui.reminders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mdpcw2.R;
import com.example.mdpcw2.databinding.FragmentRemindersBinding;

public class RemindersFragment extends Fragment {

    private FragmentRemindersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RemindersViewModel remindersViewModel =
                new ViewModelProvider(this).get(RemindersViewModel.class);

        binding = FragmentRemindersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button newReminderButton = view.findViewById(R.id.remindersAddReminderButton);

        newReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReminderAddReminderClick(v);
            }
        });
    }

    public void onReminderAddReminderClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_navigation_reminders_to_newReminderFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}