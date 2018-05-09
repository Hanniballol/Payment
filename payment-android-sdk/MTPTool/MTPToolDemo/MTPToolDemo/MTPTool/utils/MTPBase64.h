//
//  MTPBase64.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MTPBase64 : NSObject

+ (NSString *)encode:(NSData *)inputData;
+ (NSData *)decode:(NSString *)inputString;

@end
