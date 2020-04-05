package com.example.covid_19;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private YouTubePlayerFragment supportMapFragment;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private WebView webview;

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
        View view = inflater.inflate(R.layout.fragment_video, container, false);

//        webview = new WebView(getContext());
//
//        final WebSettings settings = webview.getSettings();
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
//        webview.loadUrl("http://www.youtube.com/embed/" + "voBuLsstp2s" + "?autoplay=1&vq=small");
//        webview.setWebChromeClient(new WebChromeClient());

        youTubePlayerFragment = new YouTubePlayerSupportFragment();
        youTubePlayerFragment.initialize(Config.YOUTUBE_API_KEY, this);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtubesupportfragment, youTubePlayerFragment);
        fragmentTransaction.commit();

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
            youTubePlayer.cueVideo("voBuLsstp2s"); // Plays https://www.youtube.com/watch?v=-epZ12OclMg
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
}
