package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.transformer.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author fuchanghai
 * 增加时间戳
 */
public class AddTimeStampTransformer extends Transformer {
    private static final Logger LOG = LoggerFactory.getLogger(AddTimeStampTransformer.class);
    private static final String LAST = "last";
    private static final String CONST = "const";
    private final int parasLength = 3;

    public AddTimeStampTransformer() {
        setTransformerName("hito_add_timestamp");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        try {
            if (paras.length != parasLength) {
                throw new RuntimeException("stringReplace transformer 缺少参数");
            }
            int columnIndex;
            columnIndex = Integer.parseInt(paras[0].toString());
            String originValue = record.getColumn(columnIndex).asString();
            if (null == originValue) {
                return record;
            }
            String dataFormat = paras[1].toString();
            String type = paras[2].toString();
            //String value = paras[3].toString();
            String newValue = getValue(dataFormat, type);
            if(StringUtils.isNotBlank(newValue)){
                record.setColumn(columnIndex, new StringColumn(newValue));
            }
            return record;
        } catch (Exception e) {
            LOG.info("转换的值 ：" + record.toString());
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage(), e);
        }
    }

    public String getValue(String dataFormat, String type) {
        if (LAST.equals(type)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormat);
            String formatDate = simpleDateFormat.format(new Date());
            return formatDate;
        }else if(CONST.equals(type)){
            return null;
        }
        return null;
    }
}
