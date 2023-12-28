package com.example.mdpcw2.ui.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mdpcw2.R;
import com.example.mdpcw2.databinding.FragmentNotebookBinding;
import com.example.mdpcw2.ui.notebook.newnote.NewNoteFragment;

public class NotebookFragment extends Fragment {

    private FragmentNotebookBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotebookViewModel notebookViewModel =
                new ViewModelProvider(this).get(NotebookViewModel.class);

        binding = FragmentNotebookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button newNoteButton = view.findViewById(R.id.notebookAddNoteButton);

        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNotebookAddNoteClick(v);
            }
        });
    }

    public void onNotebookAddNoteClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_navigation_notebook_to_newNoteFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
