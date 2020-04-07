package live.combatemic.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerFragment;
//import com.google.android.youtube.player.YouTubePlayerSupportFragment;
////import com.google.android.youtube.player.YouTubePlayerView;
//import com.google.android.youtube.player.YouTubeThumbnailLoader;
//import com.google.android.youtube.player.YouTubeThumbnailView;
import live.combatemic.app.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collections;


public class VideoAdapter extends ArrayAdapter {

    private final JSONArray videos;
    private static final int RECOVERY_REQUEST = 1;
    private final Context context;
//    private final Lifecycle lifecycle;
//    private YouTubePlayerView youTubeView;
//    private YouTubePlayerFragment supportMapFragment;

    public VideoAdapter(Context context, int i, JSONArray videos) {
        super(context, i, Collections.singletonList(videos));
        this.context = context;
        this.videos = videos;
    }

    @Override
    public int getCount() {
        super.getCount();
        return videos.length();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        try {
            return videos.getString(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String TAG = "dksabdkjb";
        // Get the data item for this position
        final String city = (String) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_video, parent, false);
        }

        YouTubePlayerView youTubePlayerView = convertView.findViewById(R.id.youtube_player_view);
//        lifecycle.addObserver(youTubePlayerView);

        youTubePlayerView.enableBackgroundPlayback(false);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = city;
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
        // Return the completed view to render on screen
//        YouTubePlayerSupportFragment youTubePlayerFragment = new YouTubePlayerSupportFragment();
//        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
//        FragmentManager fragmentManager = ((YouTubeActivity)context).getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment);
//        fragmentTransaction.commit();
        /*  initialize the thumbnail image view , we need to pass Developer Key */
//        YouTubePlayerView youTubeView = (YouTubePlayerView) convertView.findViewById(R.id.youtube_view);
//        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                youTubePlayer.loadVideo("voBuLsstp2s");
//            }
//
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//            }
//        });

        return convertView;
    }

//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
//        if (!wasRestored) {
//            youTubePlayer.cueVideo("voBuLsstp2s"); // Plays https://www.youtube.com/watch?v=-epZ12OclMg
////            youTubePlayer.loadVideo("voBuLsstp2s");
//
////            youTubePlayer.play();
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        if (youTubeInitializationResult.isUserRecoverableError()) {
//            youTubeInitializationResult.getErrorDialog((Activity) getContext(), RECOVERY_REQUEST).show();
//        } else {
//            String error = String.format("R.string.menu_gallery", youTubeInitializationResult.toString());
//            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return youTubeView;
//    }
}

