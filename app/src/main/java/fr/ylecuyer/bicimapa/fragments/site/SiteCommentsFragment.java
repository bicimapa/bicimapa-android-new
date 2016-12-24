package fr.ylecuyer.bicimapa.fragments.site;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

import fr.ylecuyer.bicimapa.MyPrefs_;
import fr.ylecuyer.bicimapa.MyRestClient;
import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.adapters.CommentsAdapter;
import fr.ylecuyer.bicimapa.models.Comment;
import fr.ylecuyer.bicimapa.models.CommentList;
import fr.ylecuyer.bicimapa.models.PostComment;

@EFragment(R.layout.fragment_site_comments)
public class SiteCommentsFragment extends Fragment {

    @FragmentArg
    long siteId;

    @ViewById
    ListView listView;

    @RestService
    MyRestClient myRestClient;
    private CommentList siteComments = new CommentList();

    @ViewById
    EditText comment_content;

    @Pref
    MyPrefs_ prefs;
    private CommentsAdapter itemsAdapter;

    @AfterViews
    void init() {
        Log.d("BICIMAPA", "SiteID="+siteId);

        itemsAdapter = new CommentsAdapter(getContext(), siteComments);
        listView.setAdapter(itemsAdapter);

        downloadData();
    }

    @Background
    void downloadData() {
        siteComments = myRestClient.getSiteComments(siteId);
        showData();
    }

    @UiThread
    void showData() {

        if (isDetached()) {
            return;
        }

        itemsAdapter.setComments(siteComments);
    }

    @Click
    void comment() {

        String content = comment_content.getText().toString();

        String token = prefs.user_token().get();

        if (token == null || token.isEmpty()) {

            Toast.makeText(getContext(), "You must be connected to comment", Toast.LENGTH_LONG).show();
            return;
        }

        if (content.length() > 0) {
            postComment(new PostComment(content, token));
            comment_content.setText("");
        }
    }

    @Background
    void postComment(PostComment comment) {
        myRestClient.postComment(siteId, comment);
        downloadData();
    }

}
