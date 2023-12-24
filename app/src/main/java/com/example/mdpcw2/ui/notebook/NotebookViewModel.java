package com.example.mdpcw2.ui.notebook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotebookViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotebookViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}