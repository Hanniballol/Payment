//
//  MTPHttpRequestManager.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPHttpRequestManager.h"
#import "MTPLogger.h"
#import "MTPHMACAuthentication.h"

@implementation MTPHttpRequestManager

+ (void)getWithRequest:(NSURLRequest *)request success:(MTPSuccessBlock)successBlock failure:(MTPFailureBlock)failureBlock {
    NSURLSession *urlSession = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [urlSession dataTaskWithRequest:request
                                                   completionHandler:^(NSData * _Nullable data,
                                                                       NSURLResponse * _Nullable response,
                                                                       NSError * _Nullable error) {
                                                       if (error) {
                                                           failureBlock(error);
                                                       } else {
                                                           NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data
                                                                                                               options:NSJSONReadingMutableContainers
                                                                                                                 error:nil];
                                                           successBlock(dic);
                                                       }
                                                   }];
    [dataTask resume];
}

+ (void)postWithRequest:(NSURLRequest *)request success:(MTPSuccessBlock)successBlock failure:(MTPFailureBlock)failureBlock {
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLSessionDataTask *dataTask = [session dataTaskWithRequest:request
                                                completionHandler:^(NSData * _Nullable data,
                                                                    NSURLResponse * _Nullable response,
                                                                    NSError * _Nullable error) {
        if (error) {
            failureBlock(error);
        } else {
            NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data
                                                                options:NSJSONReadingMutableContainers
                                                                  error:nil];
            successBlock(dic);
        }
    }];
    [dataTask resume];
}


@end
