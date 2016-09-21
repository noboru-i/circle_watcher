package hm.orz.chaos114.android.circlewatcher.di;

import dagger.Subcomponent;
import hm.orz.chaos114.android.circlewatcher.di.scope.ActivityScope;
import hm.orz.chaos114.android.circlewatcher.modules.auth.AuthActivity;
import hm.orz.chaos114.android.circlewatcher.modules.main.BuildFragment;

@Subcomponent(modules = ActivityModule.class)
@ActivityScope
public interface ActivityComponent {
    void inject(AuthActivity activity);

    void inject(BuildFragment fragment);
}
