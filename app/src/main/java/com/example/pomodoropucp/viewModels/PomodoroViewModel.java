package com.example.pomodoropucp.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PomodoroViewModel extends ViewModel {

    private final MutableLiveData<Long> countdown = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRunning = new MutableLiveData<>();

    public PomodoroViewModel() {
        countdown.setValue(25 * 60 * 1000L);
        isRunning.setValue(false);}

    public MutableLiveData<Long> getcountdown() {return countdown;}
    public MutableLiveData<Boolean> getisRunning() {return isRunning;}

    public void startTimer() {isRunning.setValue(true);}
    public void resetTimer() {countdown.setValue(25 * 60 * 1000L);}
    public void intervalTimer() {countdown.setValue(5 * 60 * 1000L);}
}
