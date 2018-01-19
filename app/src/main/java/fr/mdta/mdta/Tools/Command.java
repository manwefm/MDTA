package fr.mdta.mdta.Tools;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import fr.mdta.mdta.API.Callback.Callback;

/**
 * Created by manwefm on 04/12/17.
 */

/**
 * https://stackoverflow.com/questions/11451768/call-asynctask-from-static-class
 */
public class Command extends AsyncTask<String, Void, String> {
    private Callback callback;
    private List<String> suResult = null;
    private String[] command = null;

    public Command() {
    }

    public Command(Callback callback) {
        this.callback = callback;
    }

    public Command(Callback callback, String[] command) {
        this.callback = callback;
        this.command = command;
    }

    public Command(String[] command) {
        this.command = command;
    }

    protected void setCallback(Callback callback) {
        this.callback = callback;
    }

    protected List<String> getSuResult() {
        return suResult;
    }

    protected String[] getCommand() {
        return command;
    }

    @Override
    protected void onPreExecute() {
        // We're creating a progress dialog here because we want the user to wait.
        // If in your app your user can just continue on with clicking other things,
        // don't do the dialog thing.

/*        dialog = new ProgressDialog(context);
        dialog.setTitle("Executing shell command");
        dialog.setMessage("Please wait");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();*/
    }

    @Override
    protected String doInBackground(String... params) {
        // Let's do some SU stuff
        boolean suAvailable = Shell.SU.available();
        if (suAvailable) {
            suResult = Shell.SU.run(params);
        }
        //Log.d("Command", params[0]);
        //Log.d("Command", suResult.toString());
        return suResult.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        //dialog.dismiss();

        StringBuilder sb = (new StringBuilder()).append((char) 10);
        if (suResult != null) {
            for (String line : suResult) {
                sb.append(line).append((char) 10);
            }
        } else {
            sb.append("Error");
        }

        if ( callback != null ) {
            callback.OnTaskCompleted(sb.toString());
        }
    }
}
