package project.irfananda.cobamvvm.util;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by irfananda on 09/05/16.
 */
public class DataUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }


}
