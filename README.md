> 简化第三方社会化登陆/分享模块的使用，简化开发者的对各个版本API的学习使用。本模块儿不针对app使用的统计，
提供给某些不希望被umeng统计用户数据的场景

proguard config:

    -dontwarn com.rdshoep.android.social.**
    -keep class com.rdshoep.android.social.** { *; }
