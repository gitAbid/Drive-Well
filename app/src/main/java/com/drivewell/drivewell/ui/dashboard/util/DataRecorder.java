package com.drivewell.drivewell.ui.dashboard.util;
import android.content.Context;
import android.location.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataRecorder {
    private static final String EXTENSION = ".dat";
    public static String file_dir = "";
    public static boolean is_file_ready;
    private static Context m_context;
    private static FileOutputStream m_out_stream;

    public static void initialize(Context ctx) {
        m_context = ctx;
        is_file_ready = false;
        file_dir = m_context.getExternalFilesDir(null).getAbsolutePath();
        File file = new File(file_dir);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static boolean openFile() {
        try {
            Calendar c = Calendar.getInstance();
            m_out_stream = new FileOutputStream(new File(file_dir + "/" + SettingsWrapper.FILE_PREFIX + (String.valueOf(c.get(2) + 1) + "-" + String.valueOf(c.get(5)) + "-" + String.valueOf(c.get(1)) + "-" + String.valueOf(c.get(10)) + "-" + String.valueOf(c.get(12)) + "-" + String.valueOf(c.get(13)) + EXTENSION)));
            is_file_ready = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            is_file_ready = false;
            return false;
        }
    }

    public static boolean closeFile() {
        try {
            m_out_stream.close();
            is_file_ready = false;
            return true;
        } catch (IOException e) {
            is_file_ready = true;
            return false;
        }
    }

    public static boolean writeDataToFile(Data2D data, Location loc) {
        if (is_file_ready) {
            try {
                String text = String.format(Locale.US, "%.5f", new Object[]{Float.valueOf(data.right_left)}) + "\t" + String.format(Locale.US, "%.5f", new Object[]{Float.valueOf(data.acc_brake)}) + "\t" + Long.valueOf(data.timestamp / 1000000).toString() + "\t" + epochToDate(data.timestamp / 1000000) + "\t";
                if (loc == null) {
                    text = text + "null\tnull\n";
                } else {
                    text = text + String.format(Locale.US, "%.7f", new Object[]{Double.valueOf(loc.getLatitude())}) + "\t" + String.format(Locale.US, "%.7f", new Object[]{Double.valueOf(loc.getLongitude())}) + "\n";
                }
                m_out_stream.write(text.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static ArrayList<String> getFiles() {
        ArrayList<String> result = new ArrayList();
        File dir = new File(file_dir);
        if (dir.isDirectory()) {
            for (String str : dir.list()) {
                result.add(str);
            }
        }
        return result;
    }

    public static boolean deleteFile(String name) {
        File f = new File(file_dir + "/" + name);
        if (!f.exists()) {
            return false;
        }
        f.delete();
        return true;
    }

    public static Data2DCache loadDataFromFile(String name) {
        Data2DCache result = new Data2DCache(0);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file_dir + "/" + name)));
            String line = reader.readLine();
            do {
                String[] fields = line.split("\t");
                if (fields.length > 2) {
                    result.addData(new Data2D(Float.valueOf(fields[0]).floatValue(), Float.valueOf(fields[1]).floatValue(), Long.valueOf(fields[2]).longValue()));
                }
                line = reader.readLine();
            } while (line != null);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String epochToDate(long epoch) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
        format.setTimeZone(TimeZone.getDefault());
        return format.format(date);
    }
}
