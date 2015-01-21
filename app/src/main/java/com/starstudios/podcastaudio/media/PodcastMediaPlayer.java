package com.starstudios.podcastaudio.media;

import android.os.Looper;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.TrackRenderer;

/**
 * Created by delgadem on 11/22/14.
 */
public class PodcastMediaPlayer /*extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener*/ {

//    public static final String ACTION_PLAY = "action_play";
//    public static final String ACTION_PAUSE = "action_pause";
//    public static final String ACTION_REWIND = "action_rewind";
//    public static final String ACTION_FAST_FORWARD = "action_fast_foward";
//    public static final String ACTION_NEXT = "action_next";
//    public static final String ACTION_PREVIOUS = "action_previous";
//    public static final String ACTION_STOP = "action_stop";
//
    private ExoPlayer mMediaPlayer;
//    private RecentEpisode mCurrentEpisode;
//    private final MusicBinder mMusicBinder = new MusicBinder();
//    private boolean mIsRunning = false;
//    private Bitmap mCurrentBitmap;
//    private AudioManager mAudioManager;
//    private MediaSessionManager mMediaSessionManager;
//    private MediaSessionCompat mSession;
//    private MediaControllerCompat mMediaController;
//
//
//    public void initializeExoPlayer() {
//        mMediaPlayer = ExoPlayer.Factory.newInstance(0);
//    }
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        mMediaSessionManager = (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
//        mSession = new MediaSessionCompat(this, "SampleSession");
//        mSession.setCallback(new MediaSessionCompat.Callback() {
//            @Override
//            public void onPlay() {
//                super.onPlay();
//                mMediaPlayer.start();
//                buildNotification(ACTION_PLAY, mCurrentBitmap, true);
//
//            }
//
//            @Override
//            public void onPause() {
//                super.onPause();
//                mMediaPlayer.pause();
//                buildNotification(ACTION_PAUSE, mCurrentBitmap, false);
//            }
//
//            @Override
//            public void onFastForward() {
//                super.onFastForward();
////                mMediaPlayer.seekTo();
//            }
//        });
//
//        mMediaController = new MediaControllerCompat(this, mSession);
//
//        mMediaPlayer = new MediaPlayer();
//        mMediaPlayer.setWakeMode(getApplicationContext(),
//                PowerManager.PARTIAL_WAKE_LOCK);
//        mMediaPlayer.setOnPreparedListener(this);
//        mMediaPlayer.setOnCompletionListener(this);
//        mMediaPlayer.setOnErrorListener(this);
//        mIsRunning = false;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        if (!mIsRunning) {
//            mIsRunning = !mIsRunning;
//            Log.d("Manny", "Service is now running");
//        }
//
//        handleIntent(intent);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mMusicBinder;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mCurrentEpisode = null;
//        buildNotification(null, null, false);
//    }
//
//    public void updateCurrentEpisode(RecentEpisode episode) {
//        mCurrentEpisode = episode;
//        updateMediaPlayer();
//    }
//
//    public boolean isPlaying() {
//        return mMediaPlayer.isPlaying();
//    }
//
//    private void updateMediaPlayer() {
//        try {
//            if (isPlaying()) {
//                stopCurrentPlayingItem();
//            }
//            mMediaPlayer.reset();
//
//            mMediaPlayer.setDataSource(mCurrentEpisode.audioUrl);
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void stopCurrentPlayingItem() {
//        if (mMediaPlayer.isPlaying()) {
//            mMediaPlayer.stop();
//        }
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        return super.onUnbind(intent);
//    }
//
//    private void playMedia() {
//        buildNotification(null, null, true);
//        mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//        mMediaPlayer.start();
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer mediaPlayer) {
//        playMedia();
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//    }
//
//    @Override
//    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
//        return false;
//    }
//
//    @Override
//    public void onAudioFocusChange(int focusChange) {
//        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
//            // Pause playback
//            mMediaPlayer.pause();
//        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//            // Resume playback
//            mMediaPlayer.start();
//        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
////            mAudioManager.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
//            mAudioManager.abandonAudioFocus(this);
//            // Stop playback
//        }
//    }
//
//    public class MusicBinder extends Binder {
//        public PodcastMediaPlayer getService() {
//            return PodcastMediaPlayer.this;
//        }
//    }
//
//    private void buildNotification(final String action, final Bitmap audioBitmap, final boolean isOngoing) {
//        Intent intent = new Intent(getApplicationContext(), PodcastMediaPlayer.class);
//        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
//
//        if(mCurrentEpisode == null) {
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setOngoing(false);
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            notificationManager.notify(1, builder.build());
//            return;
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle(mCurrentEpisode.author)
//                .setContentText(mCurrentEpisode.title)
//                .setDeleteIntent(pendingIntent)
//                .setStyle(new NotificationCompat
//                        .BigPictureStyle()
//                        .setSummaryText(mCurrentEpisode.title)
//                        .setBigContentTitle(mCurrentEpisode.author)
//                        .bigLargeIcon(audioBitmap)
//                        .bigPicture(audioBitmap));
//
//        if(audioBitmap == null) {
//            PodcastAudioApplication.getInstance().getImageLoader().get(mCurrentEpisode.audioImage, new ImageLoader.ImageListener() {
//                @Override
//                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
//                    if (response.getBitmap() != null) {
//                        mCurrentBitmap = response.getBitmap();
//                        buildNotification(action, response.getBitmap(), isOngoing);
//                    }
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//        } else {
//            builder.setLargeIcon(audioBitmap);
//        }
//
//        if(!TextUtils.isEmpty(action)) {
//            int ffIconId;
//            int playIconId = 0;
//            int rewIconId;
//
//            String playAction = null;
//            switch (action) {
//                case ACTION_REWIND:
//                    break;
//                case ACTION_PAUSE:
//                    playIconId = android.R.drawable.ic_media_play;
//                    playAction = ACTION_PLAY;
//                    break;
//                case ACTION_PLAY:
//                    playIconId = android.R.drawable.ic_media_pause;
//                    playAction = ACTION_PAUSE;
//                    break;
//                case ACTION_FAST_FORWARD:
//                    break;
//
//                default:
//                    break;
//            }
//
//            builder.addAction(generateAction(android.R.drawable.ic_media_rew, ACTION_REWIND));
//            builder.addAction(generateAction(playIconId, playAction));
//            builder.addAction(generateAction(android.R.drawable.ic_media_ff, ACTION_FAST_FORWARD));
//        } else {
//            builder.addAction(generateAction(android.R.drawable.ic_media_rew, ACTION_REWIND));
//            builder.addAction(generateAction(android.R.drawable.ic_media_pause, ACTION_PAUSE));
//            builder.addAction(generateAction(android.R.drawable.ic_media_ff, ACTION_FAST_FORWARD));
//        }
//        builder.setOngoing(isOngoing);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(1, builder.build());
//    }
//
//    private void handleIntent(Intent intent) {
//        if (intent == null || intent.getAction() == null)
//            return;
//
//        String action = intent.getAction();
//
//        if (action.equalsIgnoreCase(ACTION_PLAY)) {
//            mMediaController.getTransportControls().play();
//        } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {
//            mMediaController.getTransportControls().pause();
//        } else if (action.equalsIgnoreCase(ACTION_FAST_FORWARD)) {
//            mMediaController.getTransportControls().fastForward();
//        } else if (action.equalsIgnoreCase(ACTION_REWIND)) {
//            mMediaController.getTransportControls().rewind();
//        } else if (action.equalsIgnoreCase(ACTION_PREVIOUS)) {
//            mMediaController.getTransportControls().skipToPrevious();
//        } else if (action.equalsIgnoreCase(ACTION_NEXT)) {
//            mMediaController.getTransportControls().skipToNext();
//        } else if (action.equalsIgnoreCase(ACTION_STOP)) {
//            mMediaController.getTransportControls().stop();
//        }
//    }
//
//    private NotificationCompat.Action generateAction(int icon, String intentAction) {
//        Intent intent = new Intent(this, PodcastMediaPlayer.class);
//        intent.setAction(intentAction);
//        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, 0);
//        return new NotificationCompat.Action.Builder(icon, null, pendingIntent).build();
//    }
}
