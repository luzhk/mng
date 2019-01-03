package com.platform.universally.manager.response;

/**
 * 返回参数
 * Created by Administrator on 2018/12/24.
 */
public class JsonResult {

    public Integer errorCode;

    public String message;

    public Integer pageNumber;

    public Integer pageSize;

    public Object result;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static JsonResult getJsonResult(Integer errorCode, Object result){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setErrorCode(errorCode);
        jsonResult.setMessage(ResCode.MSG.get(errorCode));
        jsonResult.setResult(result);
        return jsonResult;
    }

}
