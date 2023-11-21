package com.example.biblioteca.ui.prestamo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrestamoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PrestamoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}