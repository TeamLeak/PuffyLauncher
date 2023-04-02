package com.github.leanfe.updates;

public interface DownloadListener {
    public void onDownloadJobFinished(DownloadJob job);
    public void onDownloadJobProgressChanged(DownloadJob job);
    public void onDownloadJobStarted(DownloadJob job);

}
