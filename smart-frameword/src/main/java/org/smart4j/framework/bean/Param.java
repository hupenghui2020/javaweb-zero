package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 * @author 10499
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     * @return
     */
    public Map<String,Object> getFieldMap() {
        Map<String,Object> fieldMap = new HashMap<>();
        if(CollectionUtil.isNotEmpty(formParamList)) {
            for(FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFiledValue();
                if(fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     * @return
     */
    public Map<String,List<FileParam>> getFileMap(){
        Map<String,List<FileParam>> fileMap = new HashMap<>();
        if(CollectionUtil.isNotEmpty(fileParamList)) {
            List<FileParam> fileParams;
            for(FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                if(fileMap.containsKey(fieldName)) {
                    fileParams = fileMap.get(fieldName);
                }else {
                    fileParams = new ArrayList<>();
                }
                fileParams.add(fileParam);
                fileMap.put(fieldName, fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if(CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 根据参数名获取 String 型参数值
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 double 型参数值
     * @param name
     * @return
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 long 型参数值
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFileMap().get(name));
    }

    /**
     * 根据参数名获取 int 型参数值
     * @param name
     * @return
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 boolean 型参数值
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }

    /**
     * 校验参数是否为空
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(this.formParamList)
                || CollectionUtil.isEmpty(this.fileParamList);
    }
}
