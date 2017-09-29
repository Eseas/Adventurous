package com.adventurous.adventurous;

import com.adventurous.adventurous.Entities.Point;

public interface IGetPointsTaskListener {
    void notifyOfGetPointsTaskComplete(Thread thread, Point[] points);
}

