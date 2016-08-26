package com.codingforcookies.betterrecords.common.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

public class CurseModInfo {
    private String title;
    private String game;
    private String category;
    private String url;
    private String thumbnail;
    private String[] authors;
    private int favorites;
    private int likes;
    private String updated_at;
    private String created_at;
    private String project_url;
    private String release_type;
    private String license;
    private VersionInfo download;
    private Map<String, VersionInfo[]> versions;

    private static Gson gson = new Gson();

    public static CurseModInfo fromURL(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        return gson.fromJson(reader, CurseModInfo.class);
    }

    public VersionInfo[] getVersions(String mcVersion) {
        return versions.get(mcVersion);
    }

    public VersionInfo getNewestVersion(String mcVersion) {
        VersionInfo newest = null;
        for (VersionInfo versionInfo : getVersions(mcVersion)) {
            if (newest == null || versionInfo.getId() > newest.getId()) {
                newest = versionInfo;
            }
        }
        return newest;
    }

    public String getTitle() {
        return title;
    }

    public String getGame() {
        return game;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String[] getAuthors() {
        return authors;
    }

    public int getFavorites() {
        return favorites;
    }

    public int getLikes() {
        return likes;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getProject_url() {
        return project_url;
    }

    public String getRelease_type() {
        return release_type;
    }

    public String getLicense() {
        return license;
    }

    public VersionInfo getDownload() {
        return download;
    }

    public class VersionInfo {
        private int id;
        private String url;
        private String name;
        private String type;
        private String version;
        private int downloads;
        private String created_at;

        public String getModVersion() {
            return name.toLowerCase().replaceAll("betterrecords-|.jar", "");
        }

        public int getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getMinecraftVersion() {
            return version;
        }

        public int getDownloads() {
            return downloads;
        }

        public String getCreated_at() {
            return created_at;
        }
    }
}
