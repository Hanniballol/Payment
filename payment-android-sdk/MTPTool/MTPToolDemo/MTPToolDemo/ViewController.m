//
//  ViewController.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/22.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "ViewController.h"
#import "MTPTool.h"
#import "MTPLogger.h"
#import "MTPHMACAuthentication.h"
#import "MTPTradeWayPopupView.h"
#import "MTPTradeWayModel.h"
#import "MTPWebViewManager.h"

static NSString *const kAppId = @"pay692c1b60a78d18be";
static NSString *const kAppSecret = @"a2f9ecb50e01265d5d6cc70e03d8c5653b2b0431eed0b9a82a71da06320f1bb9";
@interface ViewController ()

@property (strong, nonatomic) IBOutlet UITextField *orderNumTextField;
@property (strong, nonatomic) IBOutlet UITextField *attachTextField;
@property (strong, nonatomic) IBOutlet UIButton *nativeButton;
@property (strong, nonatomic) IBOutlet UIButton *custemButton;


@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [MTPLogger enable:YES andTag:@"hannibal"];
//    [self.view addSubview:_webView];
    [[MTPTool sharedMTPTool] setAppId:kAppId
                            appSecret:kAppSecret];
//    [[MTPTool sharedMTPTool] mtpToolWechatWithOrderNum:@"131basdfakjadjlaasdfadfaadsffahxkjha3"
//                                attach:@"123"
//                                  mode:@"WechatH5"
//                                amount:1
//                           productName:@"测试"
//                               success:^(NSDictionary *data) {
//                                   NSString *url = [NSString stringWithFormat:@"%@",data[@"result"][@"url"]].copy;
//                                   NSString *referer = [NSString stringWithFormat:@"%@",data[@"result"][@"referer"]].copy;
//
//                                   NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:url]
//                                                                                          cachePolicy:NSURLRequestUseProtocolCachePolicy
//                                                                                      timeoutInterval:10.0];
//                                   [request setValue:referer forHTTPHeaderField:@"Referer"];
//                                   [_webView loadRequest:request];
//                               } failure:^(NSError *error) {
//
//                               }];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    [self.orderNumTextField resignFirstResponder];
    [self.attachTextField resignFirstResponder];
}

- (IBAction)buttonClickEvent:(id)sender {
    switch ([sender tag]) {
        case 100:
        {
            [[MTPTool sharedMTPTool] mtpToolRequestWayWithSuccess:^(NSDictionary *data) {
                NSArray *arr = [MTPTradeWayModel getTradeWayWith:(NSArray *)data];
                [self showTradeWayViewWithTradeWayArr:arr];
            } failure:^(NSError *error) {
                
            }];
        }
            break;
        case 110:
            
            break;
    }
}

- (void)showTradeWayViewWithTradeWayArr:(NSArray *)arr {
    MTPTradeWayPopupView *popupView = [[MTPTradeWayPopupView alloc] initWithTradeWayArr:arr AndOffset:NO];
    popupView.selectBlock=^(MTPTradeWayCode code){
        switch (code) {
            case MTPTradeWayCodeWechat:
                [self tradeWithWay:MTPWechatTrade];
                break;
            case MTPTradeWayCodeAli:
                [self tradeWithWay:MTPAliTrade];
                break;
        }
    };
    
    UIApplication *applaction = [UIApplication sharedApplication];
    [applaction.keyWindow addSubview:popupView];
}

- (void)tradeWithWay:(NSString *)way {
    [[MTPTool sharedMTPTool] mtpToolWithOrderNum:_orderNumTextField.text
                                          attach:_attachTextField.text
                                            mode:way
                                          amount:1
                                     productName:@"测试1"
                                            view:self.view
                                         success:^(NSDictionary *data) {
                                             
                                         } failure:^(NSError *error) {
                                             
                                         }];
}

@end
