package bgu.spl.net.impl;

import java.util.Arrays;

public class BGSServer {
    public static class ReactorMain {
        public static void main(String[] args) {
            System.out.println("Reactor:" + Arrays.toString(args));
        }
    }

    public static class TPCMain {
        public static void main(String[] args) {
            System.out.println("TPC:" + Arrays.toString(args));
        }
    }
}
