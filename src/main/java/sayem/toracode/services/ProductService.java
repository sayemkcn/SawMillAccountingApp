package sayem.toracode.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sayem.toracode.entities.BusinessPartnerEntity;
import sayem.toracode.entities.CategoryEntity;
import sayem.toracode.entities.ProductEntity;
import sayem.toracode.exception.CategoryNotFoundException;
import sayem.toracode.pojo.ProductProperties;
import sayem.toracode.repositories.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryService categoryService;

	public List<ProductEntity> getAllProductsByType(String type) {
		List<ProductEntity> productList = new ArrayList<>();
		for (ProductEntity product : productRepository.findAll()) {
			if (product.getType().equals(type))
				productList.add(product);
		}
		return productList;
	}

	public List<ProductEntity> findProductByType(String type) {
		return productRepository.findByType(type);
	}

	public List<ProductEntity> findAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

	public ProductEntity findById(Long id) {
		return productRepository.findOne(id);
	}

	public List<ProductEntity> findBySerial(String serial) {
		return productRepository.findBySerial(serial.toLowerCase());
	}

	public long calculatePrice(ProductEntity product) {
		return (long) (product.getProductProperties().getSize(product.getType())
				* product.getProductProperties().getRate());
	}

	public void saveProductList(List<ProductEntity> productList) {
		productRepository.save(productList);
	}

	public List<ProductEntity> extractData(MultipartFile file) throws CategoryNotFoundException {
		List<ProductEntity> productList = new ArrayList<>();
		try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(this.convertToFile(file)));
				HSSFWorkbook wb = new HSSFWorkbook(fs)) {
			// POIFSFileSystem fs = new POIFSFileSystem(new
			// FileInputStream(this.convertToFile(file)));
			// HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // No of columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it
			// doesn't start from first few rows
			for (int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}

			for (int r = 0; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null) {
					List<Object> rowValueList = new ArrayList<Object>();
					for (int c = 0; c < cols; c++) {
						cell = row.getCell((short) c);
						if (cell != null) {
							if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
								rowValueList.add(cell.getNumericCellValue());
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								rowValueList.add(cell.getStringCellValue());
							}
						}
					}
					ProductEntity product = new ProductEntity();
					product.setSerial(String.valueOf(rowValueList.get(0)));
					product.setType(String.valueOf(rowValueList.get(1)));
					product.setCategoryName(String.valueOf(rowValueList.get(2)));
					ProductProperties properties = new ProductProperties();
					properties.setPerimeter((double) rowValueList.get(3));
					properties.setHeight((double) rowValueList.get(4));
					properties.setWidth((double) rowValueList.get(5));
					properties.setLength((double) rowValueList.get(6));
					properties.setRate(new Double(rowValueList.get(7).toString()).intValue());
					product.setProductProperties(properties);
					product.setNote(String.valueOf(rowValueList.get(11)));
					BusinessPartnerEntity partner = new BusinessPartnerEntity();
					partner.setName(String.valueOf(rowValueList.get(8)));
					partner.setAddress(String.valueOf(rowValueList.get(9)));
					partner.setType(String.valueOf(rowValueList.get(10)));
					// find category with category name and set it to product
					CategoryEntity category = categoryService.findByName(product.getCategoryName());
					if (category == null) {
						throw new CategoryNotFoundException(
								"Can not find all of the categories. Please create the categories first!");
					}
					product.setCategory(category);
					// find Business Partner
					product.setBusinessPartner(partner);
					// calculate purchase price and save into database
					double productSize = product.getProductProperties().getSize(product.getType());
					product.setPurchasePrice((long) (productSize * product.getProductProperties().getRate()));
					productList.add(product);
				}
			}
		} catch (CategoryNotFoundException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return productList;
	}

	private File convertToFile(MultipartFile multipartFile) throws Exception {
		File file = new File(multipartFile.getOriginalFilename());
		file.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(multipartFile.getBytes());
		fileOutputStream.close();
		return file;
	}

	public List<ProductEntity> findByBusinessPartner(BusinessPartnerEntity partner) {
		return productRepository.findByBusinessPartner(partner);
	}
}
