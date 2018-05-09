//
//  MTPTool.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPTool.h"
#import "MTPLogger.h"
#import "MTPTextUtils.h"
#import "MTPConstant.h"
#import "MTPHttpRequestManager.h"
#import "MTPSignatureSecret.h"
#import "MTPWebViewManager.h"

@interface MTPTool()

@property (strong, nonatomic) NSString *appId;
@property (strong, nonatomic) NSString *appSecret;

@end

@implementation MTPTool

+ (MTPTool *)sharedMTPTool {
    static MTPTool *sharedMTPToolInstance = nil;
    static dispatch_once_t MTPToolOnceToken;
    dispatch_once(&MTPToolOnceToken, ^{
        sharedMTPToolInstance = [[self alloc] init];
    });
    return sharedMTPToolInstance;
}

- (BOOL)setAppId:(NSString *)appId appSecret:(NSString *)appSecret {
    
    if ([MTPTextUtils isEmpty:appId]) {
        [MTPLogger log:@"appId不能为空！"];
        return NO;
    }
    if ([MTPTextUtils isEmpty:appSecret]) {
        [MTPLogger log:@"appSecret不能为空！"];
        return NO;
    }
    self.appId = appId;
    self.appSecret = appSecret;
    return YES;
}

- (void)mtpToolWithOrderNum:(NSString *)appOrderNum
                     attach:(NSString *)attach
                       mode:(NSString *)mode
                     amount:(long)amound
                productName:(NSString *)productName
                       view:(UIView *)view
                    success:(MTPToolSuccessBlock)successBlock
                    failure:(MTPToolFailureBlock)failureBlock {
    if (amound < 0 || amound == 0) {
        [MTPLogger log:@"金额数格式错误！"];
        return;
    }
    if ([MTPTextUtils isEmpty:productName]) {
        [MTPLogger log:@"商品名不能为空！"];
        return;
    }
    if ([MTPTextUtils isEmpty:appOrderNum]) {
        [MTPLogger log:@"app自定义订单号不能为空！"];
        return;
    }
    NSDictionary *parmas = @{@"paymentType" : mode,
                             @"amount" : @(amound),
                             @"productName" : productName,
                             @"appOrderNum" : appOrderNum,
                             @"attach" : [MTPTextUtils isEmpty:attach] ? @"":attach};
    NSURLRequest *request = [MTPSignatureSecret handleRequestHeadWithKey:self.appSecret
                                                              httpMethod:MTPHttpMethodPost
                                                              parameters:parmas
                                                                     url:[NSString stringWithFormat:@"%@%@",MTPHttpsDebugHost,MTPTradeWeb]
                                                                   payId:self.appId];
    [MTPHttpRequestManager postWithRequest:request
                                   success:^(NSDictionary *data) {
                                       [MTPLogger log:data];
                                       dispatch_sync(dispatch_get_main_queue(), ^{
                                           NSInteger code = [data[@"code"] integerValue];
                                           NSDictionary *result = data[@"result"];
                                           if (code == MTPApiErrorCodeSuccess) {
                                               UIWebView *webView = UIWebView.new;
                                               [view addSubview:webView];
                                               webView.delegate = [MTPWebViewManager sharedManager];
                                               NSString *url = [NSString stringWithFormat:@"%@",result[@"url"]].copy;
                                               NSString *referer = [NSString stringWithFormat:@"%@",result[@"referer"]].copy;
                                               NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]];
                                               [request setValue:referer forHTTPHeaderField:@"Referer"];
                                               [webView loadRequest:request];
                                               successBlock(result);
                                           }
                                       });
                                   } failure:^(NSError *error) {
                                       [MTPLogger log:error];
                                       failureBlock(error);
                                   }];
}

- (void)mtpToolRequestWayWithSuccess:(MTPToolSuccessBlock)successBlock
                             failure:(MTPToolFailureBlock)failureBlock {
    NSURLRequest *request = [MTPSignatureSecret handleRequestHeadWithKey:self.appSecret
                                                              httpMethod:MTPHttpMethodGet
                                                              parameters:@{}
                                                                     url:[NSString stringWithFormat:@"%@%@",MTPHttpsDebugHost,MTPTradeWay]
                                                                   payId:self.appId];
    [MTPHttpRequestManager getWithRequest:request
                                  success:^(NSDictionary *data) {
                                      dispatch_sync(dispatch_get_main_queue(), ^{
                                          [MTPLogger log:data];
                                          NSInteger code = [data[@"code"] integerValue];
                                          if (code == MTPApiErrorCodeSuccess) {
                                              successBlock(data[@"result"]);
                                          }
                                      });
                                  } failure:^(NSError *error) {
                                      [MTPLogger log:error];
                                      failureBlock(error);
                                  }];
}
@end
