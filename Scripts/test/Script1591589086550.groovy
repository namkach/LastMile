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

def path = 'D:\\Users\\sunitakac\\Desktop\\apk\\lastMile\\last_mile_v.1.0.2d.apk'
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
double alltotalPrice = 0.00

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
	CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
} 

try {
	AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()
	
	String[] productName = [product_name1, product_name2, product_name3]
	Integer[] productQty = [qty1, qty2, qty3]
	Double[] productUnitPrice = [unit_price1, unit_price2, unit_price3]
	
	ArrayList<String> productList = new ArrayList<String>()
	
	KeywordUtil.logInfo('----- new order -----')
		status_id = 4
		List<MobileElement> tabs = driver.findElementsByClassName('android.widget.ImageView')
		for (int i = 0; i < tabs.size(); i++) {
			KeywordUtil.logInfo('Text tab : ' + tabs.get(i).getText())
			if (tabs.get(i).getText().contains('กำลังดำเนินการ')) {
				tabs.get(i).click()
				break
			}
		}
	(status, remark) = CustomKeywords.'myPackage.KW_LastMile.findOrder'(order_id, store_id, payment_type, status_id)
	if (status.equals('Fail')) {
		return CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
	}
	
	
	
	
	
	

} 
catch (Exception e) {
	KeywordUtil.markFailed('Crashed... ' + e)
	status = 'Fail'
	remark = e.toString()
	CustomKeywords.'myPackage.KW_LastMile.stampResult'(order_id, flow_type, payment_type, status, remark)
}
	

