
package com.aimxcel.abclearn.common.abclearncommon.util;

//import com.aimxcel.abclearn.common.abclearncommon.util.MockObserver;
import com.aimxcel.abclearn.common.abclearncommon.util.SimpleObservable;
import com.aimxcel.abclearn.common.abclearncommon.util.SimpleObserver;
import junit.framework.TestCase;

public class ZSimpleObservableTester extends TestCase {
    private volatile SimpleObservable observable;

    public void setUp() {
        observable = new SimpleObservable();
    }

    public void tearDown() {
    }

    public void testControllerPropagatesNotification() {
        MockObserver mockObserver = new MockObserver();

        observable.addObserver( mockObserver );
        observable.notifyObservers();

        assertEquals( 1, mockObserver.getUpdateCount() );
    }

    private static class MockObserver implements SimpleObserver {
        private volatile int updateCount = 0;

        public void update() {
            ++updateCount;
        }

        public int getUpdateCount() {
            return updateCount;
        }
    }
}
