package hm.orz.chaos114.android.circlewatcher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import hm.orz.chaos114.android.circlewatcher.databinding.ActivityAuthBinding;

/**
 * A login screen that offers login via access token.
 */
public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
    }

    /**
     * Call by DataBinding.
     */
    public void attemptLogin() {

        // Reset errors.
        binding.apiToken.setError(null);

        // Store values at the time of the login attempt.
        String apiToken = binding.apiToken.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(apiToken)) {
            binding.apiToken.setError(getString(R.string.error_field_required));
            focusView = binding.apiToken;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            showProgress(true);

            // TODO execute API
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

