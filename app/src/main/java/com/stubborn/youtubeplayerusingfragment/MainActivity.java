package com.stubborn.youtubeplayerusingfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener{

        public static final String DEVELOPER_KEY = "AIzaSyAf0ehIXDbrcgnXbyoxg4KfhxA7S8vLUoo";
        private static final String VIDEO_ID = "mdI35SrAKjA";
        private static final int RECOVERY_DIALOG_REQUEST = 1;
        YouTubePlayerFragment myYouTubePlayerFragment;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            myYouTubePlayerFragment = (YouTubePlayerFragment)getFragmentManager()
                    .findFragmentById(R.id.youtubefragment);
            myYouTubePlayerFragment.initialize(DEVELOPER_KEY, this);
        }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider,
                YouTubeInitializationResult errorReason) {
            if (errorReason.isUserRecoverableError()) {
                errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
            } else {
                String errorMessage = String.format(
                        "There was an error initializing the YouTubePlayer (%1$s)",
                        errorReason.toString());
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == RECOVERY_DIALOG_REQUEST) {
                // Retry initialization if user performed a recovery action
                getYouTubePlayerProvider().initialize(DEVELOPER_KEY, this);
            }
        }
        protected YouTubePlayer.Provider getYouTubePlayerProvider() {
            return (YouTubePlayerView)findViewById(R.id.youtubefragment);
        }
    }