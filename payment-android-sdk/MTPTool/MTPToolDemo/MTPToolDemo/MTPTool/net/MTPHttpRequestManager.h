//
//  MTPHttpRequestManager.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef void (^MTPCompletioBlock)(NSDictionary *dic, NSURLResponse *response, NSError *error);
typedef void (^MTPSuccessBlock)(NSDictionary *data);
typedef void (^MTPFailureBlock)(NSError *error);

@interface MTPHttpRequestManager : NSObject

/**
 * get请求
 */
+ (void)getWithRequest:(NSURLRequest *)request success:(MTPSuccessBlock)successBlock failure:(MTPFailureBlock)failureBlock;

/**
 * post请求
 */
+ (void)postWithRequest:(NSURLRequest *)request success:(MTPSuccessBlock)successBlock failure:(MTPFailureBlock)failureBlock;

@end
