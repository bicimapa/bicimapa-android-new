package fr.ylecuyer.bicimapa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.ylecuyer.bicimapa.R;
import fr.ylecuyer.bicimapa.models.Comment;

public class CommentsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    CommentsAdapter(Context context) {
       this.context = context;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        Comment comment = comments.get(i);

        View view = convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.comment_item, viewGroup, false);
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.picture);
        Picasso.with(context).load(comment.getAvatar_url()).placeholder(R.drawable.progress_animation ).into(imageView);

        TextView commenter_name = (TextView) view.findViewById(R.id.commenter_name);
        commenter_name.setText(comment.getAdded_by());

        TextView comment_content = (TextView) view.findViewById(R.id.comment_content);
        comment_content.setText(comment.getComment());

        return view;

    }
}
