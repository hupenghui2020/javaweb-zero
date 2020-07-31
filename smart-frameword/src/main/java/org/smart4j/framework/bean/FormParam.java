package org.smart4j.framework.bean;

/**
 * 表单参数
 * @author hph
 */
public class FormParam {

    private String fieldName;

    private Object filedValue;

    public FormParam(String fieldName, Object filedValue) {
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFiledValue() {
        return filedValue;
    }

    public void setFiledValue(Object filedValue) {
        this.filedValue = filedValue;
    }
}
