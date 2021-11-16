package observerpattern.manual.observers;

import observerpattern.manual.subject.Subject;

public interface Observer {

    void subscribeSource(Subject subject);
    void unSubscribeSource();
    void update(String news);
}
