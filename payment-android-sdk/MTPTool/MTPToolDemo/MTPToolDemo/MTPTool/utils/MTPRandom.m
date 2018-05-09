//
//  MTPRandom.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPRandom.h"

static NSString *const kAlphanumerics = @"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
static NSString *const kAlphabetics = @"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
static NSString *const kNumerics = @"123456789";

@implementation MTPRandom

+ (NSString *)randomAlphanumeric:(uint32_t)len {
    NSMutableString *randomString = [NSMutableString stringWithCapacity: len];
    for (int i = 0; i < len; i++) {
        [randomString appendFormat: @"%C",
         [kAlphanumerics characterAtIndex:arc4random_uniform((uint32_t)kAlphanumerics.length)]];
    }
    return randomString;
}

+ (NSString *)randomAlphabetic:(uint32_t)len {
    NSMutableString *randomString = [NSMutableString stringWithCapacity: len];
    for (int i = 0; i < len; i++) {
        [randomString appendFormat: @"%C",
         [kAlphabetics characterAtIndex:arc4random_uniform((uint32_t)kAlphabetics.length)]];
    }
    return randomString;
}

+ (NSString *)randomNumeric:(uint32_t)len {
    NSMutableString *randomString = [NSMutableString stringWithCapacity: len];
    for (int i = 0; i < len; i++) {
        [randomString appendFormat: @"%C",
         [kNumerics characterAtIndex:arc4random_uniform((uint32_t)kNumerics.length)]];
    }
    return randomString;
}

@end
