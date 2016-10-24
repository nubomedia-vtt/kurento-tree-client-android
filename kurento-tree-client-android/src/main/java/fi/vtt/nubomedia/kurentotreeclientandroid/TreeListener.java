/*
 * (C) Copyright 2016 VTT (http://www.vtt.fi)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
