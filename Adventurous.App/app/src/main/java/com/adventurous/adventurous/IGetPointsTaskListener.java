package com.adventurous.adventurous;

public interface IGetPointsTaskListener {
    void notifyOfGetPointsTaskComplete(Thread thread, Point[] points);
}

