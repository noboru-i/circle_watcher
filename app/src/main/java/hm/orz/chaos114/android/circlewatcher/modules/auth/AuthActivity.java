package hm.orz.chaos114.android.circlewatcher.modules.auth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import hm.orz.chaos114.android.circlewatcher.App;
import hm.orz.chaos114.android.circlewatcher.R;
import hm.orz.chaos114.android.circlewatcher.databinding.ActivityAuthBinding;
import hm.orz.chaos114.android.circlewatcher.modules.main.MainActivity;
import hm.orz.chaos114.android.circlewatcher.network.ApiClient;
import hm.orz.chaos114.android.circlewatcher.network.CircleCiService;
import hm.orz.chaos114.android.circlewatcher.util.SharedPreferenceUtil;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A login screen that offers login via access token.
 */
public class AuthActivity extends AppCompatActivity {

    @Inject
    CircleCiService circleCiService;

    private ActivityAuthBinding binding;

    public static Intent getIntent(Context context) {
        return new Intent(context, AuthActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        binding.setHandlers(this);

        ((App)getApplicationContext()).getApplicationComponent().activityComponent().inject(this);
    }

    /**
     * Call by DataBinding.
     */
    public void attemptLogin() {
        // Reset errors.
        binding.apiToken.setError(null);

        // Store values at the time of the login attempt.
        String apiToken = binding.apiToken.getText().toString();

        boolean hasError = false;
        View focusView = null;

        if (TextUtils.isEmpty(apiToken)) {
            binding.apiToken.setError(getString(R.string.error_field_required));
            focusView = binding.apiToken;
            hasError = true;
        }

        if (hasError) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            SharedPreferenceUtil.saveApiToken(this, apiToken);
            circleCiService.getUser()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> {
                        showProgress(false);

                        Toast.makeText(this, "hello \"" + user.getName() + "\"", Toast.LENGTH_SHORT).show();

                        startActivity(MainActivity.getIntent(this));
                        finish();
                    }, throwable -> {
                        showProgress(false);

                        if (throwable instanceof HttpException) {
                            HttpException exception = (HttpException) throwable;
                            if (exception.code() == 401) {
                                Toast.makeText(this, "invalid API Token", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        Toast.makeText(this, "Sorry, some error occured.", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        binding.apiTokenLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        binding.apiTokenLoginForm.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.apiTokenLoginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.loginProgress.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
}

