/**
  * NDK Sample
  * 2019-08-01 K.OHWADA
 * original : https://github.com/android/ndk-samples/tree/master/hello-jniCallback
  */
package jp.ohwada.android.ndk3;


/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
import android.os.Build;
import android.util.Log;


/*
 * class JniHandler
 * 
 * A helper class to demo that JNI could call into:
 *     private non-static function
 *     public non-static function
 *     static public function
 * The calling code is inside hello-jnicallback.c
 */
public class JniHandler {

    /*
     * Print out status to logcat
     */
    //@Keep
    private void updateStatus(String msg) {
        if (msg.toLowerCase().contains("error")) {
            Log.e("JniHandler", "Native Err: " + msg);
        } else {
            Log.i("JniHandler", "Native Msg: " + msg);
        }
    }

    /*
     * Return OS build version: a static function
     */
    //@Keep
    static public String getBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    /*
     * Return Java memory info
     */
    //@Keep
    public long getRuntimeMemorySize() {
        return Runtime.getRuntime().freeMemory();
    }
}
