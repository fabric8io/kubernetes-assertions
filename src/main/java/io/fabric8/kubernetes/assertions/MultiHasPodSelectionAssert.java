package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.Pod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.fail;

/**
 *
 */
public class MultiHasPodSelectionAssert implements HasPodSelectionAssert {
    private List<HasPodSelectionAssert> asserters;

    class MultiPodSelectionAssert extends AbstractPodSelectionAssert {

        @Override
        public List<Pod> getPods() {
            List<Pod> rc = new ArrayList<>();
            for (HasPodSelectionAssert asserter : asserters) {
                for (Pod pod : asserter.pods().getPods()) {
                    rc.add(pod);
                }
            }
            return rc;
        }

        @Override
        public MultiPodSelectionAssert isPodReadyForPeriod(final long notReadyTimeoutMS, final long readyPeriodMS) {
            // Do it in parallel so that this does not take longer and long if we have lots of asserters
            final AtomicReference<Throwable> failure = new AtomicReference<>();
            ArrayList<Thread> threads = new ArrayList<>(asserters.size());
            for (HasPodSelectionAssert a : asserters) {
                final HasPodSelectionAssert asserter = a;
                Thread thread = new Thread("MultiPodSelectionAssert"){
                    @Override
                    public void run() {
                        try {
                            asserter.pods().isPodReadyForPeriod(notReadyTimeoutMS, readyPeriodMS);
                        } catch (Throwable e) {
                            failure.set(e);
                        }
                    }
                };
                thread.start();
                threads.add(thread);
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    fail("Interrupted: "+e);
                }
            }
            Throwable throwable = failure.get();
            if( throwable!=null ) {
                if( throwable instanceof Error )
                    throw (Error)throwable;
                if( throwable instanceof RuntimeException )
                    throw (RuntimeException)throwable;
                throw new RuntimeException(throwable);
            }
            return this;
        }
    }

    public MultiHasPodSelectionAssert(List<HasPodSelectionAssert> asserters) {
        this.asserters = asserters;
    }

    @Override
    public AbstractPodSelectionAssert pods() {
        return new MultiPodSelectionAssert();
    }

}