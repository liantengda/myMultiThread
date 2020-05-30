package com.lian.mycollection.common.globalException.pojo.response;


import com.lian.mycollection.common.globalException.constant.IResponseEnum;
import com.lian.mycollection.common.globalException.constant.enums.CommonResponseEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>基础返回结果</p>
 *
 * @author sprainkle
 * @date 2019/5/2
 */
@Data
@Slf4j
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {

        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        log.info("构造基类code{},message{}",code,message);
        this.code = code;
        this.message = message;
    }

}
