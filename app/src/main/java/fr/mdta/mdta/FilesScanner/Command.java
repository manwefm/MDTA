package fr.mdta.mdta.FilesScanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import fr.mdta.mdta.API.Callback.Callback;
import fr.mdta.mdta.R;

/**
 * Created by manwefm on 04/12/17.
 */

/**
 * https://stackoverflow.com/questions/11451768/call-asynctask-from-static-class
 */
class Command extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog = null;
    private Callback callback;
    private Context context;
    private boolean suAvailable = false;
    private List<String> suResult = null;

    protected void setCallback(Callback callback) {
        this.callback = callback;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        // We're creating a progress dialog here because we want the user to wait.
        // If in your app your user can just continue on with clicking other things,
        // don't do the dialog thing.

        dialog = new ProgressDialog(context);
        dialog.setTitle("Executing shell command");
        dialog.setMessage("Please wait");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        // Let's do some SU stuff
        suAvailable = Shell.SU.available();
        if (suAvailable) {
            suResult = Shell.SU.run(new String[]{
                    params[0],
            });
        }
        return suResult.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();

        StringBuilder sb = (new StringBuilder()).append((char) 10);
        if (suResult != null) {
            for (String line : suResult) {
                sb.append(line).append((char) 10);
            }
        }
        else {
            sb.append("The shell command did not display anything to stdout");
        }
        TextView tv = (TextView) activity.findViewById(R.id.sample_text);
        tv.setText(sb.toString());
        Log.d("CommandFactory", sb.toString());
    }
}
