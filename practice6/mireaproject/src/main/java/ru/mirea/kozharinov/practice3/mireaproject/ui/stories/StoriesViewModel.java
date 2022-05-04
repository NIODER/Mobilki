package ru.mirea.kozharinov.practice3.mireaproject.ui.stories;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.kozharinov.practice3.mireaproject.database.stories.Video;

public class StoriesViewModel extends ViewModel {

    private MutableLiveData<String> name;
    private MutableLiveData<Uri> uri;

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<Uri> getUri() {
        return uri;
    }

    public void setUri(MutableLiveData<Uri> uri) {
        this.uri = uri;
    }
}
