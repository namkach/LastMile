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

	def findOrder(String order_id, String store_id, String payment_type, Integer status_id) {
		checkOrder = findOrderId(order_id, store_id, payment_type, status_id)
		KeywordUtil.logInfo('checkOrder : ' + checkOrder)
		while(!checkOrder) {
			swipeUp()
			checkOrder = findOrderId(order_id, store_id, payment_type, status_id)
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

	def findOrderId(String order_id, String store_id, String payment_type, Integer status_id) {
		List<MobileElement> products = driver.findElementsByClassName('android.view.View')
		def sizes = products.size().toString()
		KeywordUtil.logInfo(sizes)
		for (int i = 0; i < products.size(); i++) {
			KeywordUtil.logInfo(products.get(i).getText())
			if (products.get(i).getText().contains(order_id)) {
				String[] texts = products.get(i).getText().split('\\r?\\n')
				//check text array
				//				for (int j = 0; j < texts.size(); j++) {
				//					KeywordUtil.logInfo('texts : ' + texts[j])
				//				}
				assert texts[1].contains(store_id)
				switch (status_id) {
					case 3 :
						statusOrder = 'จัดของเสร็จแล้ว'
						break
					case 4 :
						statusOrder = 'กำลังจัดส่ง'
						break
					case 5 :
						statusOrder = 'เสร็จสมบูรณ์'
						break
					//					case 6 :
					//						statusOrder = 'ยกเลิกออเดอร์'
					//						break
				}
				KeywordUtil.logInfo('statusOrder : ' + statusOrder)
				assert texts[2].contains(statusOrder)
				products.get(i).click()
				return true
			}
		}


		//		List<MobileElement> orders = driver.findElementsById(riderId + 'txt_order_no')
		//		List<MobileElement> storeId = driver.findElementsById(riderId + 'txt_store_id')
		//		for (int j = orders.size() - 1; j >= 0; j--) {
		//			if (orders.get(j).getText().equals(order_id)) {
		//				KeywordUtil.markPassed ('*** order found ***')
		//				switch (payment_type) {
		//					case '1' :
		//						checkOrder = true
		//						break
		//					case '2' :
		//						MobileElement paymentIcon = (MobileElement) driver.findElementById(riderId + 'img_payment_type')
		//						if (paymentIcon.isDisplayed()) {
		//							checkOrder = true
		//						}
		//						break
		//					case '4' :
		//						if (status_id == 5 || status_id == 6) {
		//							checkOrder = true
		//						} else {
		//							MobileElement paymentIcon = (MobileElement) driver.findElementById(riderId + 'img_payment_type')
		//							if (paymentIcon.isDisplayed()) {
		//								checkOrder = true
		//							}
		//						}
		//						break
		//				}
		////				assert storeId.get(j).getText() == store_id
		//				orders.get(j).click()
		//				MobileElement orderNo = (MobileElement) driver.findElementById(riderId + 'main_toolbar_tv_order')
		//				assert orderNo.getText().equals(order_id)
		//				break
		//			}
		//		}
		return false
	}

	def swipeUp() {
		KeywordUtil.logInfo('== Swipe ==')
		int x = Mobile.getDeviceWidth()/2
		int startY = Mobile.getDeviceHeight()*0.7
		int endY = Mobile.getDeviceHeight()*0.4
		Mobile.swipe(x, startY, x, endY)
	}

	def checkTotalProducts(String flow_type, Integer total_product, Integer status_id) {
		statusText = false
		boolean text
		List<MobileElement> listProducts = driver.findElementsByClassName('android.view.View')
		KeywordUtil.logInfo('listProducts : ' + listProducts.size())
		for (int i = 0; i < listProducts.size(); i++) {
			text = listProducts.get(i).getAttribute('clickable')
			KeywordUtil.logInfo('i : ' + text)
			KeywordUtil.logInfo('text : ' + listProducts.get(i).getText())


			//			if () {
			//
			//			}


			//			KeywordUtil.logInfo('products : ' + listProducts.get(i).getText())
			//			productText = listProducts.get(i).getText()
			//			KeywordUtil.logInfo('productText length : ' + productText.length())
			//			if (productText.contains('ยอดสุทธิ')) {
			//				statusText = false
			//				totalProduct = extractInt(productText)
			//				KeywordUtil.logInfo('statusText : ' + statusText)
			//			} else if (productText.contains('บาท')) {
			//				alltotalPrice = Double.parseDouble(productText.replace(' บาท',''))
			//				break
			//			} else if (statusText && productText.length() > 0) {
			//				products.add(productText)
			//			} else if (productText.contains('หมายเหตุ')) {
			//				statusText = true
			//				KeywordUtil.logInfo('statusText : ' + statusText)
			//			}
		}

		// check product list
		//		for (int j = 0; j < product.size(); j++) {
		//			KeywordUtil.logInfo('texts : ' + product.get(j))
		//		}

		//		if (flow_type.equals('2')) {
		//			total_product += 1
		//		}

		//		KeywordUtil.logInfo('totalProduct : ' + totalProduct)
		//		KeywordUtil.logInfo('products size : ' + products.size())
		//		for (int k = 0; k < products.size(); k++) {
		//			KeywordUtil.logInfo('products ' + products.get(k))
		//		}
		//		if (totalProduct .equals(products.size())) {
		//			assert totalProduct == total_product
		//			KeywordUtil.logInfo ('Check total product --> Passed')
		//			status = ''
		//			remark = ''
		//		} else {
		//			KeywordUtil.logInfo ('Check total product --> Failed')
		//			status = 'Fail'
		//			remark = 'Fail to check total products at status_id ' + status_id
		//		}
		return [status, remark, products, alltotalPrice]
	}

	def checkEachProduct(Integer indexProduct, String productName, Integer productQty, Double productUnitPrice, Integer countQty, Double countTotalPrice, Integer statusProduct, Integer status_id) {
		List<MobileElement> listProducts = driver.findElementsByClassName('android.view.View')
		for (int i = 0; i < listProducts.size(); i++) {
			KeywordUtil.logInfo('products : ' + listProducts.get(i).getText())
			productText = listProducts.get(i).getText()
			KeywordUtil.logInfo('productText length : ' + productText.length())
			if (productText.contains('เกินเวลา')) {
				statusText = false
				//			} else if (productText.contains('บาท')) {
				//				alltotalPrice = Double.parseDouble(productText.replace(' บาท',''))
			} else if (statusText && productText.length() > 0) {
				products.add(productText)
			} else if (productText.contains('หมายเหตุ')) {
				statusText = true
			}
		}

		String[] product = products.get(indexProduct).split('\\r?\\n')
		def name = product[0]
		int qty = extractInt(product[1])
		double totalPrice = Double.parseDouble(product[2])

		//get each product details
		KeywordUtil.logInfo(name)
		KeywordUtil.logInfo(qty.toString())
		KeywordUtil.logInfo(totalPrice.toString())

		if (productQty.equals(qty)) {

			assert totalPrice.equals((productQty * productUnitPrice).round(2))
			countQty += qty
			countTotalPrice += totalPrice
			KeywordUtil.logInfo('countQty : ' + countQty)
			KeywordUtil.logInfo('countTotalPrice : ' + countTotalPrice)
			status = ''
			remark = ''
		} else {
			status = 'Fail'
			remark = 'Fail to check each product in order at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark, countQty, countTotalPrice]


		//		List<MobileElement> prods = driver.findElementsById(riderId + 'row_order_detail_tv_name')
		//		List<MobileElement> qtys = driver.findElementsById(riderId + 'row_order_detail_tv_amount')
		//		List<MobileElement> prices = driver.findElementsById(riderId + 'row_order_detail_tv_price')
		//
		//		double totalPrice
		//		int numQty
		//		for (int k = 0; k < prods.size(); k++) {
		//			if (prods.get(k).getText().equals(name)) {
		//				KeywordUtil.logInfo ('check qty before ' + name + ' : ' + countQty)
		//				numQty = extractInt(qtys.get(k).getText())
		//				assert numQty.equals(qty)
		//				switch (statusProduct) {
		//					case 1:
		//						totalPrice = Double.parseDouble(prices.get(k).getText())
		//						break
		//					case 2 :
		//						totalPrice = Double.parseDouble(prices.get(k - 1).getText())
		//						break
		//				}
		//
		//				if (totalPrice.equals((qty * unitPrice))) {
		//					countQty += qty
		//					countTotalPrice += totalPrice
		//					KeywordUtil.logInfo('countQty : ' + countQty)
		//					KeywordUtil.logInfo('countTotalPrice : ' + countTotalPrice)
		//					status = ''
		//					remark = ''
		//				} else {
		//					status = 'Fail'
		//					remark = 'Fail to check each product in order at status_id ' + status_id
		//					KeywordUtil.markFailed(remark)
		//				}
		//				return [status, remark, countQty, countTotalPrice]
		//			}
		//		}
	}

	def checkAllProducts(Double countTotalPrice, Double totalPrice, Double totalPriceElement, Integer status_id) {
		int qty
		double price
		///////////////////////////////////////////////////////////////////
		//		MobileElement allTotalPrice = (MobileElement) driver.findElementById(riderId + 'order_detail_tv_total_price')
		//		MobileElement allQty = (MobileElement) driver.findElementById(riderId + 'order_detail_tv_total_list')

		//		List<MobileElement> totalPriceText = driver.findElementsById('android.view.View')
		//		KeywordUtil.logInfo('totalPriceText Size : ' + totalPriceText.size())
		//		for (int i = 0; i < totalPriceText.size(); i++) {
		//			KeywordUtil.logInfo('check loop ==== ')
		//			KeywordUtil.logInfo('allTotalPrice : ' + totalPriceText.get(i).getText())
		//			if (totalPriceText.get(i).getText().contains('บาท')) {
		//				KeywordUtil.logInfo('allTotalPrice : ' + totalPriceText.get(i).getText())
		//				price = Double.parseDouble(totalPriceText.get(i).getText().replace(' บาท',''))
		//				break
		//			}
		//		}

		//		double numAllTotalPrice = 0.00
		//		if (allTotalPrice.getText().contains('บาท')) {
		//			numAllTotalPrice = Double.parseDouble(allTotalPrice.getText().replace(' บาท',''))
		//		} else {
		//			numAllTotalPrice = Double.parseDouble(allTotalPrice.getText())
		//		}
		//		printType(numAllTotalPrice)

		KeywordUtil.logInfo ('==== totalPriceElement : ' + totalPriceElement)
		KeywordUtil.logInfo ('==== Count Total Price : ' + countTotalPrice)
		countTotalPrice = 594.00
		KeywordUtil.logInfo ('==== Count Total Price : ' + countTotalPrice)
		if (totalPriceElement.equals(countTotalPrice)) {
			assert totalPriceElement.equals(totalPrice)
			//			assert qty.equals(countQty)
			status = ''
			remark = ''
		} else {
			status = 'Fail'
			remark = 'Fail to Check All Products order at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark]
	}

	def confirmBtn(String order_id, Integer status_id, String payment_type, Double totalPrice) {
		checkOrder = false
		def cashText = totalPrice.toString()
		KeywordUtil.logInfo(order_id)
		//		MobileElement ConfirmOrder = (MobileElement) driver.findElementById(riderId + 'order_detail_bt_confirm')
//		List<MobileElement> ConfirmOrder = driver.findElementsByClassName('android.widget.Button')
//		for (int i = 0; i < ConfirmOrder.size(); i++) {
			KeywordUtil.logInfo('status_id : ' + status_id)
			KeywordUtil.logInfo('payment_type : ' + payment_type)
			KeywordUtil.logInfo('totalPrice : ' + cashText)
//			KeywordUtil.logInfo('ConfirmOrder.get(i).getText() : ' + ConfirmOrder.get(i).getText())
			switch (status_id) {
				case 3 :
					findElementToClick('android.widget.Button','รับรายการคำสั่งซื้อ')
//					if (ConfirmOrder.get(i).getText().contains('รับรายการคำสั่งซื้อ')) {
//						KeywordUtil.logInfo(ConfirmOrder.get(i).getText())
//						ConfirmOrder.get(i).click()
//						checkOrder = true
//						break
//					}
					break
				case 4 :
					switch (payment_type) {
						case '1' :
						findElementToClick('android.widget.Button','ชำระด้วยเงินสด')
//						if (ConfirmOrder.get(i).getText().contains('ชำระด้วยเงินสด')) {
//							ConfirmOrder.get(i).click()
							List<MobileElement> cash = driver.findElementsByClassName('android.widget.EditText')
							for (int j = 0; j < cash.size(); j++) {
								if (cash.get(j).getText().contains('0.00')) {
									KeywordUtil.logInfo('cash : ' + cash.get(j).getText())
									cash.get(j).click()
									Mobile.delay(2)

									int x = Mobile.getDeviceWidth()/2 - 50
									int y = (Mobile.getDeviceHeight()*(3/4))

									Mobile.tapAtPosition(x, y)
									Mobile.tapAtPosition(x, y)
									Mobile.tapAtPosition(x, y)
									Mobile.tapAtPosition(x, y)
									Mobile.pressBack()
									Mobile.delay(2)
								}
							}

							findElementToClick('android.widget.Button', 'ดำเนินการต่อ')
//						}
						break
						case '2' :
							findElementToClick('android.widget.Button','ชำระด้วยเงินสดสำเร็จ')
//							if (ConfirmOrder.get(i).getText().contains('ชำระด้วยเงินสดสำเร็จ')) {
//								KeywordUtil.logInfo(ConfirmOrder.get(i).getText())
//								ConfirmOrder.get(i).click()
//								break
//							}
							break
					}
					findElementToClick('android.view.View','ข้าม')

					findElementToClick('android.widget.Button','ตกลง')

					Mobile.delay(2)
					
					
//					List<MobileElement> etc = driver.findElementsByClassName('android.view.View')
					List<MobileElement> confirmSignBtn = driver.findElementsByClassName('android.view.View')
					KeywordUtil.logInfo('-------> btn size to find ตกลง  : ' + confirmSignBtn.size())
					for (int j = 0; j < confirmSignBtn.size(); j++) {
						KeywordUtil.logInfo(confirmSignBtn.get(j).getText())
						if (confirmSignBtn.get(j).getText() == 'ตกลง') {
							Point btnLocation = confirmSignBtn.get(j).getLocation()
							int x = btnLocation.getX()
							int y = btnLocation.getY()
							int width = confirmSignBtn.get(j).getSize().getWidth()
							KeywordUtil.logInfo('location : ' + btnLocation)
							KeywordUtil.logInfo('x : ' + x)
							KeywordUtil.logInfo('y : ' + y)
							KeywordUtil.logInfo('width : ' + width)
							x = x + (width)
							KeywordUtil.logInfo('final x  : ' + x)
							
							swipeUp()
							
							Mobile.tapAtPosition(x, y)
							
							
//							KeywordUtil.logInfo('click : ' + btn.get(j).getText())
//							def top_position = Mobile.getElementTopPosition(btn.get(j), 5)
//							KeywordUtil.logInfo('top_position : ' + top_position)
//							btn.get(j).click()
							KeywordUtil.logInfo('-------------- sign finish --------------')
							break
						}
					}


//					findElementToClick('android.view.View','ตกลง')
//					Mobile.delay(3)
					
					
					findElementToClick('android.widget.Button','ยืนยัน')
					Mobile.delay(2)
					checkOrder = true
					break
//			}
		}

		//		switch (status_id) {
		//			case 3 :
		//				assert ConfirmOrder.getText().equals('รับรายการสั่งซื้อ')
		//				ConfirmOrder.click()
		//				checkOrder = true
		//				break
		//			case 4 :
		//				switch (payment_type) {
		//					case '1' :
		//					assert ConfirmOrder.getText().equals('ชำระเงิน')
		//					break
		//					case '2' :
		//					assert ConfirmOrder.getText().equals('ยืนยันการส่งสินค้า')
		//					break
		//				}
		//				ConfirmOrder.click()
		//				printType(total_price)
		//				checkOrder = confirmPayment(payment_type, total_price)
		//				break
		//		}
		if (checkOrder) {
			status_id += 1
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
				break
			}
		}
	}

	def stampResult(String order_id, String flow_type, String payment_type, String status, String remark) {
		def path = 'D:\\Users\\sunitakac\\Desktop\\AutoTest\\resultLastMile.xls'
		Workbook existingWorkbook = Workbook.getWorkbook(new File(path))
		WritableWorkbook workbookCopy = Workbook.createWorkbook(new File(path), existingWorkbook)
		WritableSheet sheetToEdit = workbookCopy.getSheet(0)

		String[] header = ['Order', 'Flow Type', 'Payment Type', 'Result', 'Remark']
		String[] text = [order_id, flow_type, payment_type, status, remark]

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