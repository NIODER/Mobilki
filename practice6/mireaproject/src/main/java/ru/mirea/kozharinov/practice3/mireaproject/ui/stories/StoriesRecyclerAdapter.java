package ru.mirea.kozharinov.practice3.mireaproject.ui.stories;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.mirea.kozharinov.practice3.mireaproject.R;
import ru.mirea.kozharinov.practice3.mireaproject.database.stories.Video;

public class StoriesRecyclerAdapter extends RecyclerView.Adapter<StoriesRecyclerAdapter.StoryViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Video> videos;

    public StoriesRecyclerAdapter(Context context, ArrayList<Video> videos) {
        this.inflater = LayoutInflater.from(context);
        this.videos = videos;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View storyView = inflater.inflate(R.layout.story, parent, false);
        return new StoryViewHolder(storyView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.name.setText(video.name);
        holder.video.setVideoURI(Uri.parse(video.uri));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class StoryViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final VideoView video;

        public StoryViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.storyName);
            this.video = view.findViewById(R.id.storyVideoView);
        }
    }
}
