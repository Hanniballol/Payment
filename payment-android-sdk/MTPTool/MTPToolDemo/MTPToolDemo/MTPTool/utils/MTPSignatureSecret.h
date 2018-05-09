//
//  MTPSignatureSecret.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MTPSignatureSecret : NSObject

+ (NSMutableURLRequest *)handleRequestHeadWithKey:(NSString *)paySecret
                                       httpMethod:(NSString *)httpMethod
                                       parameters:(NSDictionary *)params
                                              url:(NSString *)requestUrl
                                            payId:(NSString *)payId;

@end
