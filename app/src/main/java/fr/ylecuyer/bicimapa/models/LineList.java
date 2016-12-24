package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;

@JsonRootName("lines")
public class LineList extends ArrayList<Line> {
}
