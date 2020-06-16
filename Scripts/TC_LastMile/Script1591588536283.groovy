import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

import io.appium.java_client.AppiumDriver as AppiumDriver
import io.appium.java_client.MobileElement as MobileElement

def path = 'D:\\Users\\sunitakac\\Desktop\\apk\\lastMile\\app-release.apk'
def status = ''
def remark = '-'

def name
int status_id = 3
int qty = 0
double unitPrice = 0.00
int countQty = 0
double countTotalPrice = 0.00
int statusProduct = 1
int size = total_product
double totalPrice = 0.00
double alltotalPrice = 0.00ฃ
boolean checkElement

try {
    Mobile.startApplication(path, true)

    AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()
    Mobile.delay(1)

    MobileElement cpBtn = driver.findElementByClassName('android.widget.Button')
    cpBtn.click()
	
	Mobile.setText(findTestObject('LastMile/Username'), 'sunitakac@gosoft.co.th', 0)
	Mobile.setText(findTestObject('LastMile/Password'), 'nazonazo504', 0)
	Mobile.tap(findTestObject('LastMile/LogonBtn'), 0)
	Mobile.delay(2)
}
catch (Exception e) {
    KeywordUtil.markFailed('Crashed... ' + e)
    status = 'Fail'
    remark = 'Cannot start application'
	CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
} 

try {
	AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()
	
	String[] productName = [product_name1, product_name2, product_name3]
	Integer[] productQty = [qty1, qty2, qty3]
	Double[] productUnitPrice = [unit_price1, unit_price2, unit_price3]
	
	ArrayList<String> productList = new ArrayList<String>()
	
//	KeywordUtil.logInfo('----- new order -----')
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
//	}
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkProducts'(productName, productQty, productUnitPrice, size, total_price)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
//	}
//	
//	(status, remark, status_id) = CustomKeywords.'myPackage.KW_LastMile.confirmBtn'(order_id, status_id, payment_type, user_preferred, totalPrice)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
//	}
	
	KeywordUtil.logInfo('----- Processing -----')
	status_id = 4
	List<MobileElement> tabs = driver.findElementsByClassName('android.widget.ImageView')
	checkElement = false
	for (int i = 0; i < tabs.size(); i++) {
		KeywordUtil.logInfo('Text tab : ' + tabs.get(i).getText())
		if (tabs.get(i).getText().contains('กำลังดำเนินการ')) {
			tabs.get(i).click()
			checkElement = true
			break
		}
	}
	if (!checkElement) {
		status = 'Fail'
		remark = 'Fail to press processing tab'
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}
	
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}
	
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkProducts'(productName, productQty, productUnitPrice, size, total_price)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}

	(status, remark, status_id) = CustomKeywords.'myPackage.KW_LastMile.confirmBtn'(order_id, status_id, payment_type, user_preferred, totalPrice)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}
	
	KeywordUtil.logInfo('----- Processed -----')
//	status_id = 5
	List<MobileElement> tab = driver.findElementsByClassName('android.widget.ImageView')
	checkElement = false
	for (int i = 0; i < tab.size(); i++) {
		if (tab.get(i).getText().contains('ดำเนินการแล้ว')) {
			tab.get(i).click()
			checkElement = true
			break
		}
	}
	if (!checkElement) {
		status = 'Fail'
		remark = 'Fail to press processed tab'
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}
	
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}
	
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkProducts'(productName, productQty, productUnitPrice, size, total_price)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
	}
	
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkStatusId'(status_id)
	return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
}
catch (Exception e) {
	KeywordUtil.markFailed('Crashed... ' + e)
	status = 'Fail'
	remark = e.toString()
	CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, user_preferred, status, remark)
}

//Mobile.closeApplication()

