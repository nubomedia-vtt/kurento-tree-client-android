kurento-tree-android
=================
This repository contains Kurento Tree API for Android.

This project is part of [NUBOMEDIA](http://www.nubomedia.eu).

The source code is available at [https://github.com/nubomedia-vtt/kurento-tree-client-android](https://github.com/nubomedia-vtt/kurento-tree-client-android).


Repository structure
--------------------
This repository consists of an Android Studio library project with gradle build scripts. 

Usage
--------
The more detailed Developers Guide and Installation Guide are available at [http://kurento-tree-client-android.readthedocs.org/en/latest/](http://kurento-tree-client-android.readthedocs.org/en/latest/)

You can import this project to your own Android Studio project via Maven (jCenter or Maven Central) by adding the following line to module's `build.gradle` file:
```
compile 'fi.vtt.nubomedia:kurento-tree-client-android:1.0.0'
```

If you want to build the project from source, you need to import the third-party libraries via Maven by adding the following lines to
the module's `build.gradle` file
```
compile 'fi.vtt.nubomedia:utilities-android:1.0.0'
compile 'fi.vtt.nubomedia:jsonrpc-ws-android:1.0.4'
compile 'fi.vtt.nubomedia:webrtcpeer-android:1.0.0'
```

Android application code
------------------------
```
import fi.vtt.nubomedia.kurentoroomclientandroid.KurentoTreeAPI;
import fi.vtt.nubomedia.utilitiesandroid.LooperExecutor;

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

```

```
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
```



Licensing
---------
[BSD](https://github.com/nubomedia-vtt/kurento-tree-client-android/blob/master/LICENSE)

***Contribution policy***

You can contribute to this project through bug-reports, bug-fixes, new code or new documentation. For contributing to the project, drop a post to the mailing list providing information about your contribution and its value. In your contributions, you must comply with the following guidelines

•	You must specify the specific contents of your contribution either through a detailed bug description, through a pull-request or through a patch.

•	You must specify the licensing restrictions of the code you contribute.

•	For newly created code to be incorporated in the code-base, you must accept the code copyright, so that its open source nature is guaranteed.

•	You must justify appropriately the need and value of your contribution. There is no obligations in relation to accepting contributions from third parties.

Support
-------
Support is provided through the [NUBOMEDIA VTT Public Mailing List](https://groups.google.com/forum/#!forum/nubomedia-vtt).

