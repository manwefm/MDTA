package fr.mdta.mdta.FilesScanner;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by manwefm on 04/12/17.
 */

public final class CommandFactory {

    public static void execCommand (String command, Context context) {
        Command ls = new Command();
        ls.setContext(context);
        ls.execute(command);
    }

}
