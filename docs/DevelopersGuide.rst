%%%%%%%%%%%%%%%%
Developers Guide
%%%%%%%%%%%%%%%%

This documents provides information how to utilize the kurento-tree-client-android library for your project.


Setup the developing environment by importing the project to Android Studio.
You can import this project to your own Android Studio project via Maven (jCenter or Maven Central) by adding the following line to module's build.gradle file:


.. code:: java

    compile 'fi.vtt.nubomedia:kurento-tree-client-android:1.0.4'


Android application code

.. code:: java

    import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;
    
    import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoTreeAPI;
    import fi.vtt.nubomedia.kurentoroomclientandroid.TreeError;
    import fi.vtt.nubomedia.kurentoroomclientandroid.TreeListener;
    import fi.vtt.nubomedia.kurentoroomclientandroid.TreeNotification;
    import fi.vtt.nubomedia.kurentoroomclientandroid.TreeResponse;
    
    String wsTreeUri = "http://YOUR_IP_ADDRESS:8080/kurento-tree
    LooperExecutor executor = new LooperExecutor();
    executor.requestStart();
    TreeListener myTreeListener = ...
    public int requestIndex = 0;
    
    
    KurentoTreeAPI treeApi = new KurentoTreeAPI(executor, wsTreeUri, myTreeListener);
    treeApi.connectWebSocket();
    if(treeApi.isWebSocketConnected()){
     treeApi.sendCreateTree("My Tree", requestIndex++);
     treeApi.disconnectWebSocket();
    }
    
    
    class MyTreeListener implements TreeListener(){
     @Override
     public void onTreeResponse(TreeResponse response) {
      String responseId = response.getId();
      String sessionId = response.getSessionId();
      HashMap<String><String> values = response.getValues();
    }
    
    @Override
    public void onTreeError(TreeError error) {
     String errorCode = error.getCode();
     String errorData = error.getData();
    }
    
    @Override
    public void onTreeNotification(TreeNotification notification) {
      if(notification.getMethod()
        .equals(TreeListener.METHOD_ICE_CANDIDATE) {
        // TODO
      } else ...
     }
    }


Source code is available at
https://github.com/nubomedia-vtt/kurento-tree-client-android

The Javadoc is included in the source code and can be downloaded from the link below:
https://github.com/nubomedia-vtt/kurento-tree-client-android/tree/master/javadoc 

Support is provided through the Nubomedia VTT Public Mailing List available at
https://groups.google.com/forum/#!forum/nubomedia-vtt




