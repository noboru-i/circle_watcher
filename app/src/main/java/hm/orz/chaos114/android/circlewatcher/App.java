package hm.orz.chaos114.android.circlewatcher;

import android.app.Application;

import hm.orz.chaos114.android.circlewatcher.di.AppComponent;
import hm.orz.chaos114.android.circlewatcher.di.AppModule;
import hm.orz.chaos114.android.circlewatcher.di.DaggerAppComponent;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import lombok.Getter;
import timber.log.Timber;

/**
 * Application class.
 */
public class App extends Application {

    @Getter
    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            // TODO use in the future.
//            Timber.plant(new FirebaseTree());
        }

        initializeInjector();
        initializeRealm();
    }

    private void initializeInjector() {
        applicationComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initializeRealm() {
        Realm.init(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.deleteRealm(realmConfig);
        Realm.setDefaultConfiguration(realmConfig);
    }
}
