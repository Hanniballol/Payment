//
//  MTPTextUtils.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPTextUtils.h"

@implementation MTPTextUtils

+ (BOOL)isEmpty:(NSString *)string {
    return [string isEqualToString:@""] || !string;
}
@end
