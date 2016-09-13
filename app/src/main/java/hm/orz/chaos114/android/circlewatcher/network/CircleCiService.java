package hm.orz.chaos114.android.circlewatcher.network;

import java.util.List;

import hm.orz.chaos114.android.circlewatcher.entity.BuildList;
import hm.orz.chaos114.android.circlewatcher.entity.User;
import hm.orz.chaos114.android.circlewatcher.entity.Build;
import retrofit2.http.GET;
import rx.Observable;

/**
 * API interface for CircleCI.
 */
public interface CircleCiService {

    @GET("me")
    Observable<User> getUser();

    @GET("recent-builds")
    Observable<BuildList> getRecentBuilds();
}
