package io.tomislav.baking.bakingapp;

import android.app.Dialog;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
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

    @ViewById(R.id.description)
    TextView description;

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

    boolean landscapePhone = false;

    Dialog fullScreenDialog;

    @AfterViews
    public void afterViews() {
        setupTextContent();
        String videoUrl = getVideoUrl();
        if (videoUrl == null) {
            hidePlayer();
        } else {
            initializePlayer(videoUrl);
        }
    }

    private String getVideoUrl() {
        String videoUrl = step.getVideoURL();
        if (videoUrl != null && !videoUrl.equals("")) {
            return videoUrl;
        }
        videoUrl = step.getThumbnailURL();
        if (videoUrl.contains(".mp4")) {
            return videoUrl;
        }
        return null;
    }

    private void setupTextContent() {
        if (!landscapePhone) {
            shortDescription.setText(step.getShortDescription());
            description.setText(step.getDescription());
        }
    }

    @Override
    public void onPause() {
        releasePlayer();
        if (fullScreenDialog != null) {
            fullScreenDialog.dismiss();
            fullScreenDialog = null;
        }
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
            player.addListener(this);
        }

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(uriString);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, false, false);
        if (landscapePhone) {
            setFullScreen();
        }
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

    private void setFullScreen() {
        fullScreenDialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ((ViewGroup)playerView.getParent()).

            removeView(playerView);
        fullScreenDialog.addContentView(playerView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        fullScreenDialog.show();
    }
}
