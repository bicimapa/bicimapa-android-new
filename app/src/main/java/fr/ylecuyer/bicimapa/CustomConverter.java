package fr.ylecuyer.bicimapa;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class CustomConverter extends MappingJackson2HttpMessageConverter {

    public CustomConverter() {
        ObjectMapper om = getObjectMapper();
        om.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
        //om.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        setObjectMapper(om);
    }
}
