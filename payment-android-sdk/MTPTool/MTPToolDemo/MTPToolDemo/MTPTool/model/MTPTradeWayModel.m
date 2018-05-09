//
//  MTPTradeWayModel.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/24.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPTradeWayModel.h"

@implementation MTPTradeWayModel

+ (NSArray *)getTradeWayWith:(NSArray *)arr {
    if (!arr || ![arr isKindOfClass:NSArray.class]) {
        return nil;
    }
    NSMutableArray *tradeWays = @[].mutableCopy;
    for (NSString *str in arr) {
        MTPTradeWayModel *model = MTPTradeWayModel.new;
        if([str isEqualToString:@"WechatH5"]) {
            model.tradeWayCode = MTPTradeWayCodeWechat;
            model.tradeWayMsgDescription = @"微信支付";
            model.icon = @"weixinzhifu";
        } else if ([str isEqualToString:@"AlipayH5"]) {
            model.tradeWayCode = MTPTradeWayCodeAli;
            model.tradeWayMsgDescription = @"支付宝";
            model.icon = @"zhifubaozhifu";
        }
        [tradeWays addObject:model];
    }
    return [NSArray arrayWithArray:tradeWays];
}

@end
