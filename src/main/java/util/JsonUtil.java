package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonUtil {

    /**
     * 默认字符编码
     */
    private static String encoding = "UTF-8";

    /**
     * FastJSON的序列化设置
     */
    private static SerializerFeature[] features =  new SerializerFeature[]{
        //输出Map中为Null的值
        SerializerFeature.WriteMapNullValue,

        //如果Boolean对象为Null，则输出为false
        SerializerFeature.WriteNullBooleanAsFalse,

        //如果List为Null，则输出为[]
        SerializerFeature.WriteNullListAsEmpty,

        //如果Number为Null，则输出为0
        SerializerFeature.WriteNullNumberAsZero,

        //输出Null字符串
        SerializerFeature.WriteNullStringAsEmpty,

        //格式化输出日期
        SerializerFeature.WriteDateUseDateFormat
    };

    /**
     * 把Java对象JSON序列化
     * @param obj 需要JSON序列化的Java对象
     * @return JSON字符串
     */
    private static String toJSONString(Object obj){
        return JSON.toJSONString(obj, features);
    }

    /**
     * 返回JSON格式数据
     * @param response
     * @param data 待返回的Java对象
     * @param encoding 返回JSON字符串的编码格式
     */
    public static void json(HttpServletResponse response, Object data, String encoding) {
        //设置编码格式
        response.setContentType("text/plain;charset=" + encoding);
        response.setCharacterEncoding(encoding);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(toJSONString(data));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回JSON格式数据，使用默认编码
     * @param response
     * @param data 待返回的Java对象
     */
    public static void json(HttpServletResponse response, Object data){
        json(response, data, encoding);
    }
}
