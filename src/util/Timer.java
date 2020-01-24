package util;

public class Timer {
    private static long start_time;

    public static double tic(){
        return start_time = System.nanoTime();
    }//Start

    public static double toc(){
        return (System.nanoTime()-start_time)/1000000000.0;
    }//End

}
