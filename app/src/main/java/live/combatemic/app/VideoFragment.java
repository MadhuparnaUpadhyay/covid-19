package live.combatemic.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    private ImageView imageViewThumbnail;

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
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        shareTextView.setOnClickListener(this);
        likeTextView.setOnClickListener(this);
        dislikeTextView.setOnClickListener(this);

//        Bitmap bmThumbnail;
        String imageUrl = "http://img.youtube.com/vi/VUHPBHcstak/maxresdefault.jpg";
//        MICRO_KIND, size: 96 x 96 thumbnail
//        bmThumbnail = ThumbnailUtils.createVideoThumbnail(url, MediaStore.Images.Thumbnails.MICRO_KIND);
//        imageViewThumbnail.setImageBitmap(bmThumbnail);
        Picasso.get().load(imageUrl).into(imageViewThumbnail);
//        Bitmap bmp = null;
//        try {
//            URL url = new URL("http://img.youtube.com/vi/VUHPBHcstak/default.jpg");
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        imageViewThumbnail.setImageBitmap(bmp);
//        webview = new WebView(getContext());
//
//        final WebSettings settings = webview.getSettings();
//        settings.setJavaScriptEnabled(true);
//
//        String frameVideo = "<html><body>Youtube video .. <br> <iframe width=\"320\" height=\"315\" src=\"https://www.youtube.com/\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
//
//        webview.loadData(frameVideo, "text/html", "utf-8");
//
//        webview.loadUrl("http://www.youtube.com/");
//
//
//        webview.setWebViewClient(new WebViewClient());
//        settings.setJavaScriptEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setPluginState(WebSettings.PluginState.ON);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
//
////        webview.setWebChromeClient(new WebChromeClient());
//        webview.setPadding(0, 0, 0, 0);
//
////        webview.loadUrl("file:///android_asset/youtube.html");
//
////        webview.loadUrl("http://www.youtube.com/embed/" + "voBuLsstp2s" + "?autoplay=1&vq=small");
//        webview.loadData('<iframe width="560" height="315" src="https://www.youtube.com/embed/hBlO1i_WTiY" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>', "text/html", "utf-8");
//        webview.setWebChromeClient(new WebChromeClient());

        getVideo();
//        youTubePlayerFragment = new YouTubePlayerSupportFragment();
//        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment);
//        fragmentTransaction.commit();

//        youTubeView = (YouTubePlayerFragment) view.findViewById(R.id.youtubesupportfragment);
//        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

//        mYoutubeVideoTitle = (TextView)fragmentYoutubeView.findViewById(R.id.fragment_youtube_title);
//        mYoutubeVideoDescription = (TextView)fragmentYoutubeView.findViewById(R.id.fragment_youtube_description);
//
//        mYoutubeVideoTitle.setText(getArguments().getString(Resources.KEY_VIDEO_TITLE));
//        mYoutubeVideoDescription.setText(getArguments().getString(Resources.KEY_VIDEO_DESC));
//
//        VideoFragment.setTextToShare(getArguments().getString(Resources.KEY_VIDEO_URL));
//        youTubePlayerFragment = (YouTubePlayerFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.youtubesupportfragment);
//        getChildFragmentManager().getFragment()
//        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
////        val youTubePlayerFragment = supportFragmentManager.findFragmentById(R.id.youtube_fragment) as YouTubePlayerSupportFragment;
//        FragmentTransaction transaction = (YouTubePlayerSupportFragment)  youTubePlayerFragment.findFragmentById(R.id.youtubesupportfragment);
//        transaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment).commit();

//        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
//        supportMapFragment = (YouTubePlayerSupportFragment) fm.findFragmentById(R.id.youtubesupportfragment);
//        if (supportMapFragment == null) {
//            supportMapFragment = YouTubePlayerSupportFragment.newInstance();
//            fm.beginTransaction().replace(R.id.youtubesupportfragment, supportMapFragment).commit();
//        }
////        supportMapFragment.getMapAsync(getActivity());
//        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);

//        YouTubePlayerFragment youtubePlayerFragment = new YouTubePlayerFragment();
//        youtubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
////        FragmentManager fragmentManager = setTargetFragment(R.id.youtubesupportfragment);
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.youtubesupportfragment, youtubePlayerFragment);
//        fragmentTransaction.commit();

        return view;
    }

    private void getVideo() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String state = sharedPref.getString("state", "");
        String city = sharedPref.getString("city", "");
        String pincode = sharedPref.getString("zipcode", "");
        VollyServerCall controller = new VollyServerCall();
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
                            if(location.length() > 0){
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        youTubePlayerFragment = new YouTubePlayerSupportFragment();
        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment);
        fragmentTransaction.commit();

//        youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager()
//                .findFragmentById(R.id.youtubesupportfragment);

//        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
//        FragmentTransaction transaction =  getChildFragmentManager().beginTransaction();
//        transaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment).commit();
//        if (youTubePlayerFragment == null)
//            return;
//
////        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
////        supportMapFragment = (YouTubePlayerSupportFragment) fm.findFragmentById(R.id.youtubesupportfragment);
////        if (supportMapFragment == null) {
////            supportMapFragment = YouTubePlayerSupportFragment.newInstance();
////            fm.beginTransaction().replace(R.id.youtubesupportfragment, supportMapFragment).commit();
////        }
////        supportMapFragment.getMapAsync(getActivity());
//        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
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

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
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
        }
    }
}
