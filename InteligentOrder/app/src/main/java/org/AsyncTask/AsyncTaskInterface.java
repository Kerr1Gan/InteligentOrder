package org.AsyncTask;

/**
 * Created by KerriGan on 2015/8/4.
 */
public interface AsyncTaskInterface {
    public void onLoading(Task task);
    public void onLoaded(Task task);
    public void onLoadFailed(Task task);
    public Object doHttpRequestMethod(Task task);
}
