# What is this?
Implementation of banner with texts, images and links 

This implementation is based on `Theme.AppCompat` then the application must implement it

Example 
```
<resources>
    <!-- Base application theme. -->
    <style name="BaseTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryVariant">@color/purple_700</item>
        <item name="colorOnPrimary">@color/white</item>
    </style>
    <style name="BaseBlack" parent="BaseTheme">
        <item name="colorPrimary">@color/purple_500</item>
        <item name="colorPrimaryVariant">@color/purple_700</item>
        <item name="colorOnPrimary">@color/black</item>
        <item name="android:background">@color/black</item>
        <item name="android:textColor">@color/white</item>
    </style>
</resources>

<uses-permission android:name="android.permission.INTERNET"/>
<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/BaseTheme|BaseBlack">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
```

build.gradle:Module `implementation 'com.github.Allanksr:Android-BannerSlider:0.1.0'`

build.gradle:Project 
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

In the activity that wants to instantiate the banner

For urls images `private lateinit var bannerUrl: InjectUrl`

For drawable folder images `private lateinit var bannerDrawable: InjectDrawable`

In the layout linked to the activity 
```
 <LinearLayout
        android:id="@+id/bannerLL"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:paddingTop="20dip" />
```

In the activity that wants to instantiate the banner

```
        val arrayName = arrayOf(
            "Meu Github",
            "Meu site pessoal",
            "Aplicativos"
        )
        val arrayImage = arrayOf(
            "https://my-image1",
            "https://my-image2",
            "https://my-image3"
        )
        val arrayImageDrawable = arrayOf(
             R.drawable.image1,
             R.drawable.image1,
             R.drawable.image1
        )
        val arrayUrl = arrayOf(
            "https://github.com/Allanksr",
            "https://www.allanksr.com",
            "https://play.google.com/store/apps/dev?id=6061636122303064973"
        )
        
        // bannerUrl = InjectUrl(this, 2f, 3L)
       // val layoutView = bannerUrl.setData(arrayName, arrayImage, arrayUrl)

        bannerDrawable = InjectDrawable(this, 2f, 3L)
        val layoutView = bannerDrawable.setData(arrayName, arrayImageDrawable, arrayUrl)

        val bannerLL = findViewById<LinearLayout>(R.id.bannerLL)
        bannerLL.addView(layoutView)
```


<img src="https://raw.githubusercontent.com/Allanksr/zelloconsumer/gh-pages/test/token.PNG">
