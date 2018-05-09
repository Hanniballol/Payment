//
//  MTPHMACAuthentication.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPHMACAuthentication.h"
#import "MTPBase64.h"
#import <CommonCrypto/CommonDigest.h>
#import <CommonCrypto/CommonHMAC.h>

@implementation MTPHMACAuthentication

+ (NSData *)hmac:(NSString *)plaintext withKey:(NSString *)key {
    NSData *keyData = [key dataUsingEncoding:NSASCIIStringEncoding];
    NSData *plaintextData = [plaintext dataUsingEncoding:NSASCIIStringEncoding];
    NSMutableData *macOut = [NSMutableData dataWithLength:CC_SHA256_DIGEST_LENGTH];

    CCHmac( kCCHmacAlgSHA256,
           keyData.bytes,
           keyData.length,
           plaintextData.bytes,
           plaintextData.length,
           macOut.mutableBytes);
    return macOut;
}

+ (NSString *)sha256Base64:(NSString *)plaintext withKey:(NSString *)key {
    NSData *str = [self hmac:plaintext withKey:key];
    return [MTPBase64 encode:str];
}

@end
