package com.github.leanfe.updates;

import com.github.leanfe.updates.json.Data;
import com.github.leanfe.util.Utils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Updater {

    private static final Gson JSON = new Gson();

    private final List<String> ignoredFiles = Collections.synchronizedList(new ArrayList<>());

    private final String url;
    private final File dir;
    private final DownloadJob job;


    /**
     * Instantiates a new Updater.
     *
     * @param url the url
     * @param dir the dir
     * @param job the job
     */
    public Updater(String url, File dir, DownloadJob job) {
        this.url = url;
        this.dir = dir;
        this.job = job;
    }

    /**
     * Start.
     */
    public void start() {
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.printf("Folder %s doesn't exist, mkdirs folder.", dir.getName());
        } else System.out.printf("Folder %s exist, start check url.", dir.getName());

        this.jsonDeserialize(this.getUrlContents(this.url));
    }

    private void jsonDeserialize(String str) {
        Data data = JSON.fromJson(str, Data.class);

        System.out.println("maintenance: " + data.getMaintenance().isMaintenance());
        System.out.println("maintenanceMessage: " + data.getMaintenance().getMessage());

        if (data.getMaintenance().isMaintenance()) {
            System.err.print("не могу бросить в основной maintenance!");
            System.exit(0);
        }

        data.getFiles().forEach(file -> {
            File client_file = new File(this.dir, file.getPath());
            if (!client_file.exists() || !client_file.isFile()) {
                this.job.addDownloadable(client_file, file.getHash(), file.getUrl());
                this.ignoredFiles.add(client_file.getAbsolutePath());
                return;
            }
            try {
                if (!Objects.equals(Utils.getMD5(client_file), file.getHash())) {
                    System.err.printf("File %s has a bad hash '%s' hash on server => %s" , client_file.getName(), Utils.getMD5(client_file), file.getHash());
                    this.job.addDownloadable(client_file, file.getHash(), file.getUrl());
                }
                this.ignoredFiles.add(client_file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        data.getIgnoreFiles().stream().map(ignore -> new File(this.dir, ignore)).forEach(file -> {
            if (file.isDirectory()) {
                ArrayList<File> files = listFiles(file);
                files.stream().map(File::getAbsolutePath).forEach(this.ignoredFiles::add);
            } else {
                this.ignoredFiles.add(file.getAbsolutePath());
            }
        });
    }

    private boolean isIgnored(File file) {
        return this.ignoredFiles.stream().anyMatch(path -> path.equals(file.getAbsolutePath()));
    }

    /**
     * Checking files.
     */
    public void checkingFiles() {
        ArrayList<File> files = listFiles(this.dir);
        files.stream().filter(file -> !isIgnored(file)).forEach(File::delete);
    }

    private ArrayList<File> listFiles(File folder) {
        File[] files = folder.listFiles();
        ArrayList<File> list = new ArrayList<>();

        if (files == null) return list;
        Arrays.stream(files).forEach(f -> {
            if (f.isDirectory()) list.addAll(listFiles(f));
            else list.add(f);
        });
        return list;
    }

    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}

