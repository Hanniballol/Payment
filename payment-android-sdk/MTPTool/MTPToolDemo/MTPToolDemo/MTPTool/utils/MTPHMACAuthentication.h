//
//  MTPHMACAuthentication.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MTPHMACAuthentication : NSObject

+ (NSString *)sha256Base64:(NSString *)plaintext withKey:(NSString *)key;

@end
