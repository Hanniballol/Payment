//
//  MTPTradeWayPopupView.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/24.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MTPConstant.h"

@interface MTPTradeWayPopupView : UIView

@property (nonatomic,copy)void(^selectBlock)(MTPTradeWayCode code);
- (instancetype)initWithTradeWayArr:(NSArray *)arr AndOffset:(BOOL)offset;

@end
