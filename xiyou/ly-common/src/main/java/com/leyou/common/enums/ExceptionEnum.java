package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {

    PRICE_CONNOT_BE_NULL(400,"价格不能为空！"),
    CATEGORY_NOT_FOUND(404,"商品分类没查到"),
    BRAND_NOT_FOUND(404,"品牌不存在"),
    GOODS_NOT_FOUND(404,"商品不存在"),
    GOODS_DETAIL_NOT_FOUND(404,"商品详情不存在"),
    GOODS_SKU_NOT_FOUND(404,"商品SKU不存在"),
    GOODS_STOCK_NOT_FOUND(404,"商品库存不存在"),
    SPEC_BRAND_NOT_FOUND(404,"规格组不存在"),
    SPEC_PARAM_NOT_FOUND(404,"商品规格参数不存在"),
    CART_NOT_FOUND(404,"购物车为空"),
    SPEC_BRAND_SAVE_ERROR(500,"新增规格组失败"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    GOODS_SAVE_ERROR(500,"更新商品失败"),
    FILE_UPLOAD_ERROR(500,"文件上传失败"),
    GOODS_UPDATE_ERROR(500,"修改商品详情失败"),
    INVALID_FILE_TYPE(400,"无效的文件类型"),
    INVALID_USER_DATA_TYPE(400,"无效的数据类型"),
    GOODS_ID_CANNOT_BE_NULL(400,"商品id不能为空"),
    UNAUTHORIZED(403,"未授权")
    ;
    private int code;
    private String msg;
}
