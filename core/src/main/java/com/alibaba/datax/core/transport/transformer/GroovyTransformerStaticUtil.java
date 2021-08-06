package com.alibaba.datax.core.transport.transformer;


import com.alibaba.datax.common.element.Record;

/**
 * GroovyTransformer的帮助类，供groovy代码使用，必须全是static的方法
 * Created by liqiang on 16/3/4.
 */
public class GroovyTransformerStaticUtil {

   /* public static String replace(String startPlace, String text, String searchString, String insteadString) {
        if ("l".equals(startPlace)) {
            int searchStringIndex = text.indexOf(searchString);
            return text.substring(0, searchStringIndex) +insteadString+ text.substring(searchStringIndex + searchString.length());
        } else if ("r".equals(startPlace)) {
            int searchStringIndex = text.lastIndexOf(searchString);
            return text.substring(0, searchStringIndex) +insteadString+ text.substring(searchStringIndex + searchString.length());
        }else {
            return text.replace(searchString,insteadString);
        }
    }*/

}
