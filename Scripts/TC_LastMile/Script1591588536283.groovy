import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory as MobileDriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import io.appium.java_client.AppiumDriver as AppiumDriver
import io.appium.java_client.MobileElement as MobileElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

def path = 'D:\\Users\\sunitakac\\Desktop\\apk\\last_mile_v_0.0.6.apk'
def status = ''
def remark = '-'

int status_id = 3
int qty = 0
double unitPrice = 0.00
int countQty = 0
double countTotalPrice = 0.00
int statusProduct = 1
int size = total_product
double price = 0.00

try {
    Mobile.startApplication(path, true)

    AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()
    Mobile.delay(8)

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
	//	CustomKeywords.'myPac.StaffKeywords_COD.writeStaff'(order_id, flow_type, delivery_type, payment_type, status, remark)
} 

try {
	AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()
	
	String[] productName = [product_name1, product_name2, product_name3]
	Integer[] productQty = [qty1, qty2, qty3]
	Double[] productUnitPrice = [unit_price1, unit_price2, unit_price3]
	
	KeywordUtil.logInfo('----- new order -----')
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
//	}
	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkTotalProducts'(flow_type, size, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
//	}
//	
//	List<MobileElement> products = driver.findElementsById(riderId + 'row_order_detail_tv_name')
//	for(int i = 0; i < products.size(); i++) {
//		for (int j = 0; j < productName.size(); j++) {
//			if ((products.get(i).getText() == edit_product) && (products.get(i).getText() == productName[j])) {
//				switch (flow_type) {
//					case '1' :
//						qty = productQty[j] + edit_qty
//						(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(edit_product, qty, productUnitPrice[j], countQty, countTotalPrice, statusProduct, status_id)
//						if (status.equals('Fail')) {
//							return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
//						}
//						break
//					case '3':
//						KeywordUtil.logInfo ('skip deleted product : ' + edit_product)
//						statusProduct = 2
//						break
//				}
//			} else if (products.get(i).getText() == productName[j]) {
//				(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(productName[j], productQty[j], productUnitPrice[j], countQty, countTotalPrice, statusProduct, status_id)
//				if (status.equals('Fail')) {
//					return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
//				}
//			}
//		}
//	}
//	if (flow_type == '2') {
//		qty = edit_qty
//		unitPrice = edit_unit_price
//		(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(edit_product, qty, unitPrice, countQty, countTotalPrice, statusProduct, status_id)
//		if (status.equals('Fail')) {
//			return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
//		}
//	}
//	
//	switch (flow_type) {
//		case '0' :
//			price = total_price
//			break
//		case '1'..'3' :
//			price = edit_total_price
//			break
//	}
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkAllProducts'(countTotalPrice, price, countQty, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
	
//	(status, remark, status_id) = CustomKeywords.'myPackage.KW_LastMile.confirmBtn'(order_id, status_id, payment_type, price)
//	KeywordUtil.logInfo('status_id : ' + status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
	
	
	status_id = 4
	
	KeywordUtil.logInfo('----- Processing -----')
	List<MobileElement> tabs = driver.findElementsByClassName('android.widget.ImageView')
	for (int i = 0; i <= tabs.size(); i++) {
		if (tabs.get(i).getText().contains('กำลังดำเนินการ')) {
			tabs.get(i).click()
			break
		}
	}
	
	
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
	}

	(qty, unitPrice, countQty, countTotalPrice, statusProduct, size, price) = CustomKeywords.'myPackage.KW_LastMile.setDefault'(total_product)
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkTotalProducts'(flow_type, size, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
//	
//	products = driver.findElementsById(riderId + 'row_order_detail_tv_name')
//	for(int i = 0; i < products.size(); i++) {
//		for (int j = 0; j < productName.size(); j++) {
//			if ((products.get(i).getText() == edit_product) && (products.get(i).getText() == productName[j])) {
//				switch (flow_type) {
//					case '1' :
//						qty = productQty[j] + edit_qty
//						(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(edit_product, qty, productUnitPrice[j], countQty, countTotalPrice, statusProduct, status_id)
//						if (status.equals('Fail')) {
//							return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//						}
//						break
//					case '3':
//						KeywordUtil.logInfo ('skip deleted product : ' + edit_product)
//						statusProduct = 2
//						break
//				}
//			} else if (products.get(i).getText() == productName[j]) {
//				(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(productName[j], productQty[j], productUnitPrice[j], countQty, countTotalPrice, statusProduct, status_id)
//				if (status.equals('Fail')) {
//					return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//				}
//			}
//		}
//	}
//	if (flow_type == '2') {
//		qty = edit_qty
//		unitPrice = edit_unit_price
//		(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(edit_product, qty, unitPrice, countQty, countTotalPrice, statusProduct, status_id)
//		if (status.equals('Fail')) {
//			return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//		}
//	}
//	println ('countQty : ' + countQty)
//	println ('countTotalPrice : ' + countTotalPrice)
//	
//	switch (flow_type) {
//		case '0' :
//			price = total_price
//			break
//		case '1'..'3' :
//			price = edit_total_price
//			break
//	}
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkAllProducts'(countTotalPrice, price, countQty, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
	
	(status, remark, status_id) = CustomKeywords.'myPackage.KW_LastMile.confirmBtn'(order_id, status_id, payment_type, price)
	KeywordUtil.logInfo('status_id : ' + status_id)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
	}
	
	Mobile.delay(3)
	
//	KeywordUtil.logInfo('----- Processed -----')
//	List<MobileElement> tabs2 = driver.findElementsByClassName('android.widget.ImageView')
//	for (int i = 0; i <= tabs.size(); i++) {
//		if (tabs.get(i).getText().contains('ดำเนินการแล้ว')) {
//			tabs.get(i).click()
//			break
//		}
//	}
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
//	
//	(qty, unitPrice, countQty, countTotalPrice, statusProduct, size, price) = CustomKeywords.'myPackage.KW_LastMile.setDefault'(total_product)
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkTotalProducts'(flow_type, size, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
//	
//	products = driver.findElementsById(riderId + 'row_order_detail_tv_name')
//	for(int i = 0; i < products.size(); i++) {
//		for (int j = 0; j < productName.size(); j++) {
//			if ((products.get(i).getText() == edit_product) && (products.get(i).getText() == productName[j])) {
//				switch (flow_type) {
//					case '1' :
//						qty = productQty[j] + edit_qty
//						(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(edit_product, qty, productUnitPrice[j], countQty, countTotalPrice, statusProduct, status_id)
//						if (status.equals('Fail')) {
//							return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//						}
//						break
//					case '3':
//						KeywordUtil.logInfo ('skip deleted product : ' + edit_product)
//						statusProduct = 2
//						break
//				}
//			} else if (products.get(i).getText() == productName[j]) {
//				(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(productName[j], productQty[j], productUnitPrice[j], countQty, countTotalPrice, statusProduct, status_id)
//				if (status.equals('Fail')) {
//					return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//				}
//			}
//		}
//	}
//	
//	if (flow_type == '2') {
//		qty = edit_qty
//		unitPrice = edit_unit_price
//		(status, remark, countQty, countTotalPrice) = CustomKeywords.'myPackage.KW_LastMile.checkEachProduct'(edit_product, qty, unitPrice, countQty, countTotalPrice, statusProduct, status_id)
//		if (status.equals('Fail')) {
//			return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//		}
//	}
//
//	println ('countQty : ' + countQty)
//	println ('countTotalPrice : ' + countTotalPrice)
//	
//	switch (flow_type) {
//		case '0' :
//			price = total_price
//			break
//		case '1'..'3' :
//			price = edit_total_price
//			break
//	}
//
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkAllProducts'(countTotalPrice, price, countQty, status_id)
//	if (status.equals('Fail')) {
//		return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	}
//	
//	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.checkStatusId'(status_id)
//	KeywordUtil.logInfo('checkOrder : ' + checkOrder)
//	return CustomKeywords.'myPackage.KW_LastMile.writeRider'(order_id, flow_type, payment_type, status, remark)
//	
	
}
catch (Exception e) {
	KeywordUtil.markFailed('Crashed... ' + e)
	status = 'Fail'
	remark = e.toString()
		CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
}

//Mobile.closeApplication()

