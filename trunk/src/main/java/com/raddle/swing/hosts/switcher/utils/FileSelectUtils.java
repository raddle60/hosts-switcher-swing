package com.raddle.swing.hosts.switcher.utils;

import java.io.File;

import javax.swing.JFileChooser;

import org.apache.commons.lang.StringUtils;

public class FileSelectUtils {

    public static File selectFile(String defaultPath, String[] extensions) {
        String defaultDir = null;
        if (StringUtils.isNotEmpty(defaultPath)) {
            if (new File(defaultPath).exists()) {
                File defaultFile = new File(defaultPath);
                if (defaultFile.isDirectory()) {
                    defaultDir = defaultFile.getAbsolutePath();
                } else {
                    defaultDir = defaultFile.getParentFile().getAbsolutePath();
                }
            }
        }
        JFileChooser chooser = new JFileChooser(defaultDir);
        int ret = chooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }
}
