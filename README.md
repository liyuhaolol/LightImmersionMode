# LightImmersionMode

十分方便的实现纯色沉浸式状态栏的开发，只需要使用代码，不需要在布局额外写任何东西

# 效果演示

<div><img src='https://github.com/liyuhaolol/LightImmersionMode/blob/master/pic/01.gif' width="480px"/></div>

# 1.0.6更新

- 将`Android5.0`以下的状态栏颜色显示状态统一交付给Android底层处理，用来统一处理浅色状态栏字体显示不清的问题，具体影响为`API LEVEL = 21`的设备将无法设置你指定的状态栏颜色，因为底层会对你传来的状态栏进行二次处理。具体表现形式为`API LEVEL = 19`的设备将显示齿状暗纹，`API LEVEL = 21`的设备将整体显示变灰，目前已在原生系统上测试通过，本框架将不会对小于`Android6.0`暴力深度定制的第三方rom出现的问题进行适配，只会徒增烦恼而已。

# 1.0.5更新

- 优化代码，保存手机型号版本从而使匹配机型报错提示只出现一次

# 1.0.4更新

- 修复对MIUI开发版7.7.13以上的支持，就是支持从2017年7月13日以后发布的所有MIUI稳定版和开发版，不更新会造成浅色模式在MIUI上失效。

# 1.0.3更新

- 修复对Flyme6.0以上的支持，这个锅是魅族的，他偷偷改了api

# 1.0.2更新

- 添加关闭框架的方法演示
- 添加`Android4.4`显示或者隐藏插入的statusView方法演示
- 删除了TemporaryConfig类，使用ImmersionConfiguration来完成
- 大幅度修改框架运行逻辑，避免在`Android4.4`中，出现的种种BUG
- `Android4.4`实现沉浸式较为复杂，所以只能使用较为复杂的逻辑来保证不会出现BUG，`Android5.0`以上都是非常简单的调用，不用考虑生命周期，也不用考虑调用方法先后顺序。如果有需要单独大于`Android5.0`的框架，请在issues中留言，我会酌情考虑。

## 引用方法

- 在gradle中:
```
    compile 'spa.lyh.cn:immersion-sdk:1.0.6'
```

- 在maven中：
```

<dependency>
	<groupId>spa.lyh.cn</groupId>
	<artifactId>immersion-sdk</artifactId>
	<version>1.0.6</version>
	<type>pom</type>
</dependency>
```

## 主要的类介绍

- `ImmersionMode` : 沉浸式状态栏的主体类
- `ImmersionConfiguration` : 沉浸式状态栏的配置类

## 使用方式

- 本框架沉浸式效果仅支持`API LEVEL >= 19`以上的系统才可以生效对应效果
- 本框架浅色状态栏，自适应字体仅支持MIUI或者Flyme系统，或者`API LEVEL >= 23`的其他系统才会有对应效果
- 本框架支持最低`API LEVEL >= 11`但是`API LEVEL < 19`，会造成本框架不运行，不会造成程序崩溃

先完成`ImmersionMode`和`ImmersionConfiguration`的初始化配置

```java
//初始化对应的配置
ImmersionConfiguration configuration = new ImmersionConfiguration
        .Builder(this)
        .enableImmersionMode(ImmersionConfiguration.ENABLE)
        .setColor(R.color.bar_color)
        .build();
//完成ImmersionMode的配置初始化
ImmersionMode.getInstance().init(configuration);
```
在activity的`onCreate()`生命周期中完成单例，并需要重写`setContentView()`方法，在其中完成方法调用

```java
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersionMode = ImmersionMode.getInstance();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        immersionMode.execImmersionMode(this);
    }
}
```

修改临时配置的方法，一次性的设置不会影响默认的配置，再次加载会重新加载默认设置

```java
    //使用资源的colorID赋值
    public void changeStatusBarColor(int ResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            ImmersionConfiguration tConfig = new ImmersionConfiguration.Builder(this)
                    .setColor(ResId)
                    .build();
            immersionMode.setTemporaryConfig(tConfig);
            immersionMode.execImmersionMode(this);
        }
    }
    //使用String的Color
    public void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            ImmersionConfiguration tConfig = new ImmersionConfiguration.Builder(this)
                    .setColor(color)
                    .build();
            immersionMode.setTemporaryConfig(tConfig);
            immersionMode.execImmersionMode(this);
        }
    }
```

## Config的所有属性介绍

```java
//启动
public static int ENABLE = 100;
//不启动
public static int DISABLE = 101;
```

- `ImmersionConfiguration`的属性设置方法

    `enableImmersionMode()`:是否启动沉浸式状态栏，默认为：`ENABLE`

    `setColor()`:传入资源id或者String型Color，默认为：`#D0D0D0`

## 框架的完成思路

- 沉浸式状态栏的实现思路

    `API LEVEL >= 19 && API LEVEL < 23`:

    使用`window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);`开启沉浸式并在状态栏位置添加一个带有对应颜色的View填充

    `API LEVEL >= 23`:
    ```java
    Window window = activity.getWindow();
    window.setStatusBarColor(color);
    ```
    实现方式为直接修改状态栏颜色，非沉浸式实现

- 浅色状态栏的实现思路
    系统必须为MIUI或者Flyme或者`API LEVEL >= 23`的其他系统，这里不展示MIUI与Flyme的实现思路只展示原生的开发思路

    ```java
    //设置为浅色状态栏模式，深色字体
    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    //设置为普通的状态栏模式，浅色字体
    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    ```
- 深浅色判断逻辑

    通过YUV模式，用色彩明亮度，色度，浓度来判断深浅色
    ```java
    public static boolean isLightRGB(int[] colors){
        int grayLevel = (int) (colors[0] * 0.299 + colors[1] * 0.587 + colors[2] * 0.114);
        if(grayLevel>=192){
            return true;
        }
        return false;
    }
    ```
## 注意事项

- 因为`API LEVEL >= 19 && API LEVEL < 23`时，使用的沉浸式开发，方法`immersionMode.execImmersionMode(this);`会在`setContentView()`完成以后立刻调用，又因为沉浸式开发，是不可逆的设置，意思就是如果开启沉浸式，除非重新加载`Activity`否则无法翻转此状态。所以想要关闭本框架，那么对应方法必须在`setContentView()`之前调用，否则在小于`Android5.0`上会出现框架依然启动的BUG。
- 最低`API LEVEL >= 23`时，可以无视上述问题，因为`Android6.0`以上并没有使用沉浸式开发，所以使用本框架可以不许重写`setContentView()`等种种限制运行顺序和生命周期的方式，只要在`onResume()`之前任意位置调用即可。


## 联系方式

- Github: https://github.com/liyuhaolol
- 博客: http://blog.csdn.net/ccffvii
- 邮箱: liyuhaoid@sina.com

有任何意见和问题，欢迎在issues中提出，一定尽快回复。
