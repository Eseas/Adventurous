package com.adventurous.adventurous;


import org.json.JSONArray;

public class GetPointsTask extends Thread {

    private IGetPointsTaskListener listener;

    public GetPointsTask(IGetPointsTaskListener _listener) {
        listener = _listener;
    }

    @Override
    public void run() {

        Point[] points;

        try {
            points = getPoints();
        } catch (Exception ex) {
            points = null;
        }

        listener.notifyOfGetPointsTaskComplete(this, points);
    }

    private Point[] getPoints() throws Exception {

        String response = HttpClient.Get("http://adventurous.azurewebsites.net/api/points");

        JSONArray jsonArray = new JSONArray(response);

        Point[] points = new Point[jsonArray.length()];

        for(int i = 0;i < jsonArray.length(); i++)
        {
            points[i] = new Point(jsonArray.getJSONObject(i));
        }

        return points;
    }
}
