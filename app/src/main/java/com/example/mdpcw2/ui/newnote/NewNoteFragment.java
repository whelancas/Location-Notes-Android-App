package com.example.mdpcw2.ui.newnote;

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
import com.example.mdpcw2.databinding.FragmentNewNoteBinding;

public class NewNoteFragment extends Fragment {

    private FragmentNewNoteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewNoteViewModel NewNoteViewModel =
                new ViewModelProvider(this).get(NewNoteViewModel.class);

        binding = FragmentNewNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button useCurrentLocationButton = view.findViewById(R.id.newNoteButton);
        Button cancelButton = view.findViewById(R.id.newNoteCancelButton);

        useCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useCurrentLocation(v);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_new_note_to_navigation_home);
            }
        });
    }

    private void useCurrentLocation(View v) {
        // TODO: Database stuff

        Navigation.findNavController(v).navigate(R.id.action_navigation_new_note_to_navigation_home);
    }
}