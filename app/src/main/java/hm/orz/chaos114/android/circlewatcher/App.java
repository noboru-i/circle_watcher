package hm.orz.chaos114.android.circlewatcher;

import android.app.Application;

import timber.log.Timber;

/**
 * Application class.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO use in the future.
//            Timber.plant(new FirebaseTree());
        }
    }
}
