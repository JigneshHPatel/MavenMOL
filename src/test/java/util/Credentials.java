/**
 * 
 */
package util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Jignesh Read Username and password from excel file from local
 *         machine.
 */
public class Credentials {

	private XSSFSheet sheet;

	public Credentials() {
		try {
			File file = new File("C:\\Users\\Jignesh\\Desktop\\Automation\\Credentials.xlsx");
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet("Sheet1");
			wb.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public String getBrowserStackUsername() {
		String BsID = sheet.getRow(1).getCell(0).getStringCellValue();
		return BsID;
	}

	public String getBrowserStackKey() {
		String BsKey = sheet.getRow(1).getCell(1).getStringCellValue();
		return BsKey;
	}

	public String getDirectLoginUsername() {
		String DLUsername = sheet.getRow(2).getCell(0).getStringCellValue();
		return DLUsername;
	}

	public String getDirectLoginpassword() {
		String DLPassword = sheet.getRow(2).getCell(1).getStringCellValue();
		return DLPassword;
	}

	public String getFacebookUsername() {
		String FBUsername = sheet.getRow(3).getCell(0).getStringCellValue();
		return FBUsername;
	}

	public String getFacebookPassword() {
		String FBPassword = sheet.getRow(3).getCell(1).getStringCellValue();
		return FBPassword;
	}

	public String getTwitterUsername() {
		String TWUsername = sheet.getRow(4).getCell(0).getStringCellValue();
		return TWUsername;
	}

	public String getTwitterPassword() {
		String TWPassword = sheet.getRow(4).getCell(1).getStringCellValue();
		return TWPassword;
	}
}
