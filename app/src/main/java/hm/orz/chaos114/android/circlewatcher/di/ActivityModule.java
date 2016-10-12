package hm.orz.chaos114.android.circlewatcher.di;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class ActivityModule {

    @Provides
    public Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
