package util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Simple facade for cancellable task management.
 */
public class Tasks {

    // simple API to execute cancellable tasks in background - functionality is provided by three functional
    // attributes

    private static Function<Runnable, Thread> startThreadFn =
            task -> {
                Thread result = new CancellableThread(task);
                result.setDaemon(true);
                result.start();
                return result;
            };


    // the default implementation uses CancellableThread
    private static Consumer<Thread> cancelFn =
            thread -> {
                if (thread instanceof CancellableThread) ((CancellableThread) thread).cancel();
            };
    /**
     * Tests whether the current task has been cancelled. Calls of this method can be placed anywhere in the code.
     * They provide safe exits from time-consuming loops if the user is not interested in the result anymore.
     */
    private static Supplier<Boolean> isCancelledFn = CancellableThread::currIsCancelled;

    /**
     * Executes a given task in background.
     */
    public static Thread executeInBackground(Runnable task) {
        return startThreadFn.apply(task);    //Method of functional interface.
    }

    /**
     * Cancels the task which is executed in the given thread.
     */
    public static void cancel(Thread thread) {
        cancelFn.accept(thread);
    }

    public static boolean currIsCancelled() {
        return isCancelledFn.get();
    }
}
