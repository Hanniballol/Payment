//
//  MTPTradeWayModel.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/24.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MTPConstant.h"

@interface MTPTradeWayModel : NSObject

@property (assign, nonatomic) MTPTradeWayCode tradeWayCode;
@property (strong, nonatomic) NSString *icon;
@property (strong, nonatomic) NSString *tradeWayMsgDescription;

+ (NSArray *)getTradeWayWith:(NSArray *)arr;
@end
