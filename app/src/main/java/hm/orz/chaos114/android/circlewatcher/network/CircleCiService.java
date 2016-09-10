package hm.orz.chaos114.android.circlewatcher.network;

import hm.orz.chaos114.android.circlewatcher.entity.User;
import retrofit2.http.GET;
import rx.Observable;

/**
 * API interface for CircleCI.
 */
public interface CircleCiService {

    @GET("me")
    Observable<User> getUser();
}
