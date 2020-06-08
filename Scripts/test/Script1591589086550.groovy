import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement

Mobile.startApplication('D:\\Users\\sunitakac\\Desktop\\apk\\last_mile_v_0.0.6.apk', true)

AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()
//Mobile.tap(findTestObject('null'), 0)
MobileElement cpBtn = driver.findElementByClassName('android.widget.Button')
cpBtn.click()

Mobile.closeApplication()

