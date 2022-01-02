package bgu.spl.net.api.bidi;

public interface Command<T> {

    T process();
}
