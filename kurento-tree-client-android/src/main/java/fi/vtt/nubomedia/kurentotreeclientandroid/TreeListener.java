package fi.vtt.nubomedia.kurentotreeclientandroid;

public interface TreeListener {

    /**
     *  Notification method names
     */
    public static final String METHOD_ICE_CANDIDATE = "iceCandidate";


    public void onTreeResponse(TreeResponse response);

    public void onTreeError(TreeError error);

    public void onTreeNotification(TreeNotification notification);

    /**
     * The connection to room is ready.
     */
    public void onTreeConnected();

    /**
     * The connection to room is lost or disconnected.
     */
    public void onTreeDisconnected();

}
