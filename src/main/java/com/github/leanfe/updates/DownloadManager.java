package com.github.leanfe.updates;

import java.io.File;

public record DownloadManager(String url, DownloadJob job, File file) {}
