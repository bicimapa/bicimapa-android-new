package fr.ylecuyer.bicimapa.models;

import com.fasterxml.jackson.annotation.JsonRootName;

import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;

@JsonRootName("sites")
public class SiteList extends ArrayList<Site> {
}
