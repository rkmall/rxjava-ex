package JustFromRangeRepeat;

import data.Note;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

import java.util.ArrayList;
import java.util.List;

public class CustomClassEx {

    public static void main(String[] args) {

        Observable<Note> noteObservable = getNoteObservable();
        Observer<Note> noteObserver = getNoteObserver();

        noteObservable
                .map(new Function<Note, Note>() {

                    @Override
                    public Note apply(Note note) throws Throwable {
                        note.setDescription(note.getDescription().toUpperCase());
                        return note;
                    }
                })
                .subscribe(noteObserver);
    }

    public static Observer<Note> getNoteObserver() {
        return new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(@NonNull Note note) {
                System.out.println("Note: " + note.getNote()) ;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("All items are emitted");
            }
        };
    }

    private static Observable<Note> getNoteObservable() {
        final List<Note> notes = prepareNote();

        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Note> emitter) throws Throwable {

                for(Note note : notes){
                    if(!emitter.isDisposed()){
                        emitter.onNext(note);
                    }
                }

                if(!emitter.isDisposed()){
                    emitter.onComplete();
                }
            }
        });
    }

    private static List<Note> prepareNote() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "Wake up at 7"));
        notes.add(new Note(2, "Go to shopping"));
        notes.add(new Note(3, "Prepare breakfast"));
        notes.add(new Note(4, "Study RxJava"));
        notes.add(new Note(5, "Go for running"));
        return notes;
    }
}
