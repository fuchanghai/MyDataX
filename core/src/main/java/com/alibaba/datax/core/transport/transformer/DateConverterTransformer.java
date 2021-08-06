package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.common.exception.DataXException;
import com.alibaba.datax.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author fuchanghai
 * 日期格式转换
 */
public class DateConverterTransformer extends Transformer {
    private static final Logger LOG = LoggerFactory.getLogger(DateConverterTransformer.class);
    private final int parasLength = 3;

    public DateConverterTransformer() {
        setTransformerName("hito_date_converter");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        try {
            if (paras.length != parasLength) {
                throw new RuntimeException("dateConverter transformer 缺少参数");
            }
            int columnIndex;
            columnIndex = Integer.parseInt(paras[0].toString());
            Column column = record.getColumn(columnIndex);
            String oriValue = column.asString();
            if (null == oriValue) {
                return record;
            }
            String oriFormat = paras[1].toString();
            String newFormat = paras[2].toString();
            String newValue = converterDate(oriValue, oriFormat, newFormat);
            record.setColumn(columnIndex, new StringColumn(newValue));
            return record;
        } catch (Exception e) {
            LOG.info("转换的值 ：" + record.toString());
            throw DataXException.asDataXException(TransformerErrorCode.TRANSFORMER_ILLEGAL_PARAMETER, "paras:" + Arrays.asList(paras).toString() + " => " + e.getMessage(), e);
        }
    }

    public String converterDate(String oriValue, String oriFormat, String newFormat) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oriFormat);
        Date date = simpleDateFormat.parse(oriValue);
        SimpleDateFormat nowSimpleDateFormat = new SimpleDateFormat(newFormat);
        return nowSimpleDateFormat.format(date);
    }
}
