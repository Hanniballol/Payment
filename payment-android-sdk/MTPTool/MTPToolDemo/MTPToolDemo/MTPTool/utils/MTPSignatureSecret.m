//
//  MTPSignatureSecret.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPSignatureSecret.h"
#import "MTPConstant.h"
#import "MTPRandom.h"
#import "MTPMD5Digest.h"
#import "OrderedDictionary.h"
#import "MTPHMACAuthentication.h"
#import <UIKit/UIKit.h>

static const uint32_t kRandStringSize = 16;
@implementation MTPSignatureSecret

+ (NSMutableURLRequest *)handleRequestHeadWithKey:(NSString *)paySecret
                      httpMethod:(NSString *)httpMethod
                      parameters:(NSDictionary *)params
                             url:(NSString *)requestUrl
                           payId:(NSString *)payId {
    NSString *contentMd5 = [self getContentMd5];
    NSString *contentType = MTPTextXml;
    NSString *packageName = [self getPackageName];
    NSString *timeStamp = [self getTimeStamp];
    NSString *urlStr = [requestUrl substringFromIndex:MTPHttpsDebugHost.length];
    NSMutableString *authorization = NSMutableString.new;
    
    OrderedDictionary *sortDictionary = [OrderedDictionary dictionaryWithObjectsAndKeys:packageName,MTPXSprApp,timeStamp,MTPXSprDate, nil];
    
    [authorization appendFormat:@"%@\n%@\n%@\n",httpMethod,contentMd5,contentType];
    for (NSString *key in sortDictionary) {
          [authorization appendFormat:@"%@:%@\n",key,sortDictionary[key]];
    }

    [authorization appendString:urlStr];
    NSString *msg = [MTPHMACAuthentication sha256Base64:[NSString stringWithString:authorization] withKey:paySecret];
    NSMutableURLRequest *request=[NSMutableURLRequest requestWithURL:[NSURL URLWithString:requestUrl] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    request.HTTPMethod = httpMethod;
    [request setValue:MTPTextXml forHTTPHeaderField:MTPContentType];
    [request setValue:MTPSDKVersion forHTTPHeaderField:MTPVersion];
    [request setValue:[self getUuid] forHTTPHeaderField:MTPDeviceId];
    [request setValue:timeStamp forHTTPHeaderField:MTPXSprDate];
    [request setValue:packageName forHTTPHeaderField:MTPXSprApp];
    [request setValue:contentMd5 forHTTPHeaderField:MTPContentMD5];
    [request setValue:[NSString stringWithFormat:@"T %@:%@",payId,msg] forHTTPHeaderField:MTPAuthorization];
    if ([httpMethod isEqualToString:MTPHttpMethodPost]) {
        request.HTTPBody = [NSJSONSerialization dataWithJSONObject:params options:NSJSONWritingPrettyPrinted error:nil];;
    }
    return request;
}

+ (NSString *)getContentMd5 {
    return [MTPMD5Digest getmd5WithString:[MTPRandom randomNumeric:kRandStringSize]];
}

+ (NSString *)getPackageName {
    return [[NSBundle mainBundle]bundleIdentifier];
}

+ (NSString *)getTimeStamp {
    return [NSString stringWithFormat:@"%lu", (long)[NSDate date].timeIntervalSince1970 * 1000];
}

+ (NSString *)getUuid {
    return [NSString stringWithFormat:@"IOS%@",[[[UIDevice currentDevice] identifierForVendor] UUIDString]];
}
@end
