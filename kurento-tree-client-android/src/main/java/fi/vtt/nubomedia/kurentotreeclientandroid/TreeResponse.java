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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TreeResponse {
    String id = null;


    String sessionId = null;
    private HashMap<String, String> values = null;

    public TreeResponse(String id, JSONObject obj){
        super();
        this.id = id;
        this.sessionId = this.getJSONObjectSessionId(obj);
        this.values = this.getJSONObjectValues(obj);
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public String getValue(String key){
        return values.get(key);
    }

    public void setValues(HashMap<String, String> values) {
        this.values = values;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String toString(){
        return "TreeResponse: "+id+" - "+sessionId+" - "+valuesToString();
    }

    private String getJSONObjectSessionId(JSONObject obj){
        if(obj.containsKey("sessionId")) {
            return obj.get("sessionId").toString();
        } else {
            return null;
        }
    }

    private HashMap<String, String> getJSONObjectValues(JSONObject obj){
        HashMap<String, String> result = new HashMap<String, String>();;

        if(obj.containsKey("value")) {

            JSONArray value = (JSONArray)obj.get("value");
            for(int i=0;i<value.size();i++) {
                JSONObject jo = (JSONObject) value.get(i);
                Set<String> keys = jo.keySet();
                for(String key : keys){
                    result.put(key, jo.get(key).toString());
                }
            }
        }


        if (obj.containsKey("answerSdp")){
            String sd = (String)obj.get("answerSdp");
            result.put("answerSdp", sd);
        }

        if (obj.containsKey("sinkId")){
            result.put("sinkId", (String)obj.get("sinkId"));
        }

        if (result.isEmpty()) {
            result = null;
        }

        return result;
    }

    private String valuesToString(){
        StringBuffer sb = new StringBuffer();
        if(this.values!=null){
            for (Map.Entry<String,String> entry : values.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key+"="+value+", ");
            }
            return sb.toString();
        } else return null;
    }



}