package hm.orz.chaos114.android.circlewatcher.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    ActivityComponent activityComponent();
}
