# PageStateManager - 页面状态管理者

[![API](https://img.shields.io/badge/API-19%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![jitpack](https://jitpack.io/v/shaoshuai904/PageStatusManager.svg)](https://jitpack.io/#shaoshuai904/PageStateManager)

PageStateManager 可以作用于 `Activity` 、`Fragment`、任意 `View` 对象，
作用对象将作为 `contentView`，通过PageConfig配置类，设置 `LoadingView`、`EmptyView`、`RetryView` 等状态视图。
提供对外方法，控制不同状态view的显示隐藏。

避免对目标对象xml文件的更改，实现一次配置，到处使用。


### 快速使用

**Step 1.** Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2.** Add the dependency

```groovy
dependencies {
	implementation 'com.github.shaoshuai904:PageStateManager:1.0.0'
}
```


###  自定义PageConfig

```java                
public class PigConfig implements PageConfig {

    public PigConfig() {
    }

    @Nullable
    @Override
    public View loadingView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_loading_pig, null, false);
    }

    @Nullable
    @Override
    public View emptyView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_empty_2, null, false);
    }

    @Nullable
    @Override
    public View retryView(Context context) {
        // 不想设置的类型，可直接设置为null
        return null;
    }

    @Nullable
    @Override
    public PageChangeAction getPageChangeAction() {
        return new PageChangeAction() {
            @Override
            public void onShowLoading(View loadingView) {
                super.onShowLoading(loadingView);
                ImageView ivLoading = loadingView.findViewById(R.id.iv_loading);
                Drawable bg = ivLoading.getBackground();
                if (bg instanceof AnimationDrawable) {
                    AnimationDrawable ad = (AnimationDrawable) bg;
                    if (!ad.isRunning()) {
                        ad.start();
                    }
                }
            }
        };
    }
}

```

[完整预览各类用法 -（简单使用类 传送门）](https://github.com/shaoshuai904/PageStateManager/blob/master/app/src/main/java/com/maple/demo/config/MyPageConfig.java)


