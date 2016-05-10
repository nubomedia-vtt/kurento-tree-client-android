package fi.vtt.nubomedia.kurentotreeclientandroid;

import android.util.Log;

import net.minidev.json.JSONObject;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Vector;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcNotification;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcResponse;
import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcWebSocketClient;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;


public class KurentoTreeAPI extends KurentoAPI {
    private static final String LOG_TAG = "KurentoTreeAPI";
    private KeyStore keyStore;
    private boolean usingSelfSigned = false;
    private Vector<TreeListener> listeners;

    /**
     * Constructor that initializes required instances and parameters for the API calls.
     * WebSocket connections are not established in the constructor. User is responsible
     * for opening, closing and checking if the connection is open through the corresponding
     * API calls.
     *
     * @param executor is the asynchronous UI-safe executor for tasks.
     * @param uri is the web socket link to the tree web services.
     * @param listener interface handles the callbacks for responses, notifications and errors.
     */
    public KurentoTreeAPI(LooperExecutor executor, String uri, TreeListener listener){
        super(executor, uri);

        listeners = new Vector<TreeListener>();
        listeners.add(listener);

        // Create a KeyStore containing our trusted CAs
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * The method represents client's request to create a new tree in Tree server.
     * The response contains attributes "value" and "sessionId", where value is the name
     * of the created tree.
     *
     * @param treeId is the name of the tree to be created.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendCreateTree(String treeId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        send("createTree", namedParameters, id);
    }

    /**
     * The method represents a request to configure the emitter (source) in a broadcast
     * session (tree). The response contains attributes "sdpAnswer" and "sessionId".
     *
     * @param treeId is the name of the tree this method refers to.
     * @param offerSdp is the SDP offer sent by this client.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendSetTreeSource(String treeId, String offerSdp, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("offerSdp", offerSdp);
        send("setTreeSource", namedParameters, id);
    }

    /**
     * The method requests to remove the current emitter of a tree.
     * The response includes the following parameters: sessionId
     *
     * @param treeId is the name of the tree this method refers to.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendRemoveTreeSource(String treeId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        send("removeTreeSource", namedParameters, id);
    }

    /**
     * The method requests to add a new viewer (sink) to the tree.
     *  The response includes the following parameters: sdpAnswer, sinkId, and sessionId.
     *
     * @param treeId is the name of the tree this method refers to.
     * @param offerSdp is the SDP offer sent by this client.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendAddTreeSink(String treeId, String offerSdp, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("offerSdp", offerSdp);
        send("addTreeSink", namedParameters, id);
    }

    /**
     * The method requests to remove a previously connected sink (viewer).
     * The response includes the following parameters: sessionId
     *
     * @param treeId  is the name of the tree this method refers to.
     * @param sinkId is the name of the previously connected sink.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendRemoveTreeSink(String treeId, String sinkId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        namedParameters.put("sinkId", sinkId);
        send("removeTreeSink", namedParameters, id);
    }


    /**
     * The method to request to add a new ice candidate.
     * The response includes the following parameters: sessionId
     *
     * @param treeId  is the name of the tree this method refers to.
     * @param sinkId is the name of the previously connected sink.
     * @param sdpMid is the media stream identification, "audio" or "video", for the m-line.
     * @param sdpMLineIndex is the index (starting at 0) of the m-line in the SDP
     * @param candidate contains the candidate attribute information
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendAddIceCandidate(String treeId,String sinkId, String sdpMid, int sdpMLineIndex, String candidate, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        if (sinkId != null) {
            namedParameters.put("sinkId", sinkId);
        }
        namedParameters.put("sdpMid", sdpMid);
        namedParameters.put("sdpMLineIndex", new Integer(sdpMLineIndex));
        namedParameters.put("candidate", candidate);
        send("addIceCandidate", namedParameters, id);
    }

    /**
     * The method to request to remove a tree.
     * The response includes the following parameters: sessionId
     *
     * @param treeId  is the name of the tree this method refers to.
     * @param id is an index number to track the corresponding response message to this request.
     */
    public void sendRemoveTree(String treeId, int id){
        HashMap<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("treeId", treeId);
        send("removeTree", namedParameters, id);
    }

    /**
     * This methods can be used to add a self-signed SSL certificate to be trusted when establishing
     * connection.
     * @param alias is a unique alias for the certificate
     * @param cert is the certificate object
     */
    public void addTrustedCertificate(String alias, Certificate cert){
        try {
            keyStore.setCertificateEntry(alias, cert);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches on/off the self-signed certificate support.
     *
     * @see KurentoTreeAPI#addTrustedCertificate(String, Certificate)
     * @param use
     */
    public void useSelfSignedCertificate(boolean use){
        this.usingSelfSigned = use;
    }

    /**
     * Opens a web socket connection to the predefined URI as provided in the constructor.
     * The method responds immediately, whether or not the connection is opened.
     * The method isWebSocketConnected() should be called to ensure that the connection is open.
     * Secure socket is created if protocol contained in Uri is either https or wss.
     */
    public void connectWebSocket() {
        if(isWebSocketConnected()){
            return;
        }

        // Switch to SSL web socket client factory if secure protocol detected
        String scheme = null;
        try {
            scheme = new URI(wsUri).getScheme();
            if (scheme.equals("https") || scheme.equals("wss")){

                // Create an SSLContext that uses our or default TrustManager
                SSLContext sslContext = SSLContext.getInstance("TLS");

                if (usingSelfSigned) {
                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);
                    sslContext.init(null, tmf.getTrustManagers(), null);
                } else {
                    sslContext.init(null, null, null);
                }
                webSocketClientFactory = new DefaultSSLWebSocketClientFactory(sslContext);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        super.connectWebSocket();
    }

        /* WEB SOCKET CONNECTION EVENTS */


    /**
     * Callback method that relays the TreeResponse or TreeError to the TreeListener interface.
     */
    @Override
    public void onResponse(JsonRpcResponse response) {
        if(response.isSuccessful()){
            JSONObject jsonObject = (JSONObject)response.getResult();
            TreeResponse treeResponse = new TreeResponse(response.getId().toString(), jsonObject);
            synchronized (listeners) {
                for (TreeListener tl : listeners) {
                    tl.onTreeResponse(treeResponse);
                }
            }
        } else {
            TreeError treeError = new TreeError(response.getError());
            synchronized (listeners) {
                for (TreeListener tl : listeners) {
                    tl.onTreeError(treeError);
                }
            }
        }
    }

    /**
     * Callback method that relays the TreeNotification to the TreeListener interface.
     */
    @Override
    public void onNotification(JsonRpcNotification notification) {
        TreeNotification treeNotification = new TreeNotification(notification);
        synchronized (listeners) {
            for (TreeListener tl : listeners) {
                tl.onTreeNotification(treeNotification);
            }
        }
    }

    @Override
    public void onError(Exception e) {
        Log.e(LOG_TAG, "onError: "+e.getMessage(), e);
    }


    @Override
    public void onOpen(ServerHandshake handshakedata) {
        super.onOpen(handshakedata);

        synchronized (listeners) {
            for (TreeListener tl : listeners) {
                tl.onTreeConnected();
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        super.onClose(code, reason, remote);

        synchronized (listeners) {
            for (TreeListener tl : listeners) {
                tl.onTreeDisconnected();
            }
        }
    }

    public void addObserver(TreeListener listener){
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeObserver(TreeListener listener){
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

}
