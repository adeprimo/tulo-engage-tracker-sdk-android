package adeprimo.com.tuloengagetracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import adeprimo.com.tuloengagetracker.utils.Logger;


public class Dispatcher {

    private static final String TAG = Dispatcher.class.getSimpleName();

    private final String mEventUrl;
    private long mTimeout = 5 * 1000;

    private static GsonToJsonConverter jsonConverter = new GsonToJsonConverter();

    public Dispatcher(final String eventUrl) {
        mEventUrl = eventUrl;
    }

    public void send (Event event) {

        final String data = jsonConverter.serialize(event);
        Logger.debug(TAG,"Event Data: %s", data);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = null;

                    urlConnection = (HttpURLConnection) new URL(mEventUrl).openConnection();
                    urlConnection.setConnectTimeout((int) mTimeout);
                    urlConnection.setReadTimeout((int) mTimeout);

                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("charset", "utf-8");

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                        writer.write(data);
                    } finally {
                        if (writer != null) {
                            try {
                                writer.close();
                            } catch (IOException e) {
                                Logger.error(TAG, "Failed to close output stream: ", e.getMessage());
                            }
                        }
                    }

                    int statusCode = urlConnection.getResponseCode();

                    final boolean success = statusCode == HttpURLConnection.HTTP_CREATED;
                    if (!success) {
                        final StringBuilder errorReason = new StringBuilder();
                        BufferedReader errorReader = null;
                        try {
                            errorReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                            String line;
                            while ((line = errorReader.readLine()) != null)
                                errorReason.append(line);
                        } finally {
                            if (errorReader != null) {
                                try {
                                    errorReader.close();
                                } catch (IOException e) {
                                    Logger.error(TAG, "Failed to close error stream: ", e.getMessage());
                                }
                            }
                        }
                        Logger.warning(TAG,"Transmission failed " + errorReason.toString());
                    }
                    //return success;

                } catch (Exception e) {
                    Logger.error(TAG, "Failed transmission: ", e.getMessage());
                    //return false;
                } finally {
                    if (urlConnection != null) urlConnection.disconnect();
                }
            }
        });
        thread.start();
    }
}
