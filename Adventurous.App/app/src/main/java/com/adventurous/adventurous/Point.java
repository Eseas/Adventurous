package com.adventurous.adventurous;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;


public class Point implements Serializable {

    public double pointLatitude;
    public double pointLongitude;
    public String clue;
    public String[] hints;
    public String[] answers;
    public int rigthAnswerId;
    public boolean isFree;
    public UUID id;

    public Point(JSONObject jsonPoint){
        try {
            pointLatitude = jsonPoint.getDouble("latitude");
            pointLongitude = jsonPoint.getDouble("longitude");

            isFree = jsonPoint.getBoolean("isFree");

            String idAsString = jsonPoint.getString("id");
            id = UUID.fromString(idAsString);

            clue = jsonPoint.getString("clue");

            JSONArray jsonArray = jsonPoint.getJSONArray("hints");
            hints = new String[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++)
            {
                hints[i] = jsonArray.getString(i);
            }

            jsonArray = jsonPoint.getJSONArray("answers");
            answers = new String[jsonArray.length()];
            for(int i = 0;i < jsonArray.length(); i++)
            {
                answers[i] = jsonArray.getString(i);
            }

            rigthAnswerId = jsonPoint.getInt("rigthAnswerId");

        }catch(Exception ex){
            pointLatitude = 0;
            pointLongitude = 0;
            isFree = false;
            clue = null;
            hints = null;
            answers = null;
            rigthAnswerId = -1;
        }
    }
}
