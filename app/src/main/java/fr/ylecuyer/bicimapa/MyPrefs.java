package fr.ylecuyer.bicimapa;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    @DefaultInt(500)
    int max_markers_before_GC();

    @DefaultInt(500)
    int max_lines_before_GC();

    String user_token();
}
