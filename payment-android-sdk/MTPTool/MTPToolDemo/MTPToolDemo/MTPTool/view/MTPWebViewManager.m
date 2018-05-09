//
//  MTPWebViewManager.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/24.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPWebViewManager.h"

@implementation MTPWebViewManager

+(instancetype)sharedManager {
    static dispatch_once_t mTpWebViewManageronceToken;
    static MTPWebViewManager *instance;
    dispatch_once(&mTpWebViewManageronceToken, ^{
        instance = [[MTPWebViewManager alloc] init];
    });
    return instance;
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType {
    NSString* reqUrl = request.URL.absoluteString;
    NSLog(@"hannibal 📌: %@",reqUrl);
    if ([reqUrl hasPrefix:@"weixin://"] || [reqUrl hasPrefix:@"weixins://"]
        || [reqUrl hasPrefix:@"alipay://"] || [reqUrl hasPrefix:@"alipays://"]) {
        [[UIApplication sharedApplication]openURL:request.URL
                                          options:@{}
                                completionHandler:^(BOOL success) {
                                    [webView removeFromSuperview];
                                }];
    }
    return YES;
}

- (void)webViewDidFinishLoad:(UIWebView *)webView {
    NSString *msg = [webView stringByEvaluatingJavaScriptFromString:@"document.documentElement.innerHTML"];
    NSLog(@"hannibal msg : %@",msg);
}
@end
