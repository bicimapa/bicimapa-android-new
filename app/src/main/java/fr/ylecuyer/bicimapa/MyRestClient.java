package fr.ylecuyer.bicimapa;

import org.androidannotations.annotations.Background;
import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Field;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import fr.ylecuyer.bicimapa.models.CategoryList;
import fr.ylecuyer.bicimapa.models.CommentList;
import fr.ylecuyer.bicimapa.models.LineList;
import fr.ylecuyer.bicimapa.models.PostComment;
import fr.ylecuyer.bicimapa.models.Profile;
import fr.ylecuyer.bicimapa.models.SiteDetail;
import fr.ylecuyer.bicimapa.models.SiteList;
import fr.ylecuyer.bicimapa.models.SitePicturesDetail;
import fr.ylecuyer.bicimapa.models.SiteStats;
import fr.ylecuyer.bicimapa.models.Token;

//@Rest(rootUrl = "http://10.0.2.2:3000/api/v1", converters = { MappingJackson2HttpMessageConverter.class })
@Rest(rootUrl = "http://bicimapa.com/api/v1", converters = { CustomConverter.class })
public interface MyRestClient {

    @Get("/sites/get?ne={lat_ne},{lon_ne}&sw={lat_sw},{lon_sw}&categories={categories}")
    SiteList getSites(@Path double lat_ne, @Path double lon_ne, @Path double lat_sw, @Path double lon_sw, @Path String categories);

    @Get("/lines/get?ne={lat_ne},{lon_ne}&sw={lat_sw},{lon_sw}&categories={categories}")
    LineList getLines(@Path double lat_ne, @Path double lon_ne, @Path double lat_sw, @Path double lon_sw, @Path String categories);

    @Get("/sites/{id}")
    SiteDetail getSite(@Path long id);

    @Get("/sites/{id}/comments")
    CommentList getSiteComments(@Path long id);

    @Get("/sites/{id}/pictures")
    SitePicturesDetail getSitePicturesDetail(@Path long id);

    @Get("/sites/{id}/stats")
    SiteStats getSiteStats(@Path long id);

    @Get("/categories")
    CategoryList getCategories();

    @Get("/session/get_user_token_from_devise?email={email}&password={password}")
    Token getToken(@Path String email, @Path String password);

    @Get("/session/get_user_token_from_facebook_id?facebook_id={facebook_id}")
    Token getToken(@Path String facebook_id);

    @Get("/users?token={token}")
    Profile getProfile(@Path String token);

    @Post("/sites/{id}/comment")
    void postComment(@Path long id, @Body PostComment comment);
}
