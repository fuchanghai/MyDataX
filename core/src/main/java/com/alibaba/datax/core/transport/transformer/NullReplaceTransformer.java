package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.transformer.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author fuchanghai
 * 空值替换
 */
public class NullReplaceTransformer extends Transformer {

    private static final Logger LOG = LoggerFactory.getLogger(NullReplaceTransformer.class);
    private final int parasLength = 2;

    public NullReplaceTransformer() {
        setTransformerName("hito_null_replace");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        int columnIndex;
        columnIndex = Integer.parseInt(paras[0].toString());
        String defalutValue = paras[1].toString();
        try {
            if (paras.length != parasLength) {
                throw new RuntimeException("stringReplace transformer 缺少参数");
            }
            Column column = record.getColumn(columnIndex);
            String originValue = column.asString();
            //如果是null 值
            if (StringUtils.isBlank(originValue)) {
                record.setColumn(columnIndex, new StringColumn(defalutValue));
            }
            return record;
        } catch (Exception e) {

            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage(), e);
        }
    }
}
