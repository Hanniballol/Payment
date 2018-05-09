//
//  MTPTool.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void (^MTPToolCompletioBlock)(NSDictionary *dic, NSURLResponse *response, NSError *error);
typedef void (^MTPToolSuccessBlock)(NSDictionary *data);
typedef void (^MTPToolFailureBlock)(NSError *error);

@interface MTPTool : NSObject

+ (MTPTool *)sharedMTPTool;
- (BOOL)setAppId:(NSString *)appId appSecret:(NSString *)appSecret;
- (void)mtpToolWithOrderNum:(NSString *)appOrderNum
                     attach:(NSString *)attach
                       mode:(NSString *)mode
                     amount:(long)amound
                productName:(NSString *)productName
                       view:(UIView *)view
                    success:(MTPToolSuccessBlock)successBlock
                    failure:(MTPToolFailureBlock)failureBlock;

- (void)mtpToolRequestWayWithSuccess:(MTPToolSuccessBlock)successBlock
                             failure:(MTPToolFailureBlock)failureBlock;
@end
