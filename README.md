# Persian Date Picker with BottomSheetDialogFragment

![Hero Image](https://raw.githubusercontent.com/MsM512/Persian-Date-Picker/master/screenshot/main.jpg
=270x600)

![Image](https://jitpack.io/v/MsM512/Persian-Date-Picker.svg)

Android Persian Date Picker

This library provides you Persian Date picker with BottomSheetDialogFragment.

## Usages

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```
dependencies {
	        implementation 'com.github.MsM512:Persian-Date-Picker:Tag'
	}
```
Then in your Java Code, you use it like below.

```java
 PersianDatePickerBSH picker = new PersianDatePickerBSH()
        .setPositiveButtonString("تایید")
        .setTodayButtonVisible(true)
        .setInitDate(1403, 1, 1)
        .setActionTextColor(Color.WHITE)
        .setTypeFace(typeface)
        .setTitleType(PersianDatePickerBSH.NO_TITLE)
        .setListener(new PersianPickerListener() {
            @Override
            public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                Log.d(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                Log.d(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                Log.d(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                Toast.makeText(getApplicationContext(), persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDismissed() {

            }
        });

picker.

show(getSupportFragmentManager(), "date");
```

## Public Methods

|                             Name                             |                                       Description                                        |
|:------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|
|                       setMaxYear(int)                        |                             set maximum year can be selected                             |
|                       setMaxMonth(int)                       |                            set maximum month can be selected                             |
|                        setMaxDay(int)                        |                    set maximum day can be selected in the last month                     |
|                       setMinYear(int)                        |                             set minimum year can be selected                             |
|                    setTypeFace(TypeFace)                     |                                   set dialog typeface                                    |
|                 setInitDate(PersianCalendar)                 |                         set date that dialog will launch on that                         |
|             setInitDate(PersianCalendar,boolean)             | set date that dialog will launch on that and force min/max year to be compatible with it |
|               setPositiveButtonString(String)                |                                 set positive button text                                 |
|          setPositiveButtonResource(@StringRes int)           |                        set positive button text from strings.xml                         |
|                  setNegativeButton(String)                   |                                 set negative button text                                 |
|          setNegativeButtonResource(@StringRes int)           |                        set negative button text from strings.xml                         |
|                    setTodayButton(String)                    |                                  set today button text                                   |
|            setTodayButtonResource(@StringRes int)            |                          set today button text from strings.xml                          |
|                setTodayButtonVisible(boolean)                |                            set today button visible/invisible                            |
|              setActionTextColor(@ColorInt int)               |                              set dialog buttons texts color                              |
|          setActionTextColorResource(@ColorRes int)           |                      set dialog buttons texts color form colors.xml                      |
|                    setCancelable(boolean)                    |                               set dialog cancelable or not                               |
|              setBackgroundColor(@ColorInt int)               |                               set dialog background color                                |
|           setPickerBackgroundColor(@ColorInt int)            |                            set date pickers background color                             |
| setTitleType(NO_TITLE/DAY_MONTH_YEAR/WEEKDAY_DAY_MONTH_YEAR) |                           It will handle title show scenarios                            |
|        setPickerBackgroundDrawable(@DrawableRes int)         |              set date pickers background drawable from res/drawable folder               |
|                  setAllButtonsTextSize(int)                  |                               set Action button text size                                |
|                    setListener(Listener)                     |                               set dialog callback listener                               |

## STYLING

If you want to change picker text color or divider color you can set it via an easy style
in your base application Theme, add

```xml

<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>

    <!-- Refrence your picker theme here -->
    <item name="numberPickerTheme">@style/customNumberPicker</item>
</style>

    <!-- Here is your custom date picker theme -->
<style name="customNumberPicker">
<!-- use this for text color -->
<item name="android:textColorPrimary">@android:color/holo_green_dark</item>

<!-- use this for divider color -->
<item name="colorControlNormal">@android:color/holo_purple</item>
</style>
```

## CREDITS

* Special Thanks to [PersianDatePicker](https://github.com/alibehzadian/PersianDatePicker).


