//
//  MTPRandom.h
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MTPRandom : NSObject

/*!
 * @method randomAlphanumeric:
 * @abstract 生成指定长度的字母、数字随机字符串
 * @param size 随机字符串长度
 * @return 返回随机字符串
 */
+ (NSString *)randomAlphanumeric:(uint32_t)size;

/*!
 * @method randomAlphabetic:
 * @abstract 生成指定长度的字母随机字符串
 * @param size 随机字符串长度
 * @return 返回随机字符串
 */
+ (NSString *)randomAlphabetic:(uint32_t)size;

/*!
 * @method randomNumeric:
 * @abstract 生成指定长度的数字随机字符串
 * @param size 随机字符串长度
 * @return 返回随机字符串
 */
+ (NSString *)randomNumeric:(uint32_t)size;

@end
