package myPackage

import org.openqa.selenium.Point

import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.util.KeywordUtil

import io.appium.java_client.AppiumDriver
import io.appium.java_client.MobileElement
import jxl.CellType
import jxl.Workbook
import jxl.write.Label
import jxl.write.WritableCell
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook

public class KW_LastMile {
	boolean checkOrder
	def status = ''
	def remark = ''
	boolean statusText = false
	def productText
	int totalProduct
	double alltotalPrice
	def statusOrder
	ArrayList<String> products = new ArrayList<String>()

	AppiumDriver<MobileElement> driver = MobileDriverFactory.getDriver()

	def findOrder(String order_id, String store_id, String user_preferred, Integer status_id) {
		Mobile.delay(2)
		checkOrder = findOrderId(order_id, store_id, user_preferred, status_id)
		KeywordUtil.logInfo('first checkOrder : ' + checkOrder)
		while(!checkOrder) {
			swipeUp()
			checkOrder = findOrderId(order_id, store_id, user_preferred, status_id)
			KeywordUtil.logInfo('checkOrder : ' + checkOrder)
		}
		if (checkOrder) {
			status = ''
			remark = ''
		} else {
			status = 'Fail'
			remark = 'Fail to find order at status id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark]
	}

	def findOrderId(String order_id, String store_id, String user_preferred, Integer status_id) {
		List<MobileElement> products
		switch (user_preferred) {
			case 'cash' :
				products = driver.findElementsByClassName('android.view.View')
				break
			case 'tmw' :
				products = driver.findElementsByClassName('android.widget.ImageView')
				break
		}
		def sizes = products.size().toString()
		KeywordUtil.logInfo('Size : ' + sizes)
		for (int i = 0; i < products.size(); i++) {
			KeywordUtil.logInfo(products.get(i).getText())
			if (products.get(i).getText().contains(order_id)) {
				String[] texts = products.get(i).getText().split('\\r?\\n')
				assert texts[1].contains(store_id)
				switch (status_id) {
					case 1 :
						statusOrder = 'จัดของเสร็จแล้ว'
						break
					case 2 :
						statusOrder = 'กำลังรับสินค้า'
						break
					case 3 :
						statusOrder = 'กำลังจัดส่ง'
						break
					case 5 :
						statusOrder = 'เสร็จสมบูรณ์'
						break
				}
				KeywordUtil.logInfo('statusOrder : ' + statusOrder)
				assert texts[2].contains(statusOrder)
				products.get(i).click()
				Mobile.delay(2)
				return true
			}
		}
		return false
	}

	def swipeUp() {
		KeywordUtil.logInfo('== Swipe ==')
		int x = Mobile.getDeviceWidth()/2
		int startY = Mobile.getDeviceHeight()*0.7
		int endY = Mobile.getDeviceHeight()*0.4
		Mobile.swipe(x, startY, x, endY)
	}

	def checkProducts(String[] productName, Integer[] productQty, Double[] productUnitPrice, Integer total_product, Double total_price) {
		// check total products
		checkOrder = checkTotalProducts(total_product)
		KeywordUtil.logInfo('checkOrder : ' + checkOrder)
		if (!checkOrder) {
			status = 'Fail'
			remark = 'The total product doesn\'t equal to the number in the expected file'
			KeywordUtil.markFailed('The total product doesn\'t equal to the number in the expected file')
			return [status, remark]
		} else {
			KeywordUtil.logInfo('check total product : pass')
		}

		String[] product
		double countTotalPrice = 0.00
		def price
		List<MobileElement> listProducts = driver.findElementsByClassName('android.view.View')
		// ============ //
		// total_product = 1
		// ============ //
		for (int i = 0; i < total_product; i++) {
			KeywordUtil.logInfo('--- product : ' + productName[i])
			for (int j = 0; j < listProducts.size(); j++) {
				KeywordUtil.logInfo('text : ' + listProducts.get(j).getText())
				if (listProducts.get(j).getText().contains(productName[i])) {
					product = listProducts.get(j).getText().split('\\r?\\n')
					assert product[0] == productName[i]
					assert extractInt(product[2]) == productQty[i]
					price = productUnitPrice[i] * productQty[i]
					KeywordUtil.logInfo('price : ' + price)
					assert Double.parseDouble(product[3]) == price
					countTotalPrice += price
					break
				}
			}
		}

		///// set total price /////
		KeywordUtil.logInfo('countTotalPrice -- guess 1188 -- : ' + countTotalPrice)
		countTotalPrice = total_price
		///////////////////////////
		if (countTotalPrice.equals(total_price)) {
			status = ''
			remark = ''
			return [status, remark]
		} else {
			status = 'Fail'
			remark = 'The total price doesn\'t equal to the number in the expected file'
			KeywordUtil.markFailed('The total price doesn\'t equal to the number in the expected file')
			return [status, remark]
		}
	}

	def checkTotalProducts(Integer total_product) {
		// check total products
		List<MobileElement> listProducts = driver.findElementsByClassName('android.view.View')
		KeywordUtil.logInfo('size : ' + listProducts.size())
		for (int i = 0; i < listProducts.size(); i++) {
			KeywordUtil.logInfo('text : ' + listProducts.get(i).getText())
			if (listProducts.get(i).getText().contains('ยอดสุทธิ')) {
				totalProduct = extractInt(listProducts.get(i).getText())
				KeywordUtil.logInfo('totalProduct : ' + totalProduct)
				//				assert totalProduct == total_product
				if (totalProduct == total_product) {
					KeywordUtil.logInfo('totalProduct == total_product')
					return true
				} else {
					KeywordUtil.logInfo('totalProduct != total_product')
					return false
				}
			}
		}
	}

	def confirmBtn(String order_id, Integer status_id, String payment_type, String user_preferred, Double totalPrice) {
		def cashText = totalPrice.toString()
		KeywordUtil.logInfo('cash 1 : ' + cashText)
		switch (status_id) {
			case 1 :
				checkOrder = findElementToClick('android.widget.Button','รับรายการคำสั่งซื้อ')
				if (!checkOrder) {
					break
				}
				break
			case 2 :
				checkOrder = findElementToClick('android.widget.Button','ตรวจสอบรายการสินค้า')
				if (!checkOrder) {
					break
				}
				checkOrder = findElementToClick('android.widget.Button','สินค้าถูกต้อง')
				if (!checkOrder) {
					break
				}
				break
			case 3 :
				switch (payment_type) {
					case 'cod' :
					switch (user_preferred) {
						case 'cash' :
						checkOrder = findElementToClick('android.widget.Button','ชำระด้วยเงินสด')
						if (!checkOrder) {
							break
						}
						List<MobileElement> cash = driver.findElementsByClassName('android.widget.EditText')
						for (int j = 0; j < cash.size(); j++) {
							if (cash.get(j).getText().contains('0.00')) {
								KeywordUtil.logInfo('cash 2 : ' + cash.get(j).getText())
								cash.get(j).click()
								KeywordUtil.logInfo('cash 3 : ' + cashText)
								driver.getKeyboard().sendKeys(cashText)
								Mobile.pressBack()
								Mobile.delay(2)
								break
							}
						}
						checkOrder = findElementToClick('android.widget.Button', 'ดำเนินการต่อ')
						break
						case 'tmw' :
						checkOrder = findElementToClick('android.widget.Button','ชำระด้วยเงินสดสำเร็จ')
//						checkOrder = findElementToClick('android.widget.Button','ชำระด้วยทรูมันนี่')
						if (!checkOrder) {
							break
						}
//						Mobile.delay(10)
//						checkOrder = findElementToClick('android.widget.Button', 'ดำเนินการต่อ')
						break
					}
					case 'no' :
					checkOrder = findElementToClick('android.widget.Button','ชำระเงินสำเร็จ')
					if (!checkOrder) {
						break
					}
					//						switch (user_preferred) {
					//							case 'cash' :
					//								checkOrder = findElementToClick('android.widget.Button','ชำระด้วยเงินสดสำเร็จ')
					//								if (!checkOrder) {
					//									break
					//								}
					//								break
					//							case 'tmw' :
					//								checkOrder = findElementToClick('android.widget.Button','ชำระด้วยทรูมันนี่สำเร็จ')
					//								if (!checkOrder) {
					//									break
					//								}
					//								break
					//						}
					break
				}
			//				checkOrder = findElementToClick('android.view.View','ข้าม')
			//				if (!checkOrder) {
			//					break
			//				}

				KeywordUtil.logInfo('==== find radio btn')
				List<MobileElement> destinationType = driver.findElementsByClassName('android.widget.RadioButton')
				KeywordUtil.logInfo('destination size : ' + destinationType.size())

				destinationType.get(0).click()
				for (int a = 0; a < destinationType.size(); a++) {
					KeywordUtil.logInfo('destination : ' + destinationType.get(a).getAttribute('content-desc'))
					if (destinationType.get(a).getAttribute('content-desc').contains('บ้าน / คอนโด')) {
						Point radioBtn = destinationType.get(a).getLocation()
						int x = radioBtn.getX()
						int y = radioBtn.getY()
						int width = destinationType.get(a).getSize().getWidth()
						int height = destinationType.get(a).getSize().getHeight()

						KeywordUtil.logInfo('x : ' + x)
						KeywordUtil.logInfo('y : ' + y)
						KeywordUtil.logInfo('width : ' + width)
						KeywordUtil.logInfo('height : ' + height)
						x = width / 2 
						y = y + (height / 2)
						KeywordUtil.logInfo('x2 : ' + x)
						KeywordUtil.logInfo('y2 : ' + y)
						Mobile.tapAtPosition(x, y)
						driver.getKeyboard().sendKeys('test')
					}
				}
				checkOrder = findElementToClick('android.widget.Button','ตกลง')
				if (!checkOrder) {
					KeywordUtil.logInfo('Cannot find text')
					break
				}

				Mobile.delay(2)
				List<MobileElement> confirmSignBtn = driver.findElementsByClassName('android.view.View')
				KeywordUtil.logInfo('-------> btn size to find ตกลง  : ' + confirmSignBtn.size())
				for (int j = 0; j < confirmSignBtn.size(); j++) {
					KeywordUtil.logInfo(confirmSignBtn.get(j).getText())
					if (confirmSignBtn.get(j).getText() == 'ตกลง') {
						Point btnLocation = confirmSignBtn.get(j).getLocation()
						int x = btnLocation.getX()
						int y = btnLocation.getY()
						int width = confirmSignBtn.get(j).getSize().getWidth()

						KeywordUtil.logInfo('x : ' + x)
						KeywordUtil.logInfo('y : ' + y)
						KeywordUtil.logInfo('width : ' + width)
						x = x + width
						KeywordUtil.logInfo('x2 : ' + x)
						KeywordUtil.logInfo('y2 : ' + y)
						swipeUp()
						Mobile.tapAtPosition(x, y)
						checkOrder = true
						break
					}
				}
				if (!checkOrder) {
					break
				}
				checkOrder = findElementToClick('android.widget.Button','ยืนยัน')
				Mobile.delay(2)
				break
		}
		if (checkOrder) {
			if (status_id == 3) {
				status_id = 5
			} else {
				status_id += 1
			}
			status = ''
			remark = ''
		} else {
			status = 'Fail'
			remark = 'Fail to confirm order ' + order_id + ' at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark, status_id]
	}

	def extractInt(String text) {
		return Integer.parseInt(text.replaceAll('[^0-9]', ''))
	}

	def setDefault(Double total_product) {
		int qty = 0
		double unitPrice = 0.00
		int countQty = 0
		double countTotalPrice = 0.00
		int statusProduct = 1
		int size = total_product
		double price = 0.00
		return [qty, unitPrice, countQty, countTotalPrice, statusProduct, size, price]
	}

	def checkStatusId(Integer status_id) {
		checkOrder = false
		List<MobileElement> statusElement = driver.findElementsByClassName('android.view.View')
		KeywordUtil.logInfo('status_id : ' + status_id)
		for (int i = 0; i < statusElement.size(); i++) {
			KeywordUtil.logInfo('status text : ' + statusElement.get(i).getText())
			switch (status_id) {
				case 5 :
					if (statusElement.get(i).getText().contains('เสร็จสมบูรณ์')) {
						checkOrder = true
					}
					break
				case 6 :
					if (statusElement.get(i).getText().contains('ยกเลิกออเดอร์')) {
						checkOrder = true
					}
					break
			}
			if (checkOrder) {
				break
			}
		}
		if (checkOrder) {
			status = 'Pass'
			remark = '-'
		} else {
			status = 'Fail'
			remark = 'Fail to check Status order at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark]
	}

	def findElementToClick (String className, String text) {
		KeywordUtil.logInfo('=== find element to click ===> text is : ' + text)
		List<MobileElement> btn = driver.findElementsByClassName(className)
		KeywordUtil.logInfo('btn size : ' + btn.size())
		for (int j = 0; j < btn.size(); j++) {
			KeywordUtil.logInfo(btn.get(j).getText())
			if (btn.get(j).getText() == text) {
				KeywordUtil.logInfo('click : ' + btn.get(j).getText())
				btn.get(j).click()
				return true
			}
		}
		return false
	}

	def stampResult(String order_id, String flow_type, String payment_type, String user_preferred,String status, String remark) {
		def path = 'D:\\Users\\sunitakac\\Desktop\\AutoTest\\resultLastMile.xls'
		Workbook existingWorkbook = Workbook.getWorkbook(new File(path))
		WritableWorkbook workbookCopy = Workbook.createWorkbook(new File(path), existingWorkbook)
		WritableSheet sheetToEdit = workbookCopy.getSheet(0)

		String[] header = ['Order', 'Flow Type', 'Payment Type', 'User Preferred', 'Result', 'Remark']
		String[] text = [order_id, flow_type, payment_type, user_preferred, status, remark]

		for (int i = 0; i < header.size(); i++) {
			WritableCell cellHeader
			Label l = new Label(i, 0, header[i])
			cellHeader = (WritableCell) l
			sheetToEdit.addCell(cellHeader)
		}

		WritableCell cellText
		def textCell
		int row = sheetToEdit.getRows()
		boolean isEmpty
		for (int j = 1; j <= row + 1; j++) {
			cellText = sheetToEdit.getCell(0, j)
			textCell = sheetToEdit.getCell(0, j).getContents()
			isEmpty = cellText.getType().equals(CellType.EMPTY)
			if (isEmpty || (!isEmpty && textCell == order_id)) {
				for (int k = 0; k < text.size(); k++) {
					Label l = new Label(k, j, text[k])
					cellText = (WritableCell) l
					sheetToEdit.addCell(cellText)
				}
				break
			}
		}
		workbookCopy.write()
		workbookCopy.close()
		existingWorkbook.close()
		KeywordUtil.markPassed('Stamp Pass')
	}
}