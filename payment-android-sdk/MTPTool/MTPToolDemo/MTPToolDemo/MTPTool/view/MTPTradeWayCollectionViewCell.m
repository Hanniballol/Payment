//
//  MTPTradeWayCollectionViewCell.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/24.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPTradeWayCollectionViewCell.h"

@implementation MTPTradeWayCollectionViewCell

- (UIImageView *)platformImageView {
    if (!_platformImageView) {
        _platformImageView = [[UIImageView alloc] initWithFrame:CGRectMake(_itemwh*0.25, _itemwh*0.1, _itemwh*0.5, _itemwh*0.5)];
        [self.contentView addSubview:_platformImageView];
    }
    return _platformImageView;
}



- (UILabel *)platformLabel {
    if (!_platformLabel) {
        _platformLabel = [[UILabel alloc] initWithFrame:CGRectMake(_itemwh*0.2, _itemwh*0.65, _itemwh*0.6, _itemwh*0.2)];
        _platformLabel.font = [UIFont systemFontOfSize:13];
        _platformLabel.textColor = [UIColor blackColor];
        _platformLabel.textAlignment = NSTextAlignmentCenter;
        _platformLabel.adjustsFontSizeToFitWidth = YES;
        [self.contentView addSubview:_platformLabel];
    }
    return _platformLabel;
}

@end
