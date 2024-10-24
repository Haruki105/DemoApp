package com.example.demo;
import static org.junit.Assert.assertEquals;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.errorprone.annotations.Var;
import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
public class DemoTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws MalformedURLException {

        BaseOptions options = new BaseOptions(); // Khởi tạo BaseOptions
        options.setCapability("platformName", "Android");
        options.setCapability("appium:platformVersion", "14.0");
        options.setCapability("deviceName", "Pixel 8a API 34");
        options.setCapability("appium:automationName", "UiAutomator2");
        options.setCapability("app", "D:\\Nam3\\KiemChungPhanMem\\demo\\app\\build\\outputs\\apk\\debug\\app-debug.apk");
        options.setCapability("appium:ensureWebviewsHavePages", true);
        options.setCapability("appium:nativeWebScreenshot", true);
        options.setCapability("appium:newCommandTimeout", 3600);
        options.setCapability("appium:connectHardwareKeyboard", true);

        // Khởi tạo AndroidDriver với Appium Server
        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), options);
    }

    //Test1: Không nhập/ nhập thiếu thông tin
    @Test
    public void Test1() {
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_email\"]")).sendKeys("user@gmail.com");
        driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.example.demo:id/button\"]")).click();
        String errorMsg = driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.example.demo:id/tv_notification\"]")).getText();
        assertEquals("Please fill in information!", errorMsg.trim());
    }

    //Test2: Nhập sai định dạng email
    @Test
    public void Test2() {

        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_email\"]")).sendKeys("usergmail.com");
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_password\"]")).sendKeys("123");
        driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.example.demo:id/button\"]")).click();
        String errorMsg = driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.example.demo:id/tv_notification\"]")).getText();
        assertEquals( "Email is invalid!", errorMsg.trim());
    }

    //Test3: Nhập sai email/ password
    @Test
    public void Test3() {
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_email\"]")).sendKeys("user1@gmail.com");
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_password\"]")).sendKeys("123");
        driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.example.demo:id/button\"]")).click();
        String errorMsg = driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.example.demo:id/tv_notification\"]")).getText();
        assertEquals("User doesn't exist.", errorMsg.trim());
    }

    //Test4: Nhập đúng
    @Test
    public void Test4() {
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_email\"]")).sendKeys("user1@gmail.com");
        driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"com.example.demo:id/edit_password\"]")).sendKeys("12345");
        driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"com.example.demo:id/button\"]")).click();
        String errorMsg = driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"com.example.demo:id/tv_notification\"]")).getText();
        assertEquals("Sign in successfully!", errorMsg.trim());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
