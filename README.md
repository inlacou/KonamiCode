# KonamiCode

[![](https://jitpack.io/v/inlacou/KonamiCode.svg)](https://jitpack.io/#inlacou/KonamiCode)

---
Forked from [here](https://github.com/thiagokimo/KonamiCode?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2202)

## Inculde in your project
### Gradle

Add the JitPack repository to your build file:

``` groovy
repositories {
    maven {
	    url "https://jitpack.io"
	}
}
```

Add the dependency in the form:

```groovy
dependencies {
    compile 'com.github.inlacou:KonamiCode:1.2.0'
}
```

### Maven

If you use Maven, add this into your build file:

``` xml
<repository>
    <id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```

And them this into your dependencies
``` xml
<dependency>
    <groupId>com.github.thiagokimo</groupId>
    <artifactId>KonamiCode</artifactId>
	<version>1.2.0</version>
</dependency>
```
## How to use

Add the following code in your Activity:

``` java
new KonamiCode.Installer(context)
        .on(activity-or-fragment-or-view)
        .install();
```

The library injects a [KonamiCodeLayout](https://github.com/thiagokimo/KonamiCode/blob/master/library/src/main/java/io/kimo/konamicode/KonamiCodeLayout.java) as the first child of your Activity's view. It listens your swipe events without harming
 the touch events of the original view.

For the first part of the Konami code, swipe into the correct directions. If you do it correctly,
an AlertDialog with the buttons **A**, **B** and **START** will appear. Pressing them correctly will
trigger the final callback.

By default a callback with a Toast message will appear. You can customize the final callback by yourself:

``` kt
KonamiCodeInstaller(this)
	.on(this)
	.install(KonamiKodeView(this))
```

More complex (in this example, change swipes and disable buttons):

``` kt
val kkv = KonamiKodeView(this)

kkv.model = KonamiKodeViewMdl(
	directionsOrder = listOf(Direction.UP, Direction.DOWN, Direction.UP, Direction.DOWN),
	buttonsOrder = listOf(),
	onFinish = {
		Toast.makeText(this, "Correct code", Toast.LENGTH_SHORT).show()
	})

KonamiCodeInstaller(this)
	.on(this)
	.install(kkv)
```

You can change the model (and the combination whithin it) when you want!

### Attention
Make sure you add this **AFTER** your view is set, otherwise it won't listen to your swipes. KonamiCode adds a swipe listener into the root view of your Activity.

## License

    Copyright 2011, 2012 Thiago Rocha

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
