package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;

@JsonRootName("categories")
public class CategoryList extends ArrayList<Category> {
}
