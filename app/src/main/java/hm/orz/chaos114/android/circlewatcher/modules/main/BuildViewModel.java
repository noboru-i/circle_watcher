package hm.orz.chaos114.android.circlewatcher.modules.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import java.util.Locale;

import hm.orz.chaos114.android.circlewatcher.R;
import hm.orz.chaos114.android.circlewatcher.entity.Build;

public class BuildViewModel {
    private Context context;
    private Build build;

    BuildViewModel(Context context, Build build) {
        this.context = context;
        this.build = build;
    }

    public String getDisplayName() {
        return String.format(Locale.US, "%s / %s / %s #%d", build.getUsername(), build.getReponame(), build.getBranch(), build.getBuildNum());
    }

    public String getCommitMessage() {
        return build.getSubject();
    }

    public Drawable getStatusColor() {
        @DrawableRes int drawable;
        // :retried, :canceled, :infrastructure_fail, :timedout, :not_run, :running, :failed, :queued, :scheduled, :not_running, :no_tests, :fixed, :success
        switch (build.getStatus()) {
            case "retried":
            case "canceled":
            case "not_run":
                drawable = R.drawable.statusStop;
                break;
            case "running":
                drawable = R.drawable.statusBusy;
                break;
            case "failed":
                drawable = R.drawable.statusFail;
                break;
            case "scheduled":
            case "not_running":
                drawable = R.drawable.statusQueued;
                break;
            case "fixed":
            case "success":
                drawable = R.drawable.statusPass;
                break;
            default:
                return null;
        }
        return ContextCompat.getDrawable(context, drawable);
    }
}
