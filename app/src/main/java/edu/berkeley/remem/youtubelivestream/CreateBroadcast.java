package edu.berkeley.remem.youtubelivestream;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by root on 11/24/14.
 */
public class CreateBroadcast {

    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = new GsonFactory();
    String BROADCAST_TITLE = "test-bcast";
    GoogleAccountCredential googleCredential;
    Collection<String> scopes = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/youtube", "https://www.googleapis.com/auth/youtube.upload",
            "https://www.googleapis.com/auth/youtube.readonly", "https://www.googleapis.com/auth/youtubepartner",
            "https://www.googleapis.com/auth/youtubepartner-channel-audit");
    String scope =
            "oauth2:https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/youtube https://www.googleapis.com/auth/youtube.upload https://www.googleapis.com/auth/youtube.readonly https://www.googleapis.com/auth/youtubepartner https://www.googleapis.com/auth/youtubepartner-channel-audit";



    public UserRecoverableAuthIOException start(GoogleAccountCredential gCred) {

        googleCredential = gCred;
        System.out.println(googleCredential);


        try {
            YouTube youtube = new YouTube.Builder(transport, jsonFactory, googleCredential)
                    .setApplicationName("LiveStream").build();




            //ASK THE USER FOR THE TITLE
            String title = BROADCAST_TITLE;
            System.out.println("You chose " + title + " for broadcast title.");

            LiveBroadcastSnippet broadcastSnippet = new LiveBroadcastSnippet();
            broadcastSnippet.setTitle(title);
            broadcastSnippet.setScheduledStartTime(new DateTime("2024-01-30T00:00:00.000Z"));
            broadcastSnippet.setScheduledEndTime(new DateTime("2024-01-31T00:00:00.000Z"));

            LiveBroadcastContentDetails contentDetails = new LiveBroadcastContentDetails();
            MonitorStreamInfo monitorStream = new MonitorStreamInfo();
            monitorStream.setEnableMonitorStream(false);
            contentDetails.setMonitorStream(monitorStream);

            LiveBroadcastStatus status = new LiveBroadcastStatus();
            status.setPrivacyStatus("unlisted");

            LiveBroadcast broadcast = new LiveBroadcast();
            broadcast.setKind("youtube#liveBroadcast");
            broadcast.setSnippet(broadcastSnippet);
            broadcast.setStatus(status);
            broadcast.setContentDetails(contentDetails);



            // Construct and execute the API request to insert the broadcast.
            YouTube.LiveBroadcasts.Insert liveBroadcastInsert =
                    youtube.liveBroadcasts().insert("snippet,status,contentDetails", broadcast);

            LiveBroadcast returnedBroadcast = liveBroadcastInsert.execute();
            /*
            // Print information from the API response.
            System.out.println("\n================== Returned Broadcast ==================\n");
            System.out.println("  - Id: " + returnedBroadcast.getId());
            System.out.println("  - Title: " + returnedBroadcast.getSnippet().getTitle());
            System.out.println("  - Description: " + returnedBroadcast.getSnippet().getDescription());
            System.out.println("  - Published At: " + returnedBroadcast.getSnippet().getPublishedAt());
            System.out.println(
                    "  - Scheduled Start Time: " + returnedBroadcast.getSnippet().getScheduledStartTime());
            System.out.println(
                    "  - Scheduled End Time: " + returnedBroadcast.getSnippet().getScheduledEndTime());

            // Prompt the user to enter a title for the video stream.
            title = getStreamTitle();
            System.out.println("You chose " + title + " for stream title.");

            // Create a snippet with the video stream's title.
            LiveStreamSnippet streamSnippet = new LiveStreamSnippet();
            streamSnippet.setTitle(title);

            // Define the content distribution network settings for the
            // video stream. The settings specify the stream's format and
            // ingestion type. See:
            // https://developers.google.com/youtube/v3/live/docs/liveStreams#cdn
            CdnSettings cdnSettings = new CdnSettings();
            cdnSettings.setFormat("1080p");
            cdnSettings.setIngestionType("rtmp");

            LiveStream stream = new LiveStream();
            stream.setKind("youtube#liveStream");
            stream.setSnippet(streamSnippet);
            stream.setCdn(cdnSettings);

            // Construct and execute the API request to insert the stream.
            YouTube.LiveStreams.Insert liveStreamInsert =
                    youtube.liveStreams().insert("snippet,cdn", stream);
            LiveStream returnedStream = liveStreamInsert.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Stream ==================\n");
            System.out.println("  - Id: " + returnedStream.getId());
            System.out.println("  - Title: " + returnedStream.getSnippet().getTitle());
            System.out.println("  - Description: " + returnedStream.getSnippet().getDescription());
            System.out.println("  - Published At: " + returnedStream.getSnippet().getPublishedAt());

            // Construct and execute a request to bind the new broadcast
            // and stream.
            YouTube.LiveBroadcasts.Bind liveBroadcastBind =
                    youtube.liveBroadcasts().bind(returnedBroadcast.getId(), "id,contentDetails");
            liveBroadcastBind.setStreamId(returnedStream.getId());
            returnedBroadcast = liveBroadcastBind.execute();

            // Print information from the API response.
            System.out.println("\n================== Returned Bound Broadcast ==================\n");
            System.out.println("  - Broadcast Id: " + returnedBroadcast.getId());
            System.out.println(
                    "  - Bound Stream Id: " + returnedBroadcast.getContentDetails().getBoundStreamId());

        */
        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();

        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }

        return null;
    }
}