package hm.orz.chaos114.android.circlewatcher.entity;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * CircleCI's build class.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Build extends RealmObject {
    private String vcsUrl;
    @PrimaryKey
    private String buildUrl;
    private int buildNum;
    private String branch;
    private String vcsRevision;
    private String committerName;
    private String committerEmail;
    private String subject;
    private String body;
    private String why;
    private String dontBuild;
    private Date queuedAt;
    private Date startTime;
    private Date stopTime;
    private long buildTimeMillis;
    private String username;
    private String reponame;
    private String lifecycle; // :queued, :scheduled, :not_run, :not_running, :running or :finished
    private String outcome; // :canceled, :infrastructure_fail, :timedout, :failed, :no_tests or :success
    private String status; // :retried, :canceled, :infrastructure_fail, :timedout, :not_run, :running, :failed, :queued, :scheduled, :not_running, :no_tests, :fixed, :success
    private String retryOf;
}
