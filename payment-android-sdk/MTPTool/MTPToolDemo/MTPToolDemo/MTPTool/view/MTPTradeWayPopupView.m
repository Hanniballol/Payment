//
//  TradeWayPopupView.m
//  MTPToolDemo
//
//  Created by 陈荐东 on 2017/11/24.
//  Copyright © 2017年 陈荐东. All rights reserved.
//

#import "MTPTradeWayPopupView.h"
#import "MTPTradeWayCollectionViewCell.h"
#import "MTPTradeWayModel.h"

#define screenW  [UIScreen mainScreen].bounds.size.width
#define itemWH (screenW - (cols - 1) * margin) / cols

static NSInteger const cols = 4; // 4 列
static CGFloat const margin = 1; //间隔
static CGFloat cancleHeight = 44; //取消按钮高度
static CGFloat headerHeight = 48; //取消按钮高度
static CGFloat animationTime = 0.25; //动画时间。从下面移动到上面

@interface MTPTradeWayPopupView ()<UICollectionViewDelegate,UICollectionViewDataSource>

@property (nonatomic, strong) UIView *bgView;                   //   背景view
@property (nonatomic, strong) UIButton *cancleBtn;              //   取消view
@property (nonatomic, strong) UIView *headView;                 //   文字view
@property (nonatomic, strong) NSMutableArray *titlrArr;
@property (nonatomic, strong) NSMutableArray *imageArr;
@property (nonatomic, strong) NSMutableArray *tradeWayArr;
@property(nonatomic,strong)UICollectionView *collectionView;
@property (nonatomic,assign)BOOL isOffset;                      //是否偏移
@property (nonatomic,assign)CGFloat bgViewHeith;                //显示白色界面父视图高度;
@end

@implementation MTPTradeWayPopupView

- (instancetype)initWithTradeWayArr:(NSArray *)arr AndOffset:(BOOL)offset{
    self = [super init];
    if (self) {
        _bgViewHeith = 160 ;//默认
        self.titlrArr = @[].mutableCopy;
        self.imageArr = @[].mutableCopy;
        self.tradeWayArr = @[].mutableCopy;
        for (MTPTradeWayModel *model in arr) {
            [self.titlrArr addObject:model.tradeWayMsgDescription];
            [self.imageArr addObject:model.icon];
            [self.tradeWayArr addObject:@(model.tradeWayCode)];
        }
        
        self.isOffset = offset ;
        [self initSubViewUI]; //初始化UI
    }
    
    return self;
}

- (void)initSubViewUI{
    
    UIWindow *windowView = [self lastWindow];
    self.frame = [UIApplication sharedApplication].keyWindow.bounds;
    [windowView addSubview:self];
    self.backgroundColor = [UIColor clearColor];
    [self addSubview:self.bgView]; //白色底图父视图
    
    //    [self fuzzy];
    [self.bgView addSubview:self.headView];   //分享至
    [self.bgView addSubview:self.cancleBtn];  //取消按钮
    [self colletionUI];
    [self showPickView]; //显示
}

- (UIWindow *)lastWindow{
    NSArray *windows = [UIApplication sharedApplication].windows;
    for(UIWindow *window in [windows reverseObjectEnumerator]) {
        if ([window isKindOfClass:[UIWindow class]] &&
            CGRectEqualToRect(window.bounds, [UIScreen mainScreen].bounds))
            return window;
    }
    return [UIApplication sharedApplication].keyWindow;
}

//毛玻璃
-(void)fuzzy{
    UIBlurEffect *beffect = [UIBlurEffect effectWithStyle:UIBlurEffectStyleLight];
    UIVisualEffectView *view = [[UIVisualEffectView alloc]initWithEffect:beffect];
    view.frame = self.bgView.bounds;
    [self.bgView addSubview:view];
}

#pragma mark - UI
- (UIView *)bgView{
    if (!_bgView) {
        //初始化设置在屏幕之外
        NSInteger count = self.imageArr.count;
        NSInteger rows = (count - 1) / cols + 1; //计算有多少列
        _bgViewHeith = itemWH * rows + cancleHeight+headerHeight + 10; //计算bgviewHeight 的动态高度 ,加 30 是我觉得好看
        _bgView = [[UIView alloc] initWithFrame:CGRectMake(0, self.frame.size.height, self.frame.size.width, _bgViewHeith)];
        _bgView.backgroundColor = UIColor.whiteColor;
    }
    return _bgView;
}

- (UIView *)headView {
    if (!_headView) {
        _headView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, headerHeight)];
        
        UILabel *titleLabel = [[UILabel alloc] initWithFrame:_headView.frame];
        titleLabel.textAlignment = NSTextAlignmentCenter;
        titleLabel.text =@"支付方式";
        titleLabel.font = [UIFont systemFontOfSize:16];
        [_headView addSubview:titleLabel];
        
        UIView *lineView = [[UIView alloc] initWithFrame:CGRectMake(0, headerHeight-1, self.frame.size.width, 1)];
        lineView.backgroundColor = [UIColor colorWithRed:204/255 green:204/255 blue:204/255 alpha:1.0f];
        [_headView addSubview:lineView];
        
    }
    return _headView;
}

- (UIButton *)cancleBtn{
    if (!_cancleBtn) {
        _cancleBtn =[UIButton buttonWithType:UIButtonTypeCustom];
        _cancleBtn.frame=CGRectMake(0, self.bgView.frame.size.height-cancleHeight, self.frame.size.width, cancleHeight);
        _cancleBtn.layer.borderWidth = 0.5;
        [_cancleBtn setTitle:@"取消" forState:UIControlStateNormal];
        [_cancleBtn setTitleColor:[UIColor colorWithRed:102/255 green:102/255 blue:102/255 alpha:1.0f] forState:UIControlStateNormal];
        _cancleBtn.layer.borderColor = [UIColor grayColor].CGColor;
        [_cancleBtn addTarget:self action:@selector(cancleAction) forControlEvents:UIControlEventTouchUpInside];
    }
    return _cancleBtn;
}

-(void)colletionUI{
    //此处必须要有创见一个UICollectionViewFlowLayout的对象
    UICollectionViewFlowLayout *layout=[[UICollectionViewFlowLayout alloc]init];
    
    layout.itemSize = CGSizeMake(itemWH, itemWH);
    layout.minimumInteritemSpacing = margin;
    layout.minimumLineSpacing = margin;
    
    _collectionView=[[UICollectionView alloc]initWithFrame:CGRectMake(0, cancleHeight+15, screenW, 100) collectionViewLayout:layout];
    _collectionView.backgroundColor=[UIColor clearColor];
    _collectionView.delegate=self;
    _collectionView.dataSource=self;
    //这个是横向滑动
    layout.scrollDirection=UICollectionViewScrollDirectionVertical;
    [self.bgView addSubview:_collectionView];
    
    [_collectionView registerClass:[MTPTradeWayCollectionViewCell class] forCellWithReuseIdentifier:@"TradeWayCollectionViewCell"];
    
    NSInteger count = self.imageArr.count;
    NSInteger rows = (count - 1) / cols + 1;
    CGRect rect =  _collectionView.frame;
    rect.size.height = rows * itemWH + 30  ;
    _collectionView.frame = rect ;
    _collectionView.showsHorizontalScrollIndicator = NO ;
}

//取消
-(void)cancleAction{
    [self hidePickView];
}


#pragma mark private methods  私有方法
//显示
- (void)showPickView{
    if (self.isOffset) { //要偏移64
        [UIView animateWithDuration:animationTime animations:^{
            self.bgView.frame = CGRectMake(0, self.frame.size.height - _bgViewHeith - 64, self.frame.size.width, _bgViewHeith);
        } completion:^(BOOL finished) {
        }];
        
    }else{
        //不偏移
        [UIView animateWithDuration:animationTime animations:^{
            self.bgView.frame = CGRectMake(0, self.frame.size.height - _bgViewHeith , self.frame.size.width, _bgViewHeith);
        } completion:^(BOOL finished) {
            
        }];
    }
}

//隐藏
- (void)hidePickView {
    if (self.isOffset) {
        [UIView animateWithDuration:animationTime animations:^{
            self.bgView.frame = CGRectMake(0, self.frame.size.height+64, self.frame.size.width, _bgViewHeith);
        } completion:^(BOOL finished) {
            [self removeFromSuperview];
        }];
    }else{
        [UIView animateWithDuration:animationTime animations:^{
            self.bgView.frame = CGRectMake(0, self.frame.size.height, self.frame.size.width, _bgViewHeith);
        } completion:^(BOOL finished) {
            [self removeFromSuperview];
        }];
    }
    
}

-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    if ([touches.anyObject.view isKindOfClass:[self class]]) {
        [self hidePickView];
    }
}


#pragma mark UIcollectionDelegate
//一共有多少个组
-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView{
    return 1;
}
//每一组有多少个cell
-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section{
    return self.titlrArr.count;
}
//每一个cell是什么
-(UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
    MTPTradeWayCollectionViewCell *thirdShareCell=[collectionView dequeueReusableCellWithReuseIdentifier:@"TradeWayCollectionViewCell"
                                                                                         forIndexPath:indexPath];
    thirdShareCell.itemwh = itemWH;
    NSString *iamgeStr = self.imageArr[indexPath.row];
    NSString *titltrStr= self.titlrArr[indexPath.row];
    thirdShareCell.platformImageView.image = [UIImage imageNamed:iamgeStr];
    thirdShareCell.platformLabel.text = titltrStr ;
    
    return thirdShareCell;
}

//定义每一个cell的大小
- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath{
    return CGSizeMake(itemWH, itemWH);
}

//每一个分组的上左下右间距
-(UIEdgeInsets)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout *)collectionViewLayout insetForSectionAtIndex:(NSInteger)section{
    return UIEdgeInsetsMake(0, 0, 0, 0);
}

-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
    MTPTradeWayCode code = [self.tradeWayArr[indexPath.row] integerValue];
    self.selectBlock(code);
    [self hidePickView];
}

@end

