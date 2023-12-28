package com.example.mdpcw2.ui.notebook.newnote;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

}