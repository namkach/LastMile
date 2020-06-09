package myPackage

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
			remark = 'Fail to find order ' + order_id + ' at status_id ' + status_id
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
				String[] texts = products.get(i).getText().trim().split('\\r?\\n')
				//check text array
//				for (int j = 0; j < texts.size(); j++) {
//					KeywordUtil.logInfo('texts : ' + texts[j])
//				}
				assert texts[1].contains(store_id)
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
		int x = Mobile.getDeviceWidth()/2
		int startY = Mobile.getDeviceHeight()*0.7
		int endY = Mobile.getDeviceHeight()*0.4
		Mobile.swipe(x, startY, x, endY)
	}

	def checkTotalProducts(String flow_type, Integer total_product, Integer status_id) {
		///////////////////////////////////////////////////////////
		List<MobileElement> prods = driver.findElementsById(riderId + 'row_order_detail_tv_name')
		println ('Product size : ' + prods.size())
		println ('Total product : ' + total_product)
		if (flow_type.equals('2')) {
			total_product += 1
		}

		//		KeywordUtil.logInfo (printType(prods.size()))
		//		KeywordUtil.logInfo (printType(total_product))

		if (prods.size().equals(total_product)) {
			KeywordUtil.logInfo ('true')
			status = ''
			remark = ''
		} else {
			KeywordUtil.logInfo ('false')
			status = 'Fail'
			remark = 'Fail to check total products at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark]
	}

	def checkEachProduct(String name, Integer qty, Double unitPrice, Integer countQty, Double countTotalPrice, Integer statusProduct, Integer status_id) {
		List<MobileElement> prods = driver.findElementsById(riderId + 'row_order_detail_tv_name')
		List<MobileElement> qtys = driver.findElementsById(riderId + 'row_order_detail_tv_amount')
		List<MobileElement> prices = driver.findElementsById(riderId + 'row_order_detail_tv_price')

		double totalPrice
		int numQty
		for (int k = 0; k < prods.size(); k++) {
			if (prods.get(k).getText().equals(name)) {
				KeywordUtil.logInfo ('check qty before ' + name + ' : ' + countQty)
				numQty = extractInt(qtys.get(k).getText())
				assert numQty.equals(qty)
				switch (statusProduct) {
					case 1:
						totalPrice = Double.parseDouble(prices.get(k).getText())
						break
					case 2 :
						totalPrice = Double.parseDouble(prices.get(k - 1).getText())
						break
				}

				if (totalPrice.equals((qty * unitPrice))) {
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
			}
		}
	}

	def checkAllProducts(Double countTotalPrice, Double totalPrice, Integer countQty, Integer status_id) {
		int qty
		double price
		///////////////////////////////////////////////////////////////////
		//		MobileElement allTotalPrice = (MobileElement) driver.findElementById(riderId + 'order_detail_tv_total_price')
		//		MobileElement allQty = (MobileElement) driver.findElementById(riderId + 'order_detail_tv_total_list')

		List<MobileElement> allTotalPrice = driver.findElementsById('android.view.View')
		for (int i = 0; i < allTotalPrice.size(); i++) {
			if (allTotalPrice.get(i).getText().contains('บาท')) {
				KeywordUtil.logInfo(allTotalPrice.get(i).getText())
				price = Double.parseDouble(allTotalPrice.get(i).getText().replace(' บาท',''))
			}
		}

		List<MobileElement> productQty = driver.findElementsById('android.view.View')
		for (int j = 0; j < productQty.size(); j++) {
			if (productQty.get(j).getText().contains('ยอดสุทธิ')) {
				KeywordUtil.logInfo(productQty.get(j).getText())
				qty = extractInt(productQty.get(j).getText())
			}
		}

		//		double numAllTotalPrice = 0.00
		//		if (allTotalPrice.getText().contains('บาท')) {
		//			numAllTotalPrice = Double.parseDouble(allTotalPrice.getText().replace(' บาท',''))
		//		} else {
		//			numAllTotalPrice = Double.parseDouble(allTotalPrice.getText())
		//		}
		//		printType(numAllTotalPrice)
		KeywordUtil.logInfo ('total price : ' + price)
		KeywordUtil.logInfo ('All QTY : ' + qty)
		if (price.equals(countTotalPrice)) {
			assert price.equals(totalPrice)
			assert qty.equals(countQty)
			status = ''
			remark = ''
		} else {
			status = 'Fail'
			remark = 'Fail to Check All Products order at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark]
	}

	def confirmBtn(String order_id, Integer status_id, String payment_type, Double total_price) {
		checkOrder = false
		KeywordUtil.logInfo(order_id)
		//		MobileElement ConfirmOrder = (MobileElement) driver.findElementById(riderId + 'order_detail_bt_confirm')
		List<MobileElement> ConfirmOrder = driver.findElementsByClassName('android.widget.Button')
		for (int i = 0; i < ConfirmOrder.size(); i++) {
			switch (status_id) {
				case 3 :
					if (ConfirmOrder.get(i).getText().contains('รับรายการคำสั่งซื้อ')) {
						ConfirmOrder.get(i).click()
						checkOrder = true
						break
					}
					break
				case 4 :
					if (ConfirmOrder.get(i).getText().contains('ชำระด้วยเงินสดสำเร็จ')) {
						ConfirmOrder.get(i).click()
						//						List<MobileElement> skipBtn = driver.findElementsByClassName('android.view.View')
						//						for (int j = 0; j < skipBtn.size(); j++) {
						//							if (skipBtn.get(j).getText().contains('ข้าม')) {
						//								skipBtn.get(j).click()
						//								break
						//							}
						//						}
						findElementToClick('android.view.View','ข้าม')

						//						List<MobileElement> deliveryType = driver.findElementsByClassName('android.view.View')
						//						for (int k = 0; k < deliveryType.size(); k++) {
						//							if (deliveryType.get(k).getText().contains('รถจักยานยนต์')) {
						//								deliveryType.get(k).click()
						//								break
						//							}
						//						}
						findElementToClick('android.view.View','รถจักยานยนต์')

						//						List<MobileElement> confirmDeli = driver.findElementsByClassName('android.widget.Button')
						//						for (int l = 0; l < confirmDeli.size(); l++) {
						//							if (confirmDeli.get(l).getText().contains('ตกลง')) {
						//								confirmDeli.get(l).click()
						//								break
						//							}
						//						}
						findElementToClick('android.widget.Button','ตกลง')

						List<MobileElement> confirmSign = driver.findElementsByClassName('android.view.View')
						for (int m = 0; m < confirmSign.size(); m++) {
							if (confirmSign.get(m).getText().contains('ตกลง')) {
								swipeUp()
								confirmSign.get(m).click()
								//								List<MobileElement> confirmSignBtn = driver.findElementsByClassName('android.widget.Button')
								//								for (int n = 0; n < confirmSignBtn.size(); n++) {
								//									if (confirmSignBtn.get(n).getText().contains('ยืนยัน')) {
								//										confirmSignBtn.get(n).click()
								//										break
								//									}
								//								}
								findElementToClick('android.widget.Button','ยืนยัน')
								break
							}
						}
						checkOrder = true
						break
					}
					break
			}
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
		MobileElement statusElement = (MobileElement) driver.findElementById(riderId + 'order_detail_time_count_down')
		KeywordUtil.logInfo('status text : ' + statusElement.getText())
		KeywordUtil.logInfo('status_id : ' + status_id)
		switch (status_id) {
			case 5 :
				if (statusElement.getText().contains('เสร็จสมบูรณ์')) {
					checkOrder = true
				}
				break
			case 6 :
				if (statusElement.getText().contains('ยกเลิกออเดอร์')) {
					checkOrder = true
				}
				break
		}
		if (checkOrder) {
			status = 'Pass'
			remark = '-'
			KeywordUtil.markPassed('check Status : Pass')
		} else {
			status = 'Fail'
			remark = 'Fail to check Status order at status_id ' + status_id
			KeywordUtil.markFailed(remark)
		}
		return [status, remark]
	}

	def findElementToClick (String className, String text) {
		List<MobileElement> skipBtn = driver.findElementsByClassName(className)
		for (int j = 0; j < skipBtn.size(); j++) {
			if (skipBtn.get(j).getText().contains(text)) {
				skipBtn.get(j).click()
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
