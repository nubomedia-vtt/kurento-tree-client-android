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

import fi.vtt.nubomedia.jsonrpcwsandroid.JsonRpcResponseError;

public class TreeError {
    private String code = null;
    private String data = null;

    public TreeError(JsonRpcResponseError error){
        super();
        if(error!=null) {
            this.code = "" + error.getCode();
            if(error.getData()!=null) {
                this.data = error.getData().toString();
            }
        }
    }

    public String getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String toString(){
        return "TreeError: "+code+" - "+data;
    }

}
