package com.example.mdpcw2.ui.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.mdpcw2.R;
import com.example.mdpcw2.database.AppDatabase;
import com.example.mdpcw2.database.DAOs.ExercisesDao;
import com.example.mdpcw2.database.tables.Exercises;
import com.example.mdpcw2.databinding.FragmentNotebookBinding;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NotebookFragment extends Fragment {

    private FragmentNotebookBinding binding;
    AppDatabase db;
    ExercisesDao exercisesDao;
    double startLatitude, startLongitude, endLatitude, endLongitude;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotebookViewModel notebookViewModel = new ViewModelProvider(this).get(NotebookViewModel.class);

        binding = FragmentNotebookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(requireActivity(),
                AppDatabase.class, "AppDatabase").build();
        exercisesDao = db.exercisesDao();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Exercises> exercisesList = exercisesDao.getAll();

            // Update UI on the main thread
            requireActivity().runOnUiThread(() -> {
                ListView listView = view.findViewById(R.id.notebookListView);
                ExercisesArrayAdapter arrayAdapter = new ExercisesArrayAdapter(
                        requireActivity(),
                        exercisesList);

                listView.setAdapter(arrayAdapter);
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
