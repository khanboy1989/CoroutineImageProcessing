# CoroutineImageProcessing

This project is a small project in order to show coroutine example for Android 

It contains very simple image processing and uses different coroutine dispatchers such as Default, IO and Main.

Main Dispatcher used to update the image view 
IO used to download the image for given url and Default is used to convert image to black and white colors.

Default dispathcher uses thread pool in order to process image to black and wait.

Coroutines ASYNC property used to await the results. 

