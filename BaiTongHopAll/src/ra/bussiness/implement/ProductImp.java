package ra.bussiness.implement;

import ra.bussiness.design.ICrud;
import ra.bussiness.entity.Product;
import ra.bussiness.entity.Users;
import ra.data.DataURL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductImp implements ICrud<Product, Integer> {
    @Override
    public boolean create(Product product) {
        List<Product> productList = readFromFile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        productList.add(product);
        boolean result = writeToFile(productList);
        return result;
    }

    @Override
    public boolean update(Product product) {
        List<Product> productList = readFromFile();
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID() == product.getProductID()) {
                productList.set(i, product);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(productList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        Scanner scanner = new Scanner(System.in);
        List<Product> productList = readFromFile();
        System.out.println("Nhập mã sản phẩm cần xóa");
        String delete = scanner.nextLine();
        boolean returnData = false;
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductID().equals(delete)){
                productList.remove(i);
                returnData = true;
                break;
            }
        }
        boolean result = writeToFile(productList);
        if (result && returnData) {
            return true;
        }
        return false;
    }

    @Override
    public List<Product> findAll() {
        return readFromFile();
    }

    @Override
    public Product findById(Integer id) {
        return null;
    }

    @Override
    public List<Product> readFromFile() {
        List<Product> productList = null;
        File file = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            file = new File(DataURL.URL_PRODUCT_FILE);
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            productList =(List<Product>) ois.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fis != null){
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return productList;
    }

    @Override
    public boolean writeToFile(List<Product> list) {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        boolean returnData = true;
        try {
            file = new File(DataURL.URL_PRODUCT_FILE);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (fos != null){
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return returnData;
    }

    @Override
    public Product inputData(Scanner scanner) {
        List<Product> productList = readFromFile();
        if (productList == null) {
            productList = new ArrayList<>();
        }
        Product newPro = new Product();
        System.out.println("Nhập mã sản phẩm:");
        do {
            newPro.setProductID(scanner.nextLine());
            boolean checkExit = false;
            if (newPro.getProductID().length()==5){
                if (newPro.getProductID().startsWith("P")){
                    for (Product pro:productList) {
                        if (pro.getProductID().equals(newPro.getProductName())){
                            checkExit = true;
                            break;
                        }
                    }
                    if (!checkExit){
                        break;
                    }else {
                        System.err.println("Mã sản phẩm đã tồn tại!");
                    }
                }else {
                    System.err.println("Mã sản phẩm phải bắt đầu bằng chữ 'P'");
                }
            }else {
                System.err.println("Mã sản phẩm phải gồm 5 ký tự!");
            }
        }while (true);
        System.out.println("Nhập tên sản phẩm:");
        newPro.setProductName(scanner.nextLine());
        System.out.println("Nhập giá sản phẩm:");
        do {
            try {
                newPro.setPrice(Float.parseFloat(scanner.nextLine()));
                if (newPro.getPrice()>0){
                    break;
                }else {
                    System.err.println("Giá bán phải lớn hơn 0");
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập 1 số thực");
            }
        }while (true);
        System.out.println("Trạng thái của sản phẩm:");
        System.out.println("1.Còn hàng");
        System.out.println("2.Hết hàng");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1){
            newPro.setProductStatus(true);
        }
        return newPro;
    }

    @Override
    public void displayData() {
        List<Product> productList = readFromFile();
        String status;
        if (productList.get(1).isProductStatus()){
            status = "Còn hàng";
        }else {
            status = "Hết hàng";
        }
        for (Product pro:productList) {
            System.out.println("Mã sản phẩm: "+pro.getProductID());
            System.out.println("Tên sản phẩm: "+pro.getProductName());
            System.out.println("Giá sản phẩm: "+pro.getPrice());
            System.out.println("Trạng thái sản phẩm: "+status);
        }
    }
    public List<Product> findProductByName(String productName){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập tên sản phẩm cần tìm kiếm");
        String name = scanner.nextLine();
        List<Product> listFull = readFromFile();
        List<Product> product = new ArrayList<>();
        for (Product pro:listFull) {
            if (pro.getProductName().contains(name)){
                product.add(pro);
            }
        }
        return product;
    }
}
