package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;

@JsonRootName("comments")
public class CommentList extends ArrayList<Comment> {
}
