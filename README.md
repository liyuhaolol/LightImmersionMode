# LightImmersionMode

十分方便的实现纯色沉浸式状态栏的开发，只需要使用代码，不需要在布局额外写任何东西

# 1.0.0更新

- 支持activity，fragment中兼容沉浸式状态栏
- 可以添加临时config在单独的activity起效，不影响全局配置
- 兼容浅色状态栏，自动变为深色字体（需要系统支持）

## 主要的类介绍

- `ImmersionMode` : 沉浸式状态栏的主体类
- `ImmersionConfiguration` : 沉浸式状态栏的配置类
- `TemporaryConfig` : 沉浸式状态栏的临时配置类

## 使用方式

- 本框架沉浸式效果仅支持`API LEVEL >= 19`以上的系统才可以生效对应效果
- 本框架浅色状态栏，自适应字体仅支持MIUI或者Flyme系统，或者`API LEVEL >= 23`的其他系统才会有对应效果
- 本框架支持最低`API LEVEL >= 8`但是`API LEVEL < 19`，会造成本框架不运行，不会造成程序崩溃

先完成`ImmersionMode`和`ImmersionConfiguration`的初始化配置

```java
//初始化对应的配置
ImmersionConfiguration configuration = new ImmersionConfiguration
        .Builder(this)
        .enableImmersionMode(ImmersionConfiguration.ENABLE)
        .defaultColor(R.color.bar_color)
        .build();
//完成ImmersionMode的配置初始化
ImmersionMode.getInstance().init(configuration);
```
在activity的`onResume()`生命周期中调用对应方法来完成

```java
@Override
protected void onResume() {
    super.onResume();
    immersionMode = ImmersionMode.getInstance();
    immersionMode.execImmersionMode(this);
}
```

修改临时配置的方法，设置的为TemporaryConfig，一次性不会影响默认的配置

```java
//使用资源的colorID赋值
public void changeStatusBarColor(int ResId){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
        TemporaryConfig tConfig = new TemporaryConfig(this);
        tConfig.setTemporaryResIdColor(ResId);
        immersionMode.setTemporaryConfig(tConfig);
        immersionMode.execImmersionMode(this);
    }
}
//使用String的Color
public void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
        TemporaryConfig tConfig = new TemporaryConfig(this);
        tConfig.setTemporaryStringColor(color);
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

- `ImmersionConfiguration`和`TemporaryConfig`的属性是一样的

- `ImmersionConfiguration`的属性设置方法

    `enableImmersionMode()`:是否启动沉浸式状态栏，默认为：`ENABLE`

    `defaultColor()`:传入资源id或者String型Color，默认为：`#D0D0D0`

- `TemporaryConfig`的属性设置方法

    `setEnable()`:是否启动沉浸式状态栏，默认为：`0`

    `setTemporaryResIdColor()`:传入资源id型Color，默认为：`#D0D0D0`

    `setTemporaryStringColor()`:传入String型Color，默认为：`#D0D0D0`

## 框架的完成思路

- 沉浸式状态栏的实现思路

    `API LEVEL >= 19 && API LEVEL < 21`:

    使用`window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);`开启沉浸式并在状态栏位置添加一个带有对应颜色的View填充

    `API LEVEL >= 21`:
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

-因为`API LEVEL >= 19 && API LEVEL < 21`时，使用的沉浸式开发，需要或得对应的`ViewGroup`进行设置，所以如果本框架的方法在`onCreate()`方法中调用，`Android 5.0`以下的系统会无法获取到对应的`View`造成`空指针`，所以如果你开发的项目最低`API LEVEL >= 19`，那么推荐本框架在`onResume()`方法中调用，如果你的最低`API LEVEL >= 21`，则可以在任意生命周期里调用

## 联系方式

- Github: https://github.com/liyuhaolol
- 博客: http://blog.csdn.net/ccffvii
- 邮箱: liyuhaoid@sina.com

有任何意见和问题，欢迎在issues中提出，一定尽快回复。
