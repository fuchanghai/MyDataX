package com.alibaba.datax.core.transport.transformer;

import com.alibaba.datax.common.element.Record;
import com.alibaba.datax.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fuchanghai
 */
public class ConstTransFormer extends Transformer {

    private static final Logger LOG = LoggerFactory.getLogger(ConstTransFormer.class);

    public ConstTransFormer() {
        setTransformerName("hito_const_replace");
    }
    @Override
    public Record evaluate(Record record, Object... paras) {
        return null;
    }
}
