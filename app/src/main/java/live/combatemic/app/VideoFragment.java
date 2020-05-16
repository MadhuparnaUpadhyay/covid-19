package live.combatemic.app;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import live.combatemic.app.Common.CircleTransform;
import live.combatemic.app.Common.ServerCallback;
import live.combatemic.app.Common.VollyServerCall;

import live.combatemic.app.R;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener, View.OnClickListener {
    //implements YouTubePlayer.OnInitializedListener
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private YouTubePlayerFragment supportMapFragment;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private WebView webview;
    private ListView listViewVideo;
    private TextView shareTextView, likeTextView, dislikeTextView;
    private String videoId = "ghqF4CCrt2k";
    private String mainUrl = "https://www.youtube.com/watch?v=";
    private SharedPreferences sharedPref;
    private ImageView imageViewThumbnail, imageViewThumbnail1, imageViewThumbnail2;
    private static VideoFragment fragment;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param columnCount Parameter 1.
     * @param columnCount Parameter 2.
     * @return A new instance of fragment Contact.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(Integer columnCount) {
        if (fragment == null) {
            fragment = new VideoFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);

        sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//        listViewVideo = view.findViewById(R.id.list_view_video);
        shareTextView = view.findViewById(R.id.share_video);
        likeTextView = view.findViewById(R.id.like);
        dislikeTextView = view.findViewById(R.id.dislike);
        imageViewThumbnail = view.findViewById(R.id.thumbnail);
        imageViewThumbnail1 = view.findViewById(R.id.thumbnail1);
        imageViewThumbnail2 = view.findViewById(R.id.thumbnail2);
        shareTextView.setOnClickListener(this);
        likeTextView.setOnClickListener(this);
        dislikeTextView.setOnClickListener(this);
        imageViewThumbnail.setOnClickListener(this);
        imageViewThumbnail1.setOnClickListener(this);
        imageViewThumbnail2.setOnClickListener(this);

//        Bitmap bmThumbnail;
        String imageUrl = "http://img.youtube.com/vi/gND4njQhrsA/maxresdefault.jpg";
        String imageUrl1 = "http://img.youtube.com/vi/q_jUje5IS1M/maxresdefault.jpg";
        String imageUrl2 = "http://img.youtube.com/vi/gv9DgP4Svuk/maxresdefault.jpg";

//        Picasso.get().load(imageUrl).into(imageViewThumbnail);
        Picasso.get().load(imageUrl).transform(new CircleTransform()).into(imageViewThumbnail);
        Picasso.get().load(imageUrl1).transform(new CircleTransform()).into(imageViewThumbnail1);
        Picasso.get().load(imageUrl2).transform(new CircleTransform()).into(imageViewThumbnail2);

        return view;
    }

    private void getVideo() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String state = sharedPref.getString("state", "");
        String city = sharedPref.getString("city", "");
        String pincode = sharedPref.getString("zipcode", "");
        VollyServerCall controller = new VollyServerCall("https://combatemic.live/api/v1/");
        final String MAIN_URL_STATE = "videos?location=" + state + "&state=" + state + "&city=" + city + "&pincode=" + pincode;
        controller.JsonObjectRequest(getContext(), MAIN_URL_STATE, new ServerCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(JSONObject response) {
                        // do stuff here
                        JSONArray general = new JSONArray();
                        try {
//                            System.out.println(response);
//                            System.out.println(response.getJSONArray("general"));
                            general = response.getJSONArray("general");
                            JSONArray location = response.getJSONArray("location");
                            if (location.length() > 0) {
                                videoId = location.getString(0);
                            } else if (general.length() > 0) {
                                videoId = general.getString(0);
                            }
//                            System.out.println(response.getJSONArray("location"));
//                            general.addAll(location);
//                            for (int i = 0; i < location.length(); i++) {
//                                String jsonObject = location.getString(i);
//                                general.put(jsonObject);
//                            }
//                            final VideoAdapter adapter = new VideoAdapter(getActivity(), 0, general);
//                            listViewVideo.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getVideo();


        youTubePlayerFragment = new YouTubePlayerSupportFragment();
        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(videoId); // Plays https://www.youtube.com/watch?v=-epZ12OclMg
//            youTubePlayer.loadVideo("voBuLsstp2s");

//            youTubePlayer.play();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
        } else {
            String error = String.format("R.string.menu_gallery", youTubeInitializationResult.toString());
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        if (v == shareTextView) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            // Add data to the intent, the receiving app will decide
            // what to do with it.
            share.putExtra(Intent.EXTRA_SUBJECT, "DIS19");
            share.putExtra(Intent.EXTRA_TEXT, mainUrl + videoId);

            startActivity(Intent.createChooser(share, "Share link!"));

        } else if (v == likeTextView) {
            Bundle arguments = getArguments();
            SharedPreferences.Editor myEdit = sharedPref.edit();
            myEdit.putInt("like", 1);
            myEdit.apply();

            for (Drawable drawable : likeTextView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));
                }
            }
            for (Drawable drawable : dislikeTextView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.clearColorFilter();
//                    drawable.setColorFilter(new PorterDuffColorFilter(Color.MAGENTA, PorterDuff.Mode.SRC_IN));
                }
            }

        } else if (v == dislikeTextView) {
            Bundle arguments = getArguments();
            SharedPreferences.Editor myEdit = sharedPref.edit();
            myEdit.putInt("dislike", 0);
            myEdit.apply();
            for (Drawable drawable : dislikeTextView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.clearColorFilter();
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));
                }
            }
            for (Drawable drawable : likeTextView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.clearColorFilter();
//                    drawable.setColorFilter(new PorterDuffColorFilter(R.color.colorAccent, PorterDuff.Mode.SRC_IN));
                }
            }
        } else if (v == imageViewThumbnail) {
            openYoutube("gND4njQhrsA");
        } else if (v == imageViewThumbnail1) {
            openYoutube("q_jUje5IS1M");
        } else if (v == imageViewThumbnail2) {
            openYoutube("gv9DgP4Svuk");
        }
    }

    private void openYoutube(String id) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } catch (ActivityNotFoundException e) {

            // youtube is not installed.Will be opened in other available apps

            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=" + id));
            startActivity(i);
        }
    }
}
