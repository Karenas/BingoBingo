package com.gmself.studio.mg.basemodule.utils.zipUtil;

import com.gmself.mg.a7zip.Zip7Suffix;

/**
 * Created by guomeng on 4/27.
 *
 *  actionTag 行为标志，true为压缩，false 为解压缩
 *
 * */

public class ZipSeed {

    private String filesPath;

    private String zipFileAbsolute;

    private String password;

    private Zip7Suffix suffix;

    private boolean actionTag;

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public void setZipFileAbsolute(String zipFileAbsolute) {
        this.zipFileAbsolute = zipFileAbsolute;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSuffix(Zip7Suffix suffix) {
        this.suffix = suffix;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public String getZipFileAbsolute() {
        return zipFileAbsolute;
    }

    public String getPassword() {
        return password;
    }

    public Zip7Suffix getSuffix() {
        return suffix;
    }

    public boolean isActionTag() {
        return actionTag;
    }

    public void setActionTag(boolean actionTag) {
        this.actionTag = actionTag;
    }

    public static class Builder{
        private ZipSeed zipSeed;

        private String filesPath;
        private String zipFileAbsolute;
        private String password;
        private Zip7Suffix suffix;
        private Boolean actionTag;

        public Builder(String zipFileAbsolute, String filesPath, boolean actionTag){
            zipSeed = new ZipSeed();
            this.filesPath = filesPath;
            this.zipFileAbsolute = zipFileAbsolute;
            this.actionTag = actionTag;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder suffix(Zip7Suffix suffix){
            this.suffix = suffix;
            return this;
        }

        public ZipSeed build(){
            zipSeed.setFilesPath(filesPath);
            zipSeed.setZipFileAbsolute(zipFileAbsolute);
            zipSeed.setPassword(password);
            zipSeed.setSuffix(suffix);
            zipSeed.setActionTag(actionTag);
            return zipSeed;
        }
    }

}
