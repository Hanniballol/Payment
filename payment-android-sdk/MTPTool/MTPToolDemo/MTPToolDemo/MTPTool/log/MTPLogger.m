//
//  MTPLogger.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPLogger.h"

@interface MTPLogger()

@property (assign, nonatomic) BOOL enabled;
@property (strong, nonatomic) NSString *tag;

@end

static MTPLogger *instance;
@implementation MTPLogger

+ (instancetype)shareMTPLogger {
    static MTPLogger *shareMTPLoggerInstance = nil;
    static dispatch_once_t MTPLoggerOnceToken;
    dispatch_once(&MTPLoggerOnceToken, ^{
        shareMTPLoggerInstance = [[self alloc] init];
    });
    return shareMTPLoggerInstance;
}

+ (void)log:(id)format{
    MTPLogger *logger = [MTPLogger shareMTPLogger];
    if (logger.enabled) {
        NSLog(@"%@ : %@",logger.tag,format);
    }
}

+ (void)enable:(BOOL)enabled andTag:(NSString *)tag{
    MTPLogger *logger = [MTPLogger shareMTPLogger];
    logger.enabled = enabled;
    logger.tag = tag;
}

@end
