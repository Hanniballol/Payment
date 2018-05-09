//
//  MTPConstant.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#ifndef MTPConstant_h
#define MTPConstant_h

static NSString *const MTPHttpsDebugHost = @"http://pay.app-platform.info";
static NSString *const MTPHttpsReleaseHost = @"https://pay.msymobile.com.cn";

static NSString *const MTPTradeWeb = @"/v1.0/trade/submit";
static NSString *const MTPTradeWay = @"/v1.0/trade/way";
static NSString *const MTPQuery = @"/v1.0/trade/query";

static NSString *const MTPContentType = @"Content-Type";
static NSString *const MTPTextXml = @"text/xml";
static NSString *const MTPContentMD5 = @"Content-MD5";
static NSString *const MTPXSprApp = @"x-spr-app";
static NSString *const MTPXSprDate = @"x-spr-date";
static NSString *const MTPAuthorization = @"authorization";
static NSString *const MTPDeviceId = @"x-device-id";
static NSString *const MTPVersion = @"x-version";
static NSString *const MTPSDKVersion = @"1";

static NSString *const MTPHttpMethodPost = @"POST";
static NSString *const MTPHttpMethodGet = @"GET";

static NSString *const MTPWechatTrade = @"WechatH5";
static NSString *const MTPAliTrade = @"AlipayH5";

static const NSInteger MTPRequestCode = 10086;
static const NSInteger MTPResultCode = 10087;

typedef NS_ENUM(NSUInteger, MTPTradeWayCode) {//支付方式
    MTPTradeWayCodeWechat,
    MTPTradeWayCodeAli
};

typedef NS_ENUM(NSInteger, MTPApiErrorCode) {
    MTPApiErrorCodeSuccess = 0, //成功
    MTPApiErrorCodeDataNotFound = 1, //记录未找到
    MTPApiErrorCodeNotPay = 2, //订单未支付
    MTPApiErrorCodeIllegalArgument = 5, //非法参数
    MTPApiErrorCodeDataExists = 8, //记录已存在
    MTPApiErrorCodeSMSCodeError = 12, //短信验证码错误
    MTPApiErrorCodeCreateOrderError = 20, //创建订单失败
    MTPApiErrorCodeCreateWechatOrderError = 21,//创建微信订单失败
    MTPApiErrorCodeGoodsNotFound = 22, //商品不存在
    MTPApiErrorCodeOrderNotFound = 23, //订单不存在
    MTPApiErrorCodeAppInfoNotFound = 24, //应用不存在
    MTPApiErrorCodeGoodsInfoNotFound = 25, //找不到商品列表
    MTPApiErrorCodeGoodsRecordNull = 27, //购买的商品列表为空
    MTPApiErrorCodePayTypeNull = 28, //支付方法列表为空
    MTPApiErrorCodeIosInsertOrderFail = 29, //插入订单失败
    MTPApiErrorCodeNotFoundChannel = 30, //找不到对应的支付方式
    MTPApiErrorCodeAppNotPayment = 31, //该应用没有配置支付方式
    MTPApiErrorCodeAuthenticationFailure = 401, //身份验证失败
    MTPApiErrorCodeParamError = -100, //请求参数错误
    MTPApiErrorCodeActionError = -101, //操作失败
    MTPApiErrorCodeSystemError = -199, //系统错误
    MTPApiErrorCodeUnknown = -1, //未知
};

#endif /* MTPConstant_h */
