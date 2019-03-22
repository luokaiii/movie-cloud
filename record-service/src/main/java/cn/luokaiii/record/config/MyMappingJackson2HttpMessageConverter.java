package cn.luokaiii.record.config;

import com.google.gson.Gson;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyMappingJackson2HttpMessageConverter extends AbstractHttpMessageConverter<Map> {

    private Gson gson = new Gson();

    public MyMappingJackson2HttpMessageConverter() {
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mediaTypes.add(MediaType.TEXT_HTML);
        setSupportedMediaTypes(mediaTypes);
    }

    /**
     * 读取定义的类型的资源，将其转化为Map
     */
    @Override
    protected Map readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String s = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
        System.out.println("【readInternal】" + s);
        return gson.fromJson(s, Map.class);
    }

    /**
     * 在推送此类型资源时，将Map对象，转化为自定义的格式返回给调用者
     */
    @Override
    protected void writeInternal(Map map, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("【writeInternal】" + map);
//        write(map,new MediaType("interface java.util.Map"),outputMessage);
    }

    @Override
    protected boolean supports(Class clazz) {
        System.out.println("【supports】" + clazz);
        return Gson.class.isAssignableFrom(clazz);
    }
}
