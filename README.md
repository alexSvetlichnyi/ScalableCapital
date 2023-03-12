# ScalableCapital

**Steps to install:**<br />
1) Unpack application<br />
2) Import project in Android Studio<br />
3) Please ensure that you have latest Android studio & build tools and emulator<br />
4) Run application and enjoy:).<br />

**Third party libraries:**<br />
1) Hilt - DI framework <br />
2) Retrofit 2 - used for Network requests <br />
3) RXJava2 - concurrency framework <br />

**Working with the application:**<br />
At the begin of application list of repositories for the hardcoded user will be downloaded and displayed.
In case of error you will be prompted with SnackBar message. You can select specific repo to check number of commits per month using custom view.

**Important:**<br />
I have added two ways to work with thread: coroutines + RxJava to use RXJava please uncomment next lines: <br />
RepoDetailFragment line 43 uncomment, line 41 comment <br />
ReposListViewModel line 45 uncomment, line 43 comment <br />

