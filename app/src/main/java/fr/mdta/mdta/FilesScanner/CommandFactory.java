package fr.mdta.mdta.FilesScanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import fr.mdta.mdta.API.Callback.Callback;

/**
 * Created by manwefm on 04/12/17.
 */

public final class CommandFactory {

    public static ArrayList<Command> listProcess = new ArrayList<Command>();

    static final int MAX_PROCESS = 5;

    static int COUNT = 0;

    static String pathToApkUnzipFolder = "/data/local";
    static String unzipApkToFolder = "unzipedApk";

    public static void execCommand(String[] command, Callback callback, Context context) {
        Command exec_command = new Command(callback, context, command);
        exec_command.execute(command);
    }

    public static void unzipCommand(Callback callback, Context context, ApplicationInfo app, int
            my_uid) {

        String[] listCommand = new String[]{
                "cd " + pathToApkUnzipFolder,
                "rm -rRf " + pathToApkUnzipFolder + unzipApkToFolder + "_" + Integer.toString
                        (app.uid),
                "mkdir -p " + pathToApkUnzipFolder + unzipApkToFolder + "_" + Integer
                        .toString(app.uid),
                "unzip " + app.sourceDir + " -d " + pathToApkUnzipFolder + unzipApkToFolder + "_"
                        + Integer.toString(app.uid),
                "chown -R " + my_uid + ":" + my_uid + " " + pathToApkUnzipFolder +
                        unzipApkToFolder + "_" + Integer.toString(app.uid)

        };

        Log.d("CommandFactory",listCommand[0]);
        Log.d("CommandFactory",listCommand[1]);
        Log.d("CommandFactory",listCommand[2]);
        Log.d("CommandFactory",listCommand[3]);
        Log.d("CommandFactory",listCommand[4]);

        Command exec_command = new Command(callback, context, listCommand);
        exec_command.execute(listCommand);
    }

    public static void endScanApp(Callback callback, Context context, ApplicationInfo app) {

        String[] listCommand = new String[]{
                "cd /data/local",
                "rm -rRf " + pathToApkUnzipFolder + unzipApkToFolder + "_" + Integer.toString(app
                        .uid)
        };

        Command exec_command = new Command(callback, context, listCommand);
        exec_command.execute(listCommand);
    }

    public static void addCommandToExecute(final String[] command, Context context, Callback
            callback) {
        Command exec_command = new Command(callback, context, command);
        listProcess.add(exec_command);
    }

    public static void removeCommand(String[] command) {
        for (int i = 0; i < listProcess.size(); i++) {
            if (listProcess.get(i).getCommand().equals(command)) {
                listProcess.remove(i);
                return;
            }
        }
    }

    public static void cancelCommand(String[] command) {
        for (int i = 0; i < listProcess.size(); i++) {
            if (listProcess.get(i).getCommand().equals(command)) {
                listProcess.get(i).cancel(true);
                return;
            }
        }
    }

    public static void addCommand(Command command) {
        listProcess.add(command);
    }

    public static void launchVerification(Callback callback, ApplicationInfo app) {

        COUNT = 0;
        for (int i = 0; i < listProcess.size(); i++) {
            if (COUNT < MAX_PROCESS && listProcess.get(i).getStatus() == AsyncTask.Status.PENDING) {
                listProcess.get(i).execute(listProcess.get(i).getCommand());
                COUNT += 1;
            } else if (listProcess.isEmpty()) {
                callback.OnTaskCompleted(app);
            } else {
                return;
            }
        }
    }

}
