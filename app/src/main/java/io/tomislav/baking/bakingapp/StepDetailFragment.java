package io.tomislav.baking.bakingapp;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import io.tomislav.baking.bakingapp.models.Step;

@EFragment(R.layout.fragment_step_detail)
public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {
    @ViewById(R.id.short_description)
    TextView shortDescription;

    @ViewById(R.id.video_view)
    SimpleExoPlayerView playerView;

    @FragmentArg("step")
    @InstanceState
    Step step;

    SimpleExoPlayer player;

    @InstanceState
    long playbackPosition;

    @InstanceState
    int currentWindow;

    @InstanceState
    boolean playWhenReady;

    @AfterViews
    public void afterViews() {
        shortDescription.setText(step.getShortDescription());
        if (step.getVideoURL().equals("")) {
            hidePlayer();
        } else {
            initializePlayer(step.getVideoURL());
        }
    }

    @Override
    public void onPause() {
        releasePlayer();
        super.onPause();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int state) {
        if (state == ExoPlayer.STATE_ENDED){
            this.playWhenReady = false;
            this.playbackPosition = 0;
            player.setPlayWhenReady(false);
            player.seekTo(0);
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {}

    @Override
    public void onPositionDiscontinuity() {}

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}

    private void initializePlayer(String uriString) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
        }

        playerView.setPlayer(player);
        player.addListener(this);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(uriString);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    private void hidePlayer() {
        playerView.setVisibility(View.GONE);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
