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
 *
 * 空格替换
 */
public class BlankReplaceTransformer extends Transformer {

    private static final Logger LOG = LoggerFactory.getLogger(BlankReplaceTransformer.class);
    private static  final String LEFT ="l";
    private static  final String RIGHT ="r";
    private final int parasLength = 2;

    public BlankReplaceTransformer() {
        setTransformerName("hito_blank_replace");
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
            String originValue = column.asString();
            if (null == originValue) {
                return record;
            }
            String startPlace = paras[1].toString();
            String newValue = replace(startPlace, originValue);
            record.setColumn(columnIndex, new StringColumn(newValue));
            return record;
        } catch (Exception e) {
            LOG.info("转换的值 ：" + record.toString());
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage(), e);
        }
    }

    public String replace(String startPlace, String text) {
        if (LEFT.equals(startPlace)) {
            return replaceBlankFromLeft(text);
        } else if (RIGHT.equals(startPlace)) {
            return replanceBlankFromRight(text);
        } else {
            return  replaceBlankFromLeft(replanceBlankFromRight(text));
        }
    }

    public String replaceBlankFromLeft(String valueString){
        while (valueString.startsWith(" ")||valueString.startsWith("　")) {
            valueString = valueString.substring(1);
        }
        return valueString;
    }

    public String replanceBlankFromRight(String valueString){
        while (valueString.endsWith(" ")||valueString.endsWith("　")) {
            valueString = valueString.substring(0,valueString.length()-1);
        }
        return valueString;
    }
}
