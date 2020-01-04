
package com.aimxcel.abclearn.common.aimxcelcommon.view;

import com.aimxcel.abclearn.common.aimxcelcommon.view.TimeControlListener;

public interface TimeControlListener {

    void stepPressed();

    void playPressed();

    void pausePressed();

    void stepBackPressed();

    void restartPressed();

    public static class TimeControlAdapter implements TimeControlListener {

        public void stepPressed() {
        }

        public void playPressed() {
        }

        public void pausePressed() {
        }

        public void stepBackPressed() {
        }

        public void restartPressed() {
        }
    }
}