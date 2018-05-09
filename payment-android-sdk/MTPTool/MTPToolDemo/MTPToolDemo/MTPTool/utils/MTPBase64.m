//
//  MTPBase64.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPBase64.h"

@implementation MTPBase64

+ (NSString *)encode:(NSData *)inputData {
    return [inputData base64EncodedStringWithOptions:NSDataBase64EncodingEndLineWithLineFeed];
}

+ (NSData *)decode:(NSString *)inputString {
    return [[NSData alloc] initWithBase64EncodedString:inputString
                                               options:NSDataBase64DecodingIgnoreUnknownCharacters];
}

@end
