/*
 * Copyright 2014 Luke Quinane
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package car_counter.counting.opencv;

import java.nio.file.Path;
import java.util.Collection;

import car_counter.counting.CarCounter;
import car_counter.counting.DetectedVehicle;
import org.joda.time.DateTime;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.video.BackgroundSubtractorMOG;

/**
 * An OpenCV based car counter.
 */
public class OpencvCarCounter implements CarCounter
{
    static
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    Mat image;
    Mat foregroundMask;
    Mat maskedImage;
    BackgroundSubtractorMOG backgroundSubtractor;

    public OpencvCarCounter()
    {
        image = new Mat();
        foregroundMask = new Mat();
        maskedImage = new Mat();
        backgroundSubtractor = new BackgroundSubtractorMOG();
    }

    @Override
    public Collection<DetectedVehicle> processVideo(Path video, DateTime startDateTime)
    {
        CascadeClassifier carDetector = new CascadeClassifier("/Users/luke/working/car-counter/data/cars3.xml");


        VideoCapture videoCapture = new VideoCapture();
        videoCapture.open("/Users/luke/working/car-counter/data/video1.m4v");

        int index = 0;

        while (true)
        {
            if (!videoCapture.read(image))
            {
                break;
            }

            System.out.print(".");

            //processFrame();

            MatOfRect carDetections = new MatOfRect();
            carDetector.detectMultiScale(image, carDetections);

            System.out.println(String.format("Detected %s cars", carDetections.toArray().length));

            // Draw a bounding box around each hit
            for (Rect rect : carDetections.toArray())
            {
                Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
            }

            String file = String.format("/Users/luke/working/car-counter/data/out/out-%03d.jpg", index++);
            org.opencv.highgui.Highgui.imwrite(file, image);
        }

        return null;
    }

    protected void processFrame()
    {
        backgroundSubtractor.apply(image, foregroundMask, 0.1);

        //Imgproc.cvtColor(foregroundMask, foregroundMask, Imgproc.COLOR_, 4);
        //Imgproc.cvtColor(image, image, Imgproc.COLOR_RGBA2GRAY, 4);
        //Imgproc.cvtColor(maskedImage, maskedImage, Imgproc.COLOR_RGBA2GRAY, 4);

        Core.bitwise_and(image, image, maskedImage, foregroundMask);
    }
}
