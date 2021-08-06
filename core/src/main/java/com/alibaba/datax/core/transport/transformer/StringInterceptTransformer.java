package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author fuchanghai
 * 字符截取
 */
public class StringInterceptTransformer extends Transformer {
    private static final Logger LOG = LoggerFactory.getLogger(StringInterceptTransformer.class);
    private static final String DESC = "desc";
    private static final String ASC = "asc";
    private final int parasLength = 4;

    public StringInterceptTransformer() {
        setTransformerName("hito_string_intercept");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        try {
            if (paras.length != parasLength) {
                throw new RuntimeException("stringIntercept transformer 缺少参数");
            }
            int columnIndex;
            columnIndex = Integer.parseInt(paras[0].toString());
            Column column = record.getColumn(columnIndex);
            String oriValue = column.asString();
            if (null == oriValue) {
                return record;
            }
            String startTag = paras[1].toString();
            Integer startIndex = Integer.parseInt(paras[2].toString());
            Integer endIndex = Integer.parseInt(paras[3].toString());
            String newValue = sub(startTag, startIndex, endIndex, oriValue);
            record.setColumn(columnIndex, new StringColumn(newValue));
            return record;
        } catch (Exception e) {
            LOG.info("转换的值 ：" + record.toString());
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage(), e);
        }
    }

    public String sub(String startTag, Integer startIndex, Integer endIndex, String originValue) {
        if (DESC.equals(startTag)) {
            originValue = new StringBuffer(originValue).reverse().toString();
        }
        if (startIndex > originValue.length()) {
            throw new RuntimeException(String.format("hito_string_intercept startIndex(%s) out of range(%s)", startIndex, originValue.length()));
        }
        if (endIndex > originValue.length()) {
            endIndex = originValue.length();
        }
        return originValue.substring(startIndex, endIndex);
    }
}
