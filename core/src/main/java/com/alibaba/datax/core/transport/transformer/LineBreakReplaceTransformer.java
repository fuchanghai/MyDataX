package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Column;
import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.common.element.StringColumn;
import com.alibaba.datax.transformer.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fuchanghai
 *
 * 换行符替换
 */
public class LineBreakReplaceTransformer extends Transformer {
    private static final Logger LOG = LoggerFactory.getLogger(LineBreakReplaceTransformer.class);
    private final int parasLength = 2;
    private final static String defaultValue = "";
    private static  Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");

    public LineBreakReplaceTransformer() {
        setTransformerName("hito_line_break_replace");
    }

    @Override
    public Record evaluate(Record record, Object... paras) {
        String insteadValue;
        if (paras.length != parasLength) {
            throw new RuntimeException("stringReplace transformer 缺少参数");
        }
        int columnIndex;
        columnIndex = Integer.parseInt(paras[0].toString());
        if (StringUtils.isNotBlank(paras[1].toString())) {
            insteadValue = paras[1].toString();
        } else {
            insteadValue = defaultValue;
        }
        Column column = record.getColumn(columnIndex);
        String oriValue = column.asString();
        if (null == oriValue) {
            return record;
        }
        Matcher matcher = pattern.matcher(oriValue);
        String newValue = matcher.replaceAll(insteadValue);
        record.setColumn(columnIndex, new StringColumn(newValue));
        return record;
    }
}
