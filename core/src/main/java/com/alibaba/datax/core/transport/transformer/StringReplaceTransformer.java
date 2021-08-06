package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author fuchanghai
 * <p>
 * 字符替换
 * <p>
 * 剔除字符[1,"any","a","b"]
 */
public class StringReplaceTransformer extends Transformer {
    private static final Logger LOG = LoggerFactory.getLogger(StringReplaceTransformer.class);
    private static final String LEFT = "l";
    private static final String RIGHT = "r";
    private final int parasLength = 4;

    public StringReplaceTransformer() {
        setTransformerName("hito_string_replace");
    }


    @Override
    public Record evaluate(Record record, Object... paras) {
        try {
            if (paras.length != parasLength) {
                throw new RuntimeException("stringReplace transformer 缺少参数");
            }
            int columnIndex;
            columnIndex = Integer.parseInt(paras[0].toString());
            Column column = record.getColumn(columnIndex);
            String oriValue = column.asString();
            if (null == oriValue) {
                return record;
            }
            String startPlace = paras[1].toString();
            String searchString = paras[2].toString();
            String insteadString = paras[3].toString();
            String newValue = replace(startPlace, oriValue, searchString, insteadString);
            record.setColumn(columnIndex, new StringColumn(newValue));
            return record;
        } catch (Exception e) {
            LOG.info("转换的值 ：" + record.toString());
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage(), e);
        }
    }

    public String replace(String startPlace, String text, String searchString, String insteadString) {
        if (LEFT.equals(startPlace)) {
            int searchStringIndex = text.indexOf(searchString);
            if (searchStringIndex >= 0) {
                return text.substring(0, searchStringIndex) + insteadString + text.substring(searchStringIndex + searchString.length());
            }
        } else if (RIGHT.equals(startPlace)) {
            int searchStringIndex = text.lastIndexOf(searchString);
            if (searchStringIndex >= 0) {
                return text.substring(0, searchStringIndex) + insteadString + text.substring(searchStringIndex + searchString.length());
            }
        } else {
            return text.replace(searchString, insteadString);
        }
        return text;
    }
}
